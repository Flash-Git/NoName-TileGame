package dev.flash.tilegame.menu;

import dev.flash.tilegame.Handler;


public class Menu {
	
	private Handler handler;
	
	public Menu(Handler handler) {
		this.setHandler(handler);
	}
	
	public Handler getHandler() {
		return handler;
	}
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
}
