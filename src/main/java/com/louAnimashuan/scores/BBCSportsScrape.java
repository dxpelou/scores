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
	
	public  Document getDocument() throws IOException{
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
	
	
	
	public Game getScore(Document bbcDoc, Map<String, Slot> slots){
		
		String homeTeam = slots.get("HomeTeam").getValue();
		String awayTeam = null;

		if(slots.containsKey("AwayTeam")){
			awayTeam = slots.get("AwayTeam").getValue();
		}
		return getScore(bbcDoc, homeTeam, awayTeam);
	}
	
	public Game getScore(Document bbcDoc, String h, String a){
		Elements fixtures = bbcDoc.select(".fixture");
		Elements liveMatches = bbcDoc.select(".live");
		Elements results = bbcDoc.select(".result");
		
		String homeTeam = h;
		String awayTeam = a;
		
	/*	System.out.print("*");
		System.out.print(h);
		System.out.print("*");
		System.out.print(a);
		System.out.print("*"); */
		
		for(Element match : fixtures ){
			System.out.println("size of fixture(s): "+fixtures.size());
			try {
				 String home = match.select(".team-home").first().text();
				 String away = match.select(".team-away").first().text();
				 System.out.println("*"+home +"*"+away+"*");
				 if (homeTeam.equals(home)|| homeTeam.equals(away)){
					 String score = match.select(".score").first().text();
					 String time = match.select(".elapsed-time").first().text();
					 return new Game(home, away, score, time, MatchStatus.TOSTART);
				 }else{
					 System.out.println("not found home fixtures");
				 }
				 
				 if( (awayTeam != null && awayTeam.equals(home)) || (awayTeam != null && awayTeam.equals(away))){
					 String score = match.select(".score").first().text();
					 String time = match.select(".elapsedTime").first().text();
					 System.out.println("");
					 return new Game(home, away, score, time, MatchStatus.TOSTART );
				 }else{
					 System.out.println("not found away fixtures");
				 }
			}catch(NullPointerException e){
				System.out.println("continue fixtures");
				continue;
			}
		}
					
		for(Element match : liveMatches ){
			System.out.println("size of liveMatches: " + liveMatches.size());
			try {
				 String home = match.select(".team-home").first().text();
				 String away = match.select(".team-away").first().text();
				 
				 	if (homeTeam.equals(home) || homeTeam.equals(away)){
					String score = match.select(".score").first().text();
					String time = match.select(".elapsed-time").first().text();
					return new Game(home, away, score, time, MatchStatus.PLAYING );
					}else{
						System.out.println("not found home live match");
					}
							 
					if( (awayTeam != null && awayTeam.equals(home)) || (awayTeam != null && awayTeam.equals(away))){
						String score = match.select(".score").first().text();
						String time = match.select(".elapsed-time").first().text();
						return new Game(home, away, score, time, MatchStatus.PLAYING );
					}else{
						System.out.println("not found away live match");
					}
			}catch(NullPointerException e){
				System.out.println("continue live match");
				continue;
			}
		}
		
		for (Element match : results){
			System.out.println("Size of result: " +results.size());
			try {
				 String home = match.select(".team-home").first().text();
				 String away = match.select(".team-away").first().text();
				 
					if (homeTeam.equals(home) || homeTeam.equals(away)){
					String score = match.select(".score").first().text();
					String time = match.select(".elapsed-time").first().text();
					return new Game(home, away, score, time, MatchStatus.FINISHED);
						}else{
							System.out.println("not found home result");
						}
					if( (awayTeam != null && awayTeam.equals(home)) || (awayTeam != null && awayTeam.equals(away))){
						String score = match.select(".score").first().text();
						String time = match.select(".elapsed-time").first().text();
						return new Game(home, away, score, time, MatchStatus.FINISHED);
						}else{
							System.out.println("not found away result");
						}
			}catch(NullPointerException e){
				System.out.println("continue result");
				continue;
			}
		}
		return null;
	}
	
	//new code
	
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
					 System.out.println(time);
					 time = time.split(" ", 2)[0];
					 System.out.println(time);
					 return new Match(home, away, score, time, MatchStatus.TOSTART);
				 }
				 
				 if( (awayTeam != null && awayTeam.equals(home)) || (awayTeam != null && awayTeam.equals(away))){
					 String score = match.select(".score").first().text();
					 String time = match.select(".elapsedTime").first().text();
					 String pattern = "([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]";
					 Pattern r = Pattern.compile(pattern);
					 time = r.matcher(time).group(0);
					 
					 return new Match(home, away, score, time, MatchStatus.TOSTART );
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
					return new Match(home, away, score, time, MatchStatus.PLAYING );
					}else{
						System.out.println("not found home live match");
					}
							 
					if( (awayTeam != null && awayTeam.equals(home)) || (awayTeam != null && awayTeam.equals(away))){
						String score = match.select(".score").first().text();
						String time = match.select(".elapsed-time").first().text();
						return new Match(home, away, score, time, MatchStatus.PLAYING );
					}else{
						System.out.println("not found away live match");
					}
			}catch(NullPointerException e){
				System.out.println("continue live match");
				continue;
			}
		}
		
		return null;
	}
	
	public static Match getResult(Document bbcDoc, String h, String a){
		Elements results = bbcDoc.select(".report");
		//System.out.println("Size of result: " +results.size());
		String homeTeam = h;
		String awayTeam = a;
		
		for (Element match : results){
			try {
				 String home = match.select(".team-home").first().text();
				 String away = match.select(".team-away").first().text();
				 
				 //System.out.format("home: %s homeAssert: %s, away: %s awayAssert:%s", home, away, h, a);
				 
					if (homeTeam.equals(home) || homeTeam.equals(away)){
						String score = match.select(".score").first().text();
						String time = match.select(".elapsed-time").first().text();
						return new Match(home, away, score, time, MatchStatus.FINISHED);
					}else{
							System.out.println("not found home result");
						}
					if( (awayTeam != null && awayTeam.equals(home)) || (awayTeam != null && awayTeam.equals(away))){
						String score = match.select(".score").first().text();
						String time = match.select(".elapsed-time").first().text();
						return new Match(home, away, score, time, MatchStatus.FINISHED);
						}else{
							System.out.println("not found away result");
						}
			}catch(NullPointerException e){
				System.out.println("continue result");
				continue;
			}
		}
		return null;
	}


	
	public boolean currentlyPlaying(String team) throws IOException{
		
		boolean isPlaying = false;
		//TODO implement isCurrentlyPlaying Method
		Document doc = Jsoup.connect(bbcSportsURL).get();
		
		return isPlaying;
		

	}
	
}
