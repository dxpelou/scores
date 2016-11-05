package com.louAnimashuan.scores;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BBCSportsScrape {
	
	private String scoresURL = "http://www.bbc.co.uk/sport/football/live-scores";
	private String isPlayingURL = "http://www.bbc.co.uk/sport/football/live-scores";
	
	public ArrayList<Game> getScores() throws IOException{ 
		// TODO refactor code to make getScore return a games ArrayList
		
		Element homeTeam = null;
		Element awayTeam = null;
		Element elapsedTime = null;
		Element score = null;
		ArrayList<Game> games = new ArrayList<Game>();
		
		Document doc = Jsoup.connect(scoresURL).get();
		
		/*
		 * TODO fix bbc scraper for all cases of match status
		 */
		
		Elements gameData = doc.select(".fixture");
		
		for(Element fixture : gameData ){
			String home = fixture.select(".team-home").first().text();
			String away = fixture.select(".team-away").first().text();
			String time = fixture.select(".elapsed-Time").first().text();
			String scr = fixture.select(".score").first().text();
			
			games.add(new Game(home, away,scr, time));
			
		}
		
		return games;
		
	}
	
	
	public boolean currentlyPlaying(String team) throws IOException{
		
		boolean isPlaying = false;
		//TODO implement isCurrentlyPlaying Method
		Document doc = Jsoup.connect(scoresURL).get();
		
		return isPlaying;
		

	}
	
}
