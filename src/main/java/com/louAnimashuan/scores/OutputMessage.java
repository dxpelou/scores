package com.louAnimashuan.scores;

import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;

public class OutputMessage {

	//DEPRICATED
	public static PlainTextOutputSpeech createScoreSpeech2(Match match){
		StringBuilder speechText = new StringBuilder("The results of the ");
		speechText.append(match.getHomeTeam() + ", " + match.getAwayTeam() + "game is " + match.getHomeScore() + match.getAwayScore());
		PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
		outputSpeech.setText("speechText");
		return outputSpeech;
	}
	
	
	
	public static PlainTextOutputSpeech createScoreSpeech(Match match){
		String speechText; 
		if(match.getMatchStatus() == MatchStatus.FIXTURE){
			speechText = String.format("The match between %s, and %s starts at %s.", match.getHomeTeam(), match.getAwayTeam(), match.getTime());
		}else{
			speechText = String.format("The result of the %s, %s match is %d %d", match.getHomeTeam(), match.getAwayTeam(), match.getHomeScore(), match.getAwayScore());
		}
		
		PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
		outputSpeech.setText(speechText);
		return outputSpeech;
	}
	
	
	
	public static SimpleCard createCard(PlainTextOutputSpeech outputSpeech, String title) {
		
		SimpleCard card = new SimpleCard();
		card.setTitle(title);
		card.setContent(outputSpeech.getText());
		
		return card;
	}
	
}
