package com.louAnimashuan.scores;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

public class BBCSportsScrapeTest extends TestCase{
	
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
			//TODO add catch statement
			e.printStackTrace();
		}
	}
	
	@Test
	public void test(){
		try {
			this.games = scrape.getScores();
		} catch (IOException e) {
			// TODO add catch statement
			e.printStackTrace();
		}
	}
	

}
