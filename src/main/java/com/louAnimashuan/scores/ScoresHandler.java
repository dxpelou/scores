package com.louAnimashuan.scores;

import java.util.HashSet;
import java.util.Set;


import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;


public final class ScoresHandler extends SpeechletRequestStreamHandler{

	private static final Set<String> supportedApplicationIds;
    
    static {
    	supportedApplicationIds = new HashSet<String>();
    }
    
    public ScoresHandler(){
    	super(new ScoresSpeechlet(), supportedApplicationIds);
    }
}
