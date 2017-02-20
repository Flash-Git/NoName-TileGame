package dev.flash.tilegame.input;

import dev.flash.tilegame.Handler;

public class InputManager {
	
	private Handler handler;
	
	public InputManager(Handler handler) {
		this.handler = handler;
	}
	
	
	public void keyPressed() {
		handler.getSpriteViewerWorld().keyPressed();
	}
	
	
	public void keyReleased() {
		handler.getSpriteViewerWorld().keyReleased();
	}
	
	
	public Handler getHandler() {
		return handler;
	}
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	
}