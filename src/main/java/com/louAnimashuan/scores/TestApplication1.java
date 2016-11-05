package com.louAnimashuan.scores;

import java.io.IOException;
import java.util.ArrayList;

public class TestApplication1 {
	
	public static void main (String[] args) throws IOException{
		ArrayList<Game> games = null;
		BBCSportsScrape bbcss = new BBCSportsScrape();
		
		games = bbcss.getScores();
		
		for(Game game : games){
			System.out.print(game.getAll());
		}
	}

}
