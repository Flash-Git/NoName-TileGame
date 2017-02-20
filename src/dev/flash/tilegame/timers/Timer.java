package dev.flash.tilegame.timers;

public class Timer {//TODO fix all waves, make a note of where every timer is
	
	private double delay;
	private double elapsedTime;
	private boolean active;
	
	private boolean global;
	
	//CONSTRUCTORS
	public Timer(double delay) {
		this.delay = delay;
		elapsedTime = 0;
		active = true;
	}
	
	public Timer(boolean active) {
		this.active = active;
	}
	
	
	public void tick(double delta) {
		if (active) {
			elapsedTime = delta + elapsedTime;
		}
	}
	
	public void reset() {
		elapsedTime = 0;
	}
	
	
	public boolean isDone() {
		if (active == false) {
			return false;
		}
		if (elapsedTime > delay) {
			elapsedTime = 0;
			return true;
		} else {
			return false;
		}
	}
	
	public int getProgress() {
		return (int) (elapsedTime / delay);
	}
	
	public double getDelay() {
		return delay;
	}
	
	public void setDelay(double delay) {
		this.delay = delay;
		if (delay == 0) {
			deactivate();
			return;
		}
		if (delay != 0) {
			activate();
			return;
		}
	}
	
	public void activate() {
		active = true;
	}
	
	public void deactivate() {
		active = false;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public boolean isGlobal() {
		return global;
	}
	
	public void setGlobal(boolean global) {
		this.global = global;
	}
	
}
