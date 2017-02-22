package com.louAnimashuan.scores;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.PlainTextOutputSpeech;

public class BBCSportsPageWebScrapeTest {
	
	private Document bbcFixture;
	private Document bbcLiveMatch;
	private Document bbcResult;
	private BBCSportsScrape scrape; 
	private ArrayList<Game> games;
	private ScoresSpeechlet speechlet; 
	
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

	@Before
	public void setUp(){
		// TODO try writing tests for the File object :
		//System.out.println(new File(".").getAbsoluteFile());
		
		try{
			File duringInput = new File("during-match2.html"); 
			File beforeInput = new File("before-match2.html");
			File afterInput = new File("after-match.html");
			
			//File input = new File("/Users/louanimashaun/Documents/Amazon Alexa/Scores/scores/src/test/java/com/louAnimashuan/scores/before-match2.html");
			this.bbcFixture = Jsoup.parse(beforeInput, "UTF-8");
			this.bbcLiveMatch = Jsoup.parse(duringInput, "UTF-8");
			this.bbcResult = Jsoup.parse(afterInput, "UTF-8");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		speechlet = new ScoresSpeechlet();
	}
	
	@Test
	public void getFixtureTest(){
		String home = "Chelsea";
		String away = "Everton";
		String time = "17:30";
		Match fixture = BBCSportsScrape.getFixture(bbcFixture, home, away);
		AssertFixture(home, away, time, fixture);
	}
	
	@Test
	public void getLiveMatchTest(){
		String home = "Arsenal";
		String away = "Chelsea";
		int homeScore = 0;
		int awayScore = 0;
		String time = "12 mins";
		
		Match liveMatch = BBCSportsScrape.getLiveMatch(bbcLiveMatch, home, away);
		AssertLiveMatch(home, away, homeScore,  awayScore, time, liveMatch);
	}

	
	@Test
	public void getResultTest(){
		String home = "Bournemouth";
		String away = "Manchester City";
		int homeScore = 0;
		int awayScore = 2;
		Match result = BBCSportsScrape.getResult(bbcResult, home, away);
		AssertResult(home, away, homeScore, awayScore, result);
	}
	
	@Test
	public void speechletResponseResultTest() throws SpeechletException{
		IntentRequest request =  buildIntentRequest("Bournemouth", "Manchester City","GetResultIntent");
		Session session = buildSession("123456789");
		
		SpeechletResponse response = speechlet.onIntentTest(request, session, bbcResult);
		response.getCard().getTitle();
		OutputSpeech outputSpeech = response.getOutputSpeech();
		
		String speech = ((PlainTextOutputSpeech)outputSpeech).getText();
		
		Assert.assertEquals("The result of the Bournemouth, Manchester City match is 0 2", speech);
	}
	
	@Test 
	public void speechletResponseLiveMatchTest() throws SpeechletException{
		IntentRequest request = buildIntentRequest("Arsenal", "Chelsea", "GetLiveMatchIntent"); // EDIT
		Session session = buildSession("012345678");
		System.out.println(bbcLiveMatch);
		
		SpeechletResponse response = speechlet.onIntentTest(request, session, bbcLiveMatch);
		response.getCard().getTitle();
		OutputSpeech outputSpeech = response.getOutputSpeech();
		
		String speech = ((PlainTextOutputSpeech)outputSpeech).getText(); //EDIT 
		Assert.assertEquals("The result of the Arsenal, Chelsea match is 0 0", speech); //EDIT
	}
	
	@Test 
	public void speechletResponseFixtureTest() throws SpeechletException{
		IntentRequest request = buildIntentRequest("team1","Team2", "GetFixtureIntent"); //EDIT
		Session session = buildSession("012346789");
		
		SpeechletResponse response = speechlet.onIntentTest(request, session, bbcFixture);
		response.getCard().getTitle();
		OutputSpeech outputSpeech = response.getOutputSpeech();
		
		String speech = ((PlainTextOutputSpeech)outputSpeech).getText();//EDIT
		Assert.assertEquals("The result of the match is ", speech);//EDIT
	}
	
	@Test
	public void getFixtureTest2(){
		String home = "Chelsea";
		String away = "Everton";
		String time = "17:30";
		Match fixture = BBCSportsScrape.getFixture2(bbcFixture, home, away);
		AssertFixture(home, away, time, fixture);
	}
	
	@Test
	public void getLiveMatchTest2(){
		String home = "Arsenal";
		String away = "Chelsea";
		int homeScore = 0;
		int awayScore = 0;
		String time = "12 mins";
		
		Match liveMatch = BBCSportsScrape.getLiveMatch2(bbcLiveMatch, home, away);
		AssertLiveMatch(home, away, homeScore,  awayScore, time, liveMatch);
	}
	
	@Test
	public void getResultTest2(){
		String home = "Bournemouth";
		String away = "Manchester City";
		int homeScore = 0;
		int awayScore = 2;
		Match result = BBCSportsScrape.getResult2(bbcResult, home, away);
		AssertResult(home, away, homeScore, awayScore, result);
	}
	
	
	// TODO Test for can not find team was looking for
	
	
	public IntentRequest buildIntentRequest(String home, String away, String IntentName){
		Slot homeSlot = Slot.builder().withName("HomeTeam").withValue(home).build();
		Slot awaySlot = Slot.builder().withName("AwayTeam").withValue(away).build();
		Map slots = new HashMap();
		slots.put("Home", homeSlot);
		slots.put("Away", awaySlot);
		
		Intent intent = Intent.builder().withName(IntentName).withSlots(slots).build();
		System.out.println("Mock Intent Name: " + intent.getName());
		return IntentRequest.builder().withIntent(intent).withRequestId("000000000").build();
	}
	
	
	
	public Session buildSession(String sessionId){
		return Session.builder().withSessionId(sessionId).build();
	}
	
	
	
	public void AssertFixture(String home, String away, String time,Match fixture){
		Assert.assertEquals(home, fixture.getHomeTeam());
		Assert.assertEquals(away, fixture.getAwayTeam());
		Assert.assertEquals(time, fixture.getTime());
		// TODO Assert that homeScore and awaySCore equal null, as match has not started
	}
	
	
	
	public void AssertLiveMatch(String home , String away, int homeScore, int awayScore, String time, Match liveMatch){
		Assert.assertEquals(home, liveMatch.getHomeTeam());
		Assert.assertEquals(away, liveMatch.getAwayTeam());
		Assert.assertEquals(homeScore, liveMatch.getHomeScore());
		Assert.assertEquals(awayScore,liveMatch.getAwayScore());
		Assert.assertEquals(time, liveMatch.getTime());
	}

	
	public void AssertResult(String home, String away, int homeScore, int awayScore, Match result ){
		Assert.assertEquals(home, result.getHomeTeam());
		Assert.assertEquals(away, result.getAwayTeam());
		Assert.assertEquals(homeScore, result.getHomeScore());
		Assert.assertEquals(awayScore, result.getAwayScore());
	} 
	
	

	
	

}
