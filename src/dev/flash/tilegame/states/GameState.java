package dev.flash.tilegame.states;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.input.InputManager;
import dev.flash.tilegame.ui.GameUserInterface;
import dev.flash.tilegame.ui.UserInterface;
import dev.flash.tilegame.worlds.World;

import java.awt.*;

public class GameState extends State {
	
	private World world;
	
	public GameState(Handler handler) {
		super(handler);
		setUserInterface(new GameUserInterface(handler));
		//inputManager = null;
		world = new World(handler, "res/worlds/world3.txt");
		handler.setWorld(world);
		setInputManager(new InputManager(handler));
	}
	
	@Override
	public void tick(double delta) {
		world.tick(delta);
		userInterface.tick();
		
		if (handler.getKeyManager().m) {
			handler.getRuleManager().getRule("paused").setBoolVar(true);
			userInterface.setUIManager(((GameUserInterface) userInterface).getPauseUIManager());//TODO
		}
		if (handler.getKeyManager().r) {
			handler.getRuleManager().getRule("world").setIntVar(handler.getRuleManager().getRule("world").getIntVar() + 1);
			int inc = handler.getRuleManager().getRule("world").getIntVar() % 4;
			world = new World(handler, "res/worlds/world" + inc + ".txt");
		}
	}
	
	@Override
	public void render(Graphics g) {
		world.render(g);
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