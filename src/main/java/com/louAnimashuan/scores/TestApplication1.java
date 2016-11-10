package com.louAnimashuan.scores;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestApplication1 {
	
	public static void main (String[] args) throws IOException{
		ArrayList<Game> games = null;
		BBCSportsScrape bbcss = new BBCSportsScrape();
		
		Document doc = bbcss.getDocument();
		
		games = bbcss.getScores(doc);
		
		for(Game game : games){
			System.out.print( "get all:" + game.getAll());
		}
	}
}
