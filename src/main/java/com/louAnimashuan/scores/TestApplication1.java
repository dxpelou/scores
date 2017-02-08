package com.louAnimashuan.scores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.amazon.speech.slu.Slot;

public class TestApplication1 {
	
	public static void main (String[] args) throws IOException{
		ArrayList<Game> games = null;
		Game game2 = null;
		
		BBCSportsScrape bbcss = new BBCSportsScrape();
		Document doc = bbcss.getDocument();
	    //}
		
	/*	if(game2 == null){
		System.out.println("game is null");
		}else{
			System.out.println(game2.getHomeTeam());
		}
		*/
		
		ScoresSpeechlet speechlet = new ScoresSpeechlet();
		
	
	}
}
