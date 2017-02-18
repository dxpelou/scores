package com.louAnimashuan.scores;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;


public class ScoresSpeechlet implements Speechlet {
	
	private static final Logger log = LoggerFactory.getLogger(ScoresSpeechlet.class);
	
	public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {
		log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
	}
	
	public SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException {
		log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        return welcomeMessage();
	}
	

	public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {
		log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

		Intent intent = request.getIntent();
		String intentName = (intent != null) ? intent.getName() : null;
		System.out.println("Intent Recieved: "+ intentName);
		Document doc = null;
		
		//Test Code
		File afterInput = new File("after-match.html");
		try {
			doc = Jsoup.parse(afterInput, "UTF-8");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		try {
			doc = new BBCSportsScrape().getDocument();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		if("GetScoresIntent".equals(intentName)){
			System.out.println("GetScoresIntent Recieved");
			return getScores(intent, session); 
		}else if("GetLiveMatchIntent".equals(intentName)) {
			System.out.println("GetLiveMatchIntent recieved");
			return requestLiveMatch(intent, session, doc);
		}else if("GetResultIntent".equals(intentName)){
			System.out.println("GetResultIntent recieved");
			return requestResult(intent, session, doc);
		}else{
			throw new SpeechletException("Invalid Intent");
		}
	}



	public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {
		log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
	}
	
	//DEPRICATED 
	private SpeechletResponse getScores(Intent intent, Session session){
		
		Map<String, Slot> chosenTeams = intent.getSlots();
		StringBuilder speechText = new StringBuilder("the results of the ");
		boolean twoSlots = false;
		
		
		Slot home = chosenTeams.get("HomeTeam");
		Slot away = chosenTeams.get("AwayTeam");
		
		// Test
		System.out.println("Home and Away slots created");
		
		speechText.append(home.getValue());
		
		if (chosenTeams.containsKey("AwayTeam")){
			twoSlots = true;
			speechText.append(away.getValue());
		}
		
		speechText.append(" game, is");
		
		BBCSportsScrape scrape = new BBCSportsScrape();
		Game game = null;
		try{
			game = scrape.getScore( scrape.getDocument(), chosenTeams);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//Test
		System.out.println("succefully created game object");
		
		try{
		speechText.append(game.getHomeScore());
		speechText.append(game.getAwayScore());
		}catch(NullPointerException e){
			//Test
			System.out.println("Match has yet to start");
		//return SpeechletResponse.newAskResponse("", reprompt, card)
		}
		
		//Test
		System.out.println("speechText: " +speechText.toString());
		
		SimpleCard card = new SimpleCard();
		card.setTitle(" Football Scores");
		card.setContent(speechText.toString());
		
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText.toString());
		
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);
		
		
		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}
	
	
	
	
	// new Code 
	
	private SpeechletResponse requestFixture(Intent intent, Session session, Document bbc){
		
		Map<String, Slot> chosenTeams = intent.getSlots();
		Slot home = chosenTeams.get("HomeTeam");
		Slot away = chosenTeams.get("AwayTeam");
		
		Match fixture = null;
		Match liveMatch = null;
		Match result = null;
		PlainTextOutputSpeech speech = null;
		SimpleCard card = null;
		Reprompt reprompt = null;
		
		try{
			fixture = BBCSportsScrape.getFixture(bbc , home.getValue(), away.getValue());
		}catch(NullPointerException n){
			try{
				liveMatch = BBCSportsScrape.getLiveMatch(bbc, home.getValue(), away.getValue());
			}catch(NullPointerException p){
				try{
					result = BBCSportsScrape.getResult(bbc, home.getValue(), away.getValue());
				}catch(NullPointerException e){
					
					String speechText = "I could not find the match you requested";
					speech.setText(speechText);
					
					return SpeechletResponse.newAskResponse( speech, reprompt, createCard(speech, "Match not found"));
				}
				
			}
		}
		
		speech = createScoreSpeech(fixture);
		card = createCard(speech, "Football Scores");
		
		reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);
	
		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}
	
	
	
	
	
	private SpeechletResponse requestLiveMatch(Intent intent, Session session, Document doc){
		
		Map<String, Slot> chosenTeams = intent.getSlots();
		Slot home = chosenTeams.get("HomeTeam");
		Slot away = chosenTeams.get("AwayTeam");
		
		Match fixture = null;
		Match liveMatch = null;
		Match result = null;
		PlainTextOutputSpeech speech = null;
		SimpleCard card = null;
		Reprompt reprompt = null;
		
		try{
			
			liveMatch = BBCSportsScrape.getLiveMatch(doc, home.getValue(), away.getValue());
		}catch(NullPointerException n){
			try{
				result = BBCSportsScrape.getResult(doc, home.getValue(), away.getValue());
			}catch(NullPointerException p){
				try{
					fixture = BBCSportsScrape.getFixture(doc , home.getValue(), away.getValue());
				}catch(NullPointerException e){
					
					String speechText = "I could not find the match you requested";
					speech.setText(speechText);
					
					return SpeechletResponse.newAskResponse( speech, reprompt, createCard(speech, "Match not found"));
				}
				
			}
		}
		
		//TODO change output speech
		
		speech = createLiveMatchScoreSpeech(liveMatch);
		card = createCard(speech, "Football Scores");
		
		reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);
	
		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}
	
	private SpeechletResponse requestResult(Intent intent, Session session, Document doc){
		System.out.println("requestResult called");
		Map<String, Slot> chosenTeams = intent.getSlots();
		Slot home = chosenTeams.get("Home");
		Slot away = chosenTeams.get("Away");
		
		Match result = null;
		PlainTextOutputSpeech speech = null;
		SimpleCard card = null;
		Reprompt reprompt = null;
		//System.out.println("requestResult called2");
		//System.out.format("home: %s away: %s ",home.getValue() , away.getValue());
		System.out.println(home.getValue());
		System.out.println(home.getValue());
		try{
			result = BBCSportsScrape.getResult(doc, home.getValue(), away.getValue());
			System.out.println("Found Result Element");
		}catch(NullPointerException n){
			System.out.println("did not find result");
			try{
				result = BBCSportsScrape.getLiveMatch(doc, home.getValue(), away.getValue());
				System.out.println("Found live Match");
			}catch(NullPointerException p){
				System.out.println("did not find live Match");
				try{
					result = BBCSportsScrape.getFixture(doc , home.getValue(), away.getValue());
					System.out.println("Found fixture");
				}catch(NullPointerException e){
					String speechText = "I could not find the match you requested";
					speech = new PlainTextOutputSpeech(); 
					speech.setText(speechText);
					
					reprompt = new Reprompt();
					reprompt.setOutputSpeech(speech);
					return SpeechletResponse.newAskResponse( speech, reprompt, createCard(speech, "Match not found"));
				}
				
			}
		}
		
		System.out.println(result.getHomeTeam());
		System.out.println(result.getAwayTeam());
		System.out.println(result.getHomeScore());
		System.out.println(result.getAwayScore());
		
		
		//TODO change output speech
		speech = createLiveMatchScoreSpeech(result);
		card = createCard(speech, "Football Scores");
		
		reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);
		
		System.out.println(speech);
	
		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}
	
		
	
	private PlainTextOutputSpeech createScoreSpeech(Match match){
		StringBuilder speechText = new StringBuilder("The results of the ");
		speechText.append(match.getHomeTeam() + ", " + match.getAwayTeam() + "game is " + match.getHomeScore() + match.getAwayScore());
		PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
		outputSpeech.setText("speechText");
		return outputSpeech;
	}
	
	
	
	private PlainTextOutputSpeech createLiveMatchScoreSpeech(Match match){
		String speechText;
		
		speechText = String.format("The result of the %s, %s match is %d %d", match.getHomeTeam(), match.getAwayTeam(), match.getHomeScore(), match.getAwayScore());
		
		PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
		outputSpeech.setText(speechText);
		return outputSpeech;
	}
	
	
	
	private SimpleCard createCard(PlainTextOutputSpeech outputSpeech, String title) {
		
		SimpleCard card = new SimpleCard();
		card.setTitle(title);
		card.setContent(outputSpeech.getText());
		
		return card;
	}
	
	
	
	private SpeechletResponse getWelcomeMessage(){
		return null; 
	}
	
	
	
	
	private SpeechletResponse welcomeMessage(){
		
		return null;
	}	
}
