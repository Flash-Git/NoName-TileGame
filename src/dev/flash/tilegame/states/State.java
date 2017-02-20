package dev.flash.tilegame.states;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.input.InputManager;
import dev.flash.tilegame.ui.UserInterface;

import java.awt.*;

public abstract class State {
	protected UserInterface userInterface;
	
	protected InputManager inputManager;
	
	private static State currentState = null;
	
	//Class
	protected Handler handler;
	
	public State(Handler handler) {
		this.handler = handler;
	}
	
	public abstract void tick(double delta);
	
	public abstract void render(Graphics g);
	
	public static void setState(State state) {
		currentState = state;
		UserInterface.setUserInterface(currentState.getUserInterface());
	}
	
	public abstract InputManager getInputManager();
	
	protected abstract void setInputManager(InputManager inputManager);
	
	public static State getState() {
		return currentState;
	}
	
	public abstract UserInterface getUserInterface();
	
	public abstract void setUserInterface(UserInterface userInterface);
}
