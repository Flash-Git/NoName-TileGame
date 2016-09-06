package dev.flash.tilegame.rules;

import java.util.ArrayList;

import dev.flash.tilegame.Handler;

public class RuleManager {

	private Handler handler;
	private ArrayList<Rule> rules;
	
	public RuleManager(Handler handler){
		this.handler = handler;
		rules = new ArrayList<Rule>();
	}

	public void addRule(Rule r){
		rules.add(r);
	}
	
	//getters and setter
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<Rule> getRules() {
		return rules;
	}

	public void setRules(ArrayList<Rule> rules) {
		this.rules = rules;
	}
	
	public Rule getRule(String name){
		for(Rule r : rules){
			if(r.getName().equals(name)){
				return r;
			}
		}return null;
	}
}
