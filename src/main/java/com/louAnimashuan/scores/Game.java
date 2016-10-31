package com.louAnimashuan.scores;

public class Game {
	
	private String homeTeam;
	private String awayTeam;
	private int homeScore;
	private int awayScore;
	private MatchStatus matchStatus;
	
	public Game(String home, String away, int homeScore, int awayScore, String status){
		this.homeTeam = home;
		this.awayTeam = away;
		this.homeScore = homeScore;
		this.awayScore = awayScore;
		this.matchStatus = formatMatchStatus(status);
	}
	
	public Game(String home, String away, String score, String status){
		this.homeTeam = home;
		this.awayTeam = away;
		
		String[] scores = score.split("v");
		this.homeScore = Integer.parseInt(scores[0]);
		this.awayScore = Integer.parseInt(scores[1]);
		//TODO implement Game constructor 
		
	}
	
	private MatchStatus formatMatchStatus(String status ){
		//TODO implement formatMatchStatus method
		
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
		return null;
	}

}
