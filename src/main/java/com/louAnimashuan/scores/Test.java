package com.louAnimashuan.scores;

import java.util.HashMap;
import java.util.Map;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.slu.Intent.Builder;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;

public class Test {

	public static void main(String[] args){
		Test test = new Test();
		
		test.buildIntent();
		
	}
	
	public IntentRequest buildIntentRequest(){
		return null;
	}
	
	public void  buildIntent(){
	Slot homeSlot = Slot.builder().withName("Home").withValue("Chelsea").build();
	Slot awaySlot = Slot.builder().withName("Away").withValue("Arsenal").build();
	
	Map slots = new HashMap();
	slots.put("Home", homeSlot);
	slots.put("Away", awaySlot);
	
	Intent intent = Intent.builder().withName("getScoresIntent").withSlots(slots).build();
	IntentRequest request = IntentRequest.builder().withIntent(intent).withRequestId("000000000").build();
	
	Session session = Session.builder().withSessionId("000000x0000000").build();
	System.out.println(" " +" Session id: " + request.getRequestId() + " AwayTeam: " +request.getIntent().getSlot("Home").getValue()  +" Away Team: " +request.getIntent().getSlot("Away").getValue() + " Session ID:" +session.getSessionId() );
	}
	
}
