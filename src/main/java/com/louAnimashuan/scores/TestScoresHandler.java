package com.louAnimashuan.scores;

import java.util.HashSet;
import java.util.Set;


import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;


public final class TestScoresHandler extends SpeechletRequestStreamHandler{

	private static final Set<String> supportedApplicationIds;
    
    static {
    	supportedApplicationIds = new HashSet<String>();
    }
    
    public TestScoresHandler(){
    	super(new TestScoresSpeechlet(), supportedApplicationIds);
    }
}

