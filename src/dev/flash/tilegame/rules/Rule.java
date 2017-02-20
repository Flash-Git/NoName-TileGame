package dev.flash.tilegame.rules;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.timers.Timer;

public class Rule {
	protected Handler handler;
	protected String name;
	protected int intVar;
	protected boolean boolVar;
	
	//delay for switching rule variables
	protected Timer ruleTimer;
	
	public Rule(Handler handler, String name, int intVar) {
		this.handler = handler;
		this.name = name;
		this.intVar = intVar;
		handler.getTimerManager().addToAddList(ruleTimer = new Timer(100));
		ruleTimer.isGlobal();//TEMP TODO
	}
	
	public Rule(Handler handler, String name, boolean boolVar) {
		this.handler = handler;
		this.name = name;
		this.boolVar = boolVar;
		handler.getTimerManager().addToAddList(ruleTimer = new Timer(100));
		ruleTimer.isGlobal();//TEMP TODO
	}
	
	public Handler getHandler() {
		return handler;
	}
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Timer getRuleTimer() {
		return ruleTimer;
	}
	
	public void setRuleTimer(Timer ruleTimer) {
		this.ruleTimer = ruleTimer;
	}
	
	public int getIntVar() {
		return intVar;
	}
	
	public boolean getBoolVar() {
		return boolVar;
	}
	
	public void setIntVar(int i) {
		if (ruleTimer.isDone()) {//TODO
			intVar = i;
		}
	}
	
	public void setBoolVar(boolean b) {
		if (ruleTimer.isDone()) {
			boolVar = b;
		}
	}
	
	public void swapBoolVar() {
		if (ruleTimer.isDone()) {
			boolVar = !boolVar;
		}
	}
	
	
}
