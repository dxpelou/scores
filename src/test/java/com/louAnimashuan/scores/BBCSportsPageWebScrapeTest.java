package com.louAnimashuan.scores;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BBCSportsPageWebScrapeTest extends TestCase{
	
	private Document bbc;
	private BBCSportsScrape scrape; 
	private ArrayList<Game> games;
	

	@Before
	public void setUp(){
		try{
			File input = new File("/live-scores.html");
			this.bbc = Jsoup.parse(input, "UTF-8", null);
			this.scrape = new BBCSportsScrape();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void gameTest(){
		//TODO clean
		Game beforeGame = new Game("Arsenal", "Chelsea","v", "19:45" );
		Game duringGame = new Game("Arsenal","Chelsea","5-0","12mins" );
		Game duringGame2 = new Game("Asenal", "Chelsea","0-0,","112mins");
		Game afterGame = new Game("Arsenal", "Chelsea","10-0","Result" );
		
		AssertGame(beforeGame, "Arsenal", "Chelsea", 0, 0, MatchStatus.TOSTART);
		AssertGame(duringGame, "Arsenal", "Chelsea", 5, 0, MatchStatus.PLAYING);
		AssertGame(duringGame, "Arsenal", "Chelsea", 0, 0, MatchStatus.PLAYING);
		AssertGame(duringGame, "arsenal", "Chelsea", 10, 0, MatchStatus.FINISHED);
	}
	
	
	@Test
	public void test(){
		
		try {
			this.games = scrape.getScores(bbc);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		AssertGame(games.get(0),"Arsenal","Chelsea", 0, 0, MatchStatus.FINISHED);
		AssertGame(games.get(1), "Arsenal", "Chelsea", 0, 0 , null);
		AssertGame(games.get(2), "Arsenal", "Chelsea", 0, 0, null);
		
		Assert.assertEquals(games.get(0).getHomeTeam(), "basel");
		Assert.assertEquals(games.get(0).getAwayTeam(), "basel");
		Assert.assertEquals(games.get(0).getHomeScore(), 1);
		Assert.assertEquals(games.get(0).getAwayScore(), 2);
		Assert.assertEquals(games.get(0).getMatchStatus(),MatchStatus.FINISHED);
		
	}
	
	
	public void AssertGame(Game testGame, String home, String away, int homeScore, int awayScore, MatchStatus status){
		
		Assert.assertEquals(testGame.getHomeTeam(), home);
		Assert.assertEquals(testGame.getAwayTeam(), away);
		Assert.assertEquals(testGame.getHomeScore(), homeScore);
		Assert.assertEquals(testGame.getAwayScore(), awayScore);
		Assert.assertEquals(testGame.getMatchStatus(),status);
	}
}
