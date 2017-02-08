package com.louAnimashuan.scores;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.PlainTextOutputSpeech;

public class BBCSportsPageWebScrapeTest {
	
	private Document bbcFixture;
	private Document bbcLiveMatch;
	private Document bbcResult;
	private BBCSportsScrape scrape; 
	private ArrayList<Game> games;
	private ScoresSpeechlet speechlet; 
	
	/*Sample inputs:
	 * class: fixture
	 * time: "15:00"
	 * scr: " v "
	 * 
	 * class: live
	 * time: "12 mins"
	 * scr: "1 - 0"
	 * 
	 * class: result
	 * time: Result
	 * scr: "1 - 0"
	 */

	@Before
	public void setUp(){
		// TODO try writing tests for the File object :
		//System.out.println(new File(".").getAbsoluteFile());
		
		//System.out.println(new File(".").getAbsoluteFile());
		
		try{
			File duringInput = new File("during-match2.html"); // TODO implemented as resource
			File beforeInput = new File("before-match2.html");
			
			//File input = new File("/Users/louanimashaun/Documents/Amazon Alexa/Scores/scores/src/test/java/com/louAnimashuan/scores/before-match2.html");
			this.bbcFixture = Jsoup.parse(beforeInput, "UTF-8");
			this.bbcLiveMatch = Jsoup.parse(duringInput, "UTF-8");
			System.out.println(bbcFixture.toString());
			System.out.println(bbcLiveMatch.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		speechlet = new ScoresSpeechlet();
	}
	
	/*@Test
	public void getScoreTest(){
		boolean bool = true;
		//TODO clean
		Assert.assertEquals(true, bool);
		
		Game game = scrape.getScore(bbcFixture, "Arsenal",  "Chelsea");
		Assert.assertEquals(game.getHomeTeam(),"Arsenal");
		Assert.assertEquals(game.getAwayTeam(), "Chelsea");
	}
	
	@Test
	public void speechletResponseTest() throws SpeechletException{
		IntentRequest request =  buildIntentRequest("Arsenal", "Chelsea","getScoresIntent");
		Session session = buildSession("123456789");
		
		SpeechletResponse response = speechlet.onIntent(request, session);
		response.getCard().getTitle();
		OutputSpeech outputSpeech = response.getOutputSpeech();
		
		String speech = ((PlainTextOutputSpeech)outputSpeech).getText();
		
		Assert.assertEquals(speech,"the results of the Chelsea Arsenal game is00");
	}
	

	@Test
	public void test2() throws SpeechletException {
		/*IntentRequest request =  buildIntentRequest("Tottenham Hotspur", "Chelsea","getScores");
		Session session = buildSession("123456789");
		
		SpeechletResponse response = speechlet.onIntent(request, session);
		response.getCard().getTitle();
		OutputSpeech outputSpeech = response.getOutputSpeech();
		
		String speech = ((PlainTextOutputSpeech)outputSpeech).getText();
		
		//Assert.assertEquals(speech,"the results of the Chelsea Arsenal game is00"); 
	}
	
	
	//TODO finish getFixture test
	@Test
	public void getFixtureTest(){
		String home = "Chelsea";
		String away = "Everton";
		String time = "17:30";
		Match fixture = BBCSportsScrape.getFixture(bbcFixture, home, away);
		System.out.println("getFixtureTest: " +fixture.getAllParameters());
		AssertFixture(fixture, home, away, time );
	}
	
	
	
	//TODO finish getLiveMatch test
	public void getLiveMatchTest(){
		String home = "Arsenal";
		String away = "Chelsea";
		int homeScore = 1;
		int awayScore = 0;
		String time = "12 mins";
		
		Match liveMatch = BBCSportsScrape.getLiveMatch(bbcLiveMatch, home, away);
		AssertLiveMatch(liveMatch, home, away, homeScore,  awayScore, time);
	}
	
	
	
	//TODO finish getReulst test
	public void getResultTest(){
		String home = "Besiktas ";
		String away = "Napol";
		int homeScore = 1;
		int awayScore = 1;
		
		Match result = BBCSportsScrape.getResult(bbcLiveMatch, home, away);
		//AssertResult(result, home, away, homeScore, awayScore);
	}
	
	
	
	public IntentRequest buildIntentRequest(String home, String away, String IntentName){
		Slot homeSlot = Slot.builder().withName("Home").withValue(home).build();
		Slot awaySlot = Slot.builder().withName("Away").withValue(away).build();
		Map slots = new HashMap();
		slots.put("Home", homeSlot);
		slots.put("Away", awaySlot);
		
		Intent intent = Intent.builder().withName(IntentName).withSlots(slots).build();
		return IntentRequest.builder().withIntent(intent).withRequestId("000000000").build();
	}
	
	
	
	public Session buildSession(String sessionId){
		return Session.builder().withSessionId(sessionId).build();
	}
	
	
	
	// TODO check Match class on how to handle a fixture
	public void AssertFixture(Match fixture, String home, String away, String time){
		Assert.assertEquals(fixture.getHomeTeam(), home);
		Assert.assertEquals(fixture.getAwayTeam(), away);
		Assert.assertEquals(fixture.getTime(), time);
		// TODO Assert that homeScore and awaySCore equal null, as match has not started
	}
	
	
	
	public void AssertLiveMatch(Match liveMatch, String home , String away, int homeScore, int awayScore, String time){
		Assert.assertEquals(liveMatch.getHomeTeam(), home);
		Assert.assertEquals(liveMatch.getAwayTeam(), away);
		Assert.assertEquals(liveMatch.getHomeScore(), homeScore);
		Assert.assertEquals(liveMatch.getAwayScore(), awayScore);
		Assert.assertEquals(liveMatch.getTime(), time);
	}
	
	public void AssertResult(Match result, String home, String away, int homeScore, int awayScore ){
		Assert.assertEquals(result.getHomeTeam(), home);
		Assert.assertEquals(result.getAwayTeam(), away);
		Assert.assertEquals(result.getHomeScore(), homeScore);
		Assert.assertEquals(result.getAwayScore(), awayScore);
	} */
	
	
	

}
