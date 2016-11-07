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
	
	
	public BBCSportsScrape(){
		
	}
	
	public Document getDocument() throws IOException{
		return Jsoup.connect(scoresURL).get();
	}
	
	public ArrayList<Game> getScores(Document bbcDoc) throws IOException{ 
	
		String homeTeam = null;
		String awayTeam = null;
		String elapsedTime = null;
		String score = null;
		
		ArrayList<Game> games = new ArrayList<Game>();
		
		/*
		 * TODO add feature to select league playing in
		 * - current best solution: is to search for the premier league header and then navigate to parent node and then to the child node
		 */
		
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
		
		Elements fixtures = bbcDoc.select(".fixture");
		Elements liveMatches = bbcDoc.select(".live");
		Elements results = bbcDoc.select(".result");
		int i =0;
		for(Element match : fixtures ){
			try{
				homeTeam = match.select(".team-home").first().text();
				awayTeam = match.select(".team-away").first().text();
				elapsedTime = match.select(".elapsed-Time").first().text();
			}catch(NullPointerException e){
				break;
			}
			games.add(new Game(homeTeam, awayTeam, null, elapsedTime, MatchStatus.TOSTART));
		}
		
		for(Element match : liveMatches  ){
			homeTeam = match.select(".team-home").first().text();
			awayTeam = match.select(".team-away").first().text();
			elapsedTime = match.select(".elapsed-Time").first().text();
			score = match.select(".score").first().text();
			
			games.add(new Game(homeTeam, awayTeam, score, elapsedTime, MatchStatus.PLAYING));
		}
		
		for(Element match : results){
			homeTeam = match.select(".team-home").first().text();
			awayTeam = match.select(".team-away").first().text();
			score = match.select(".score").first().text();
			
			games.add(new Game(homeTeam, awayTeam, score, null, MatchStatus.FINISHED));
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
