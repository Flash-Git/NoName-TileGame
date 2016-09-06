package dev.flash.tilegame.timers;

import java.util.ArrayList;
import dev.flash.tilegame.Handler;

public class TimerManager {

	private Handler handler;
	private ArrayList<Timer> timers;
	private ArrayList<Timer> toAdd;
	private ArrayList<Timer> toRemove;
	
	public TimerManager(Handler handler){//Maybe substitute handler for world
		this.handler = handler;
		timers = new ArrayList<Timer>();
		toAdd = new ArrayList<Timer>();
		toRemove = new ArrayList<Timer>();
	}
	
	public void tick(double delta){
		for(Timer t : timers){
			t.tick(delta);
		}
		if(!toAdd.isEmpty()){
			toAddTimers();
			toAdd.clear();
		}
		
		if(!toRemove.isEmpty()){
			toRemoveTimers();
			toRemove.clear();
		}
	}
	
	public void globalTick(double delta){
		for(Timer t : timers){
			if(t.isGlobal()){
				t.tick(delta);
			}
		}
		if(!toAdd.isEmpty()){
			toAddTimers();
			toAdd.clear();
		}
		
		if(!toRemove.isEmpty()){
			toRemoveTimers();
			toRemove.clear();
		}
	}
	
	private void toAddTimers(){
		for(Timer t : toAdd)
			addTimer(t);
	}
	
	private void toRemoveTimers(){
		for(Timer t : toAdd)
			removeTimer(t);
	}
	
	public void addToAddList(Timer t){
		toAdd.add(t);
	}
	
	public void addToAddList(ArrayList<Timer> timers) {
		for(Timer t : timers){
			toAdd.add(t);
		}
		
	}
	public void addToRemoveList(Timer t){
		toRemove.add(t);
	}
	
	public void addToRemoveList(ArrayList<Timer> timers) {
		for(Timer t : timers){
			toRemove.add(t);
		}		
	}
	
	private void addTimer(Timer t){
		timers.add(t);
	}
	
	public void removeTimer(Timer t){
		timers.remove(t);
	}
		
	//getters and setter
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}


	public ArrayList<Timer> getTimers() {
		return timers;
	}

	public void setTimers(ArrayList<Timer> timers) {
		this.timers = timers;
	}

	

	
}
