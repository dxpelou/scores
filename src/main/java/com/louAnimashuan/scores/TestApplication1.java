package com.louAnimashuan.scores;

import java.io.IOException;

public class TestApplication1 {
	
	public static void main (String[] args) throws IOException{
		BBCSportsScrape bbcss = new BBCSportsScrape();
		
		bbcss.getScores();
	}

}
