package com.louAnimashuan.scores;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;


public class TestScoresSpeechlet implements Speechlet {
	
	private static final Logger log = LoggerFactory.getLogger(ScoresSpeechlet.class);
	
	public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {
		log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
	}
	
	public SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException {
		log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        return welcomeMessage();
	}

	public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {
		log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

		Intent intent = request.getIntent();
		String intentName = (intent != null) ? intent.getName() : null;
		
		if("GetScoresIntent".equals(intentName)){
			
			PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
			speech.setText("getScoresIntent");
			Reprompt reprompt = new Reprompt();
			reprompt.setOutputSpeech(speech);
			return SpeechletResponse.newAskResponse(speech, reprompt);
		} else {
			throw new SpeechletException("Invalid Intent");
		}
	}



	public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {
		log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
	}
	
	
	
	private SpeechletResponse getWelcomeMessage(){
		return null; 
	}
	
	
	private SpeechletResponse welcomeMessage(){
		
		return null;
	}	
}
