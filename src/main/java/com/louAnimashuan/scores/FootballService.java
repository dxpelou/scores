package com.louAnimashuan.scores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.*;

public class FootballService {
	
	private HttpClient client = null;
	private String AuthType = "X-Auth-Token";
	private String APIToken = "your api token";
	private String Url = "api.football-data.org/";
	
	void getScores() throws ClientProtocolException, IOException{
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(Url);
		request.addHeader(AuthType, APIToken );
		HttpResponse response = client.execute(request);
	
		BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		
		String line ="";
		
		while ((line = rd.readLine()) != null) {
			System.out.println(line);
		}
	}

	 


	
	
}
