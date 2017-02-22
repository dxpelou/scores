package com.louAnimashuan.scores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.amazon.speech.slu.Slot;

public class BBCSportsScrape {
	
	private static final String bbcSportsURL = "http://www.bbc.co.uk/sport/football/live-scores";
	
	public BBCSportsScrape(){
	
	}
	
	public static Document getDocument() throws IOException{
		return Jsoup.connect(bbcSportsURL).get();
	}
	
	public ArrayList<Game> getScores(Document bbcDoc) throws IOException{ //TODO change name to get matches
	
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
				continue;
			}
			games.add(new Game(homeTeam, awayTeam, null, elapsedTime, MatchStatus.FIXTURE));
		}
		
		for(Element match : liveMatches  ){
			homeTeam = match.select(".team-home").first().text();
			awayTeam = match.select(".team-away").first().text();
			elapsedTime = match.select(".elapsed-Time").first().text();
			score = match.select(".score").first().text();
			
			games.add(new Game(homeTeam, awayTeam, score, elapsedTime, MatchStatus.LIVEMATCH));
		}
		
		for(Element match : results){
			homeTeam = match.select(".team-home").first().text();
			awayTeam = match.select(".team-away").first().text();
			score = match.select(".score").first().text();
			
			games.add(new Game(homeTeam, awayTeam, score, null, MatchStatus.RESULT));
		}
	
		return games;
	}
	
	
	
	
	public static Match getFixture(Document bbcDoc, String h, String a){
		Elements fixtures = bbcDoc.select(".fixture");
		Elements liveMatches = bbcDoc.select(".live");
		Elements results = bbcDoc.select(".result");
		
		String homeTeam = h;
		String awayTeam = a;
		
		for(Element match : fixtures ){
			try {
				 String home = match.select(".team-home").first().text();
				 String away = match.select(".team-away").first().text();
				 if (homeTeam.equals(home)|| homeTeam.equals(away)){
					 String score = match.select(".score").first().text();
					 String time = match.select(".elapsed-time").first().text();
					 time = time.split(" ", 2)[0];
					 return new Match(home, away, score, time, MatchStatus.FIXTURE);
				 }
				 
				 if( (awayTeam != null && awayTeam.equals(home)) || (awayTeam != null && awayTeam.equals(away))){
					 String score = match.select(".score").first().text();
					 String time = match.select(".elapsedTime").first().text();
					 String pattern = "([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]";
					 Pattern r = Pattern.compile(pattern);
					 time = r.matcher(time).group(0);
					 
					 return new Match(home, away, score, time, MatchStatus.FIXTURE );
				 }
			}catch(NullPointerException e){
				continue;
			}
		}
		
		return null;
	}
	
	public static Match getLiveMatch(Document bbcDoc, String h, String a){
		Elements liveMatches = bbcDoc.select(".live");
		
		String homeTeam = h;
		String awayTeam = a;
		
		for(Element match : liveMatches ){
			System.out.println("size of liveMatches: " + liveMatches.size());
			try {
				 String home = match.select(".team-home").first().text();
				 String away = match.select(".team-away").first().text();
				 
				 	if (homeTeam.equals(home) || homeTeam.equals(away)){
					String score = match.select(".score").first().text();
					String time = match.select(".elapsed-time").first().text();
					return new Match(home, away, score, time, MatchStatus.LIVEMATCH );
					}
							 
					if( (awayTeam != null && awayTeam.equals(home)) || (awayTeam != null && awayTeam.equals(away))){
						String score = match.select(".score").first().text();
						String time = match.select(".elapsed-time").first().text();
						return new Match(home, away, score, time, MatchStatus.LIVEMATCH );
					}
			}catch(NullPointerException e){
				continue;
			}
		}
		
		return null;
	}
	
	public static Match getResult(Document bbcDoc, String h, String a){
		Elements results = bbcDoc.select(".report");
		String homeTeam = h;
		String awayTeam = a;
		
		for (Element match : results){
			try {
				 String home = match.select(".team-home").first().text();
				 String away = match.select(".team-away").first().text();
				 
					if (homeTeam.equals(home) || homeTeam.equals(away)){
						String score = match.select(".score").first().text();
						String time = match.select(".elapsed-time").first().text();
						return new Match(home, away, score, time, MatchStatus.RESULT);
					}
					
					if( (awayTeam != null && awayTeam.equals(home)) || (awayTeam != null && awayTeam.equals(away))){
						String score = match.select(".score").first().text();
						String time = match.select(".elapsed-time").first().text();
						return new Match(home, away, score, time, MatchStatus.RESULT);
					}
			}catch(NullPointerException e){
				continue;
			}
		}
		return null;
	}

	//NOT IN USE
	private static Match getMatch(Document doc, String h, String a, MatchStatus status, String selector){
		
		Elements results = doc.select(selector);
		String homeTeam = h;
		String awayTeam = a;
		
		for(Element match : results){
			try {
				 String home = match.select(".team-home").first().text();
				 String away = match.select(".team-away").first().text();
				 
				if (homeTeam.equals(home) || homeTeam.equals(away)){
					String score = match.select(".score").first().text();
					String time = match.select(".elapsed-time").first().text();
					return new Match(home, away, score, time, status);
				}
					
				if( (awayTeam != null && awayTeam.equals(home)) || (awayTeam != null && awayTeam.equals(away))){
					String score = match.select(".score").first().text();
					String time = match.select(".elapsed-time").first().text();
					return new Match(home, away, score, time, status);
				}
			}catch(NullPointerException e){
				continue;
			}
		}	
		return null;
	}
	
	
	//NOT IN  USE
	public static Match getResult2(Document doc, String h, String a ){
		MatchStatus status = MatchStatus.RESULT;
		String selector = ".report";
		
		return getMatch(doc, h, a, status, selector);
	}
	
	//NOT IN USE
	public static Match getFixture2(Document doc, String h, String a ){
		MatchStatus status = MatchStatus.FIXTURE;
		String selector = ".fixture";
		
		return getMatch(doc, h,  a, status, selector);
	}
	//NOT IN USE
	public static Match getLiveMatch2(Document doc, String h, String a){
		MatchStatus status = MatchStatus.LIVEMATCH;
		String selector = ".live";
		return getMatch(doc, h, a, status, selector);
	}
	
}
