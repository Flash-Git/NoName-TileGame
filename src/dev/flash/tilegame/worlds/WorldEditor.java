package dev.flash.tilegame.worlds;

import dev.flash.tilegame.Handler;

import java.awt.*;

public class WorldEditor {
	
	private Handler handler;
	
	public WorldEditor(Handler handler) {
		this.handler = handler;
		handler.setWorldEditor(this);
	}
	
	public void tick(double delta) {
		useInput();
	}
	
	public void render(Graphics g) {
		
	}
	
	//INPUT
	
	public void keyPressed() {
		getInput();
	}
	
	public void keyReleased() {
		getInput();
	}
	
	private void useInput() {
		
	}
	
	private void getInput() {
		
	}
	
	//Getters and setters
	
	public Handler getHandler() {
		return handler;
	}
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	
}
