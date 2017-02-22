package com.louAnimashuan.scores;

public class Match {
	
	private final String homeTeam;
	private final String awayTeam;
	
	//TODO find out why homeScore and awayScore can not be made final
	private int homeScore;
	private int awayScore;
	private MatchStatus status;
	private String time;
	
	
	public Match(String home, String away, String score, String time, MatchStatus status){
		this.status = status;
		this.homeTeam = home;
		this.awayTeam = away;
		setScore(score);
		this.time = time;
	}
		
	private void setScore(String score){
			String[] scores = null;
			if(status == MatchStatus.LIVEMATCH || status == MatchStatus.RESULT ){
				scores = score.split(" - ");
				this.homeScore = Integer.parseInt(scores[0]);
				this.awayScore = Integer.parseInt(scores[1]);
			}
		}
	
	public String getHomeTeam(){
		return homeTeam;
	}
	
	public String getAwayTeam(){
		return awayTeam;
	}
	
	public int getHomeScore(){
		return homeScore;
	}
	
	public int getAwayScore(){
		return awayScore;
	}
	
		
	public int[] getScore(){
		
		return new int[]{homeScore,awayScore};
	}
	
	public MatchStatus getMatchStatus(){
		return status;
	}
	
	public String getTime(){
		return time;
	}
	
	@Override
	//class used for testing 
	public String toString(){
		return(String.format(" home Team:%s, Away Team:%s, Home Score:%d, Away Score:%d, Match Status:%s, time: %s", 
				this.homeTeam, this.awayTeam, this.homeScore, this.awayScore, this.status.toString(), this.time));
	}
	


}
