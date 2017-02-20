package dev.flash.tilegame.states;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.input.InputManager;
import dev.flash.tilegame.ui.MenuUserInterface;
import dev.flash.tilegame.ui.UserInterface;

import java.awt.*;

public class MenuState extends State {
	
	public MenuState(Handler handler) {
		super(handler);
		
		
		setUserInterface(new MenuUserInterface(handler));
		setInputManager(new InputManager(handler));
		
	}
	
	@Override
	public void tick(double delta) {
		userInterface.tick();
	}
	
	@Override
	public void render(Graphics g) {
		userInterface.render(g);
	}
	
	@Override
	public UserInterface getUserInterface() {
		return userInterface;
	}
	
	@Override
	public void setUserInterface(UserInterface userInterface) {
		this.userInterface = userInterface;
		handler.setUserInterface(userInterface);
		
	}
	
	@Override
	public InputManager getInputManager() {
		return inputManager;
	}
	
	@Override
	protected void setInputManager(InputManager inputManager) {
		this.inputManager = inputManager;
		handler.getKeyManager().setInputManager(inputManager);
	}
}
