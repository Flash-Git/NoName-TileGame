package dev.flash.tilegame.states;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.input.InputManager;
import dev.flash.tilegame.ui.SpriteViewerUserInterface;
import dev.flash.tilegame.ui.UserInterface;
import dev.flash.tilegame.worlds.SpriteViewerWorld;

import java.awt.*;

public class SpriteViewerState extends State {
	
	private SpriteViewerWorld world;
	
	public SpriteViewerState(Handler handler) {
		super(handler);
		setUserInterface(new SpriteViewerUserInterface(handler));
		world = new SpriteViewerWorld(handler);
		setInputManager(new InputManager(handler));
	}
	
	@Override
	public void tick(double delta) {
		userInterface.tick();
		
		if (handler.getKeyManager().m) {
			handler.getRuleManager().getRule("paused").setBoolVar(true);
			userInterface.setUIManager(((SpriteViewerUserInterface) userInterface).getSpriteMenuUIManager());//TODO
		}
		if (((SpriteViewerUserInterface) userInterface).getSpriteMenuUIManager().equals(userInterface.getUIManager())) {//TODO TEMP
			return;
		}
		world.tick(delta);
	}
	
	@Override
	public void render(Graphics g) {
		userInterface.render(g);
		if (((SpriteViewerUserInterface) userInterface).getSpriteMenuUIManager().equals(userInterface.getUIManager())) {//TODO TEMP
			return;
		}
		world.render(g);
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
