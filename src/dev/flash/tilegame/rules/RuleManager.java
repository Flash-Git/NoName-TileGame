package dev.flash.tilegame.rules;

import java.util.ArrayList;

public class RuleManager {
	
	private ArrayList<Rule> rules;
	
	public RuleManager() {
		rules = new ArrayList<Rule>();
	}
	
	public void addRule(Rule r) {
		rules.add(r);
	}
	
	//getters and setter
	public ArrayList<Rule> getRules() {
		return rules;
	}
	
	public void setRules(ArrayList<Rule> rules) {
		this.rules = rules;
	}
	
	public Rule getRule(String name) {
		for (Rule r : rules) {
			if (r.getName().equals(name)) {
				return r;
			}
		}
		return null;
	}
}
