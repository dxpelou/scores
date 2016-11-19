package com.louAnimashuan.scores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
	
	private String homeTeam;
	private String awayTeam;
	private int homeScore;
	private int awayScore;
	private MatchStatus matchStatus;
	private String time;
	
	
	public Game(String home, String away, int homeScore, int awayScore, String status){
		this.homeTeam = home;
		this.awayTeam = away;
		this.homeScore = homeScore;
		this.awayScore = awayScore;
		setMatchStatus(status);
	}
	
	
	public Game(String home, String away, String score, String time, MatchStatus status){
		this.matchStatus = status;
		this.homeTeam = home;
		this.awayTeam = away;
		setScore(score);
		this.time = time;
	}
	
	
	private void setScore(String score){
		String[] scores = null;
		if(matchStatus == MatchStatus.PLAYING || matchStatus == MatchStatus.FINISHED ){
			scores = score.split(" - ");
			this.homeScore = Integer.parseInt(scores[0]);
			this.awayScore = Integer.parseInt(scores[1]);
		}
	}
	
	
	public Game(String home, String away, String score, String status){
		this.homeTeam = home;
		this.awayTeam = away;
		setMatchStatus(status);
		String[] scores = score.split("-");
		this.homeScore = Integer.parseInt(scores[0]);
		this.awayScore = Integer.parseInt(scores[1]);
	}
	
	
	private MatchStatus setMatchStatus(String status ){
		Pattern beforeMatchPattern = Pattern.compile("\\d\\d:\\d\\d");
		Pattern afterMatchPattern = Pattern.compile("Result");
		Pattern duringMatchPattern = Pattern.compile("*\\d\\d mins"); 
		//TODO Find regular expression to account for the case where a match is playing for a time with 3 digits 
		
		Matcher beforeMatcher = beforeMatchPattern.matcher(status);
		Matcher afterMatcher = afterMatchPattern.matcher(status);
		Matcher duringMatcher = duringMatchPattern.matcher(status);
		
		if (beforeMatcher.find()){
			this.matchStatus = MatchStatus.TOSTART; 
		}else if (afterMatcher.find()){
			this.matchStatus = MatchStatus.FINISHED; 
		}else if (duringMatcher.find()){
			this.matchStatus = MatchStatus.PLAYING; 
		}else {
			System.out.println("could not find a match for the status");
		}
		return null;
	}

	
	public String getHomeTeam(){
		return this.homeTeam;
	}
	
	
	public String getAwayTeam(){
		return this.awayTeam;
	}
	
	
	public int getHomeScore(){
		return this.homeScore;
	}
	
	
	public int getAwayScore(){
		return this.awayScore;
	}
	
	
	public MatchStatus getMatchStatus(){
		return this.matchStatus;
	}
	
	
	public String getAll(){
		String temp = getHomeTeam();
		temp += " ";
		temp += getAwayTeam();
		temp += " ";
		temp += getHomeScore();
		temp += " ";
		temp += getAwayScore();
		temp += " ";
		temp += getMatchStatus().toString();	
		return temp;
	}
}
