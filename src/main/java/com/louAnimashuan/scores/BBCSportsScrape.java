package com.louAnimashuan.scores;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BBCSportsScrape {
	
	private String scoresURL = "http://www.bbc.co.uk/sport/football/live-scores";
	private String isPlayingURL = "http://www.bbc.co.uk/sport/football/live-scores";
	
	public void getScores() throws IOException{ 
		Element homeTeam = null;
		Element awayTeam = null;
		Element elapsedTime = null;
		Element score = null;
		
		Document doc = Jsoup.connect(scoresURL).get();
		
		Elements gameData = doc.select(".fixture");
		
		for(Element fixture : gameData ){
			homeTeam = fixture.select(".team-home").first();
			awayTeam = fixture.select(".team-away").first();
			elapsedTime = fixture.select(".elapsed-Time").first();
			score = fixture.select(".score").first();
			
		}
		
		
		String home = homeTeam.text();
		String away = awayTeam.text();
		String time = elapsedTime.text();
		String scr = score.text();
		
		Game game = new Game(homeTeam.text(), awayTeam.text(), score.text(), elapsedTime.text());
		
	}
	
	
	public boolean currentlyPlaying(String team) throws IOException{
		
		boolean isPlaying = false;
		//TODO implement isCurrentlyPlaying Method
		Document doc = Jsoup.connect(scoresURL).get();
		
		return isPlaying;
		

	}
	
	
	

}
