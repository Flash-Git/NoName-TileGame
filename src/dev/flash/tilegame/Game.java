package dev.flash.tilegame;

import dev.flash.tilegame.display.Display;
import dev.flash.tilegame.gfx.Assets;
import dev.flash.tilegame.gfx.GameCamera;
import dev.flash.tilegame.input.KeyManager;
import dev.flash.tilegame.input.MouseManager;
import dev.flash.tilegame.rules.Rule;
import dev.flash.tilegame.rules.RuleManager;
import dev.flash.tilegame.states.GameState;
import dev.flash.tilegame.states.MenuState;
import dev.flash.tilegame.states.SpriteViewerState;
import dev.flash.tilegame.states.State;
import dev.flash.tilegame.timers.TimerManager;

import java.awt.*;
import java.awt.image.BufferStrategy;


public class Game implements Runnable {
	//display handles JFrame
	private Display display;
	private BufferStrategy bs;
	private Graphics g;
	
	//used for fps counter
	private int fps;
	
	//total number of ticks
	public static int tickCount;
	
	//game variables
	private int width, height;
	private String title;
	private boolean running;
	private Thread thread;
	
	//Input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	private RuleManager ruleManager;
	private TimerManager timerManager;
	
	//Camera
	private GameCamera gameCamera;
	
	//States
	private State gameState;
	private State menuState;
	private State spriteViewerState;
	
	//Handler
	private Handler handler;
	
	public Game(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
		ruleManager = new RuleManager();//putting this in game over world, so restarts don't touch the rules
		timerManager = new TimerManager();
	}
	
	private void init() {
		handler = new Handler(this);
		keyManager.setHandler(handler);
		//Initial rules TODO import from file
		ruleManager.addRule(new Rule(handler, "world", 1));//is paused
		Rule pausedRule = new Rule(handler, "paused", false);
		pausedRule.getRuleTimer().setGlobal(true);
		ruleManager.addRule(pausedRule);//is paused
		
		Rule frKeyboardRule = new Rule(handler, "frKeyboard", false);
		//frKeyboardRule.getRuleTimer().setGlobal(true);
		ruleManager.addRule(frKeyboardRule);
		
		ruleManager.addRule(new Rule(handler, "bounds", false));//show bounds
		ruleManager.addRule(new Rule(handler, "checkNum", 999));//how many cells entities will check to reach goal before giving up
		ruleManager.addRule(new Rule(handler, "entity collision", true));//allows entities to collide//TODO actually make use of this
		ruleManager.addRule(new Rule(handler, "score", 0));//initial score
		
		//Create window
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		
		//Load the game's assets
		Assets.init();
		
		gameCamera = new GameCamera(handler, 0, 0);
		
		//Setting up the program's States
		menuState = new MenuState(handler);
		gameState = new GameState(handler);
		spriteViewerState = new SpriteViewerState(handler);
		State.setState(menuState);
	}
	
	private void tick(double delta) {
		keyManager.updateKeys();//TODO this is more of a failsafe than actually necessary
		if (State.getState() != null) {
			State.getState().tick(delta);
		}
	}
	
	private void render() {
		//Draw frames before displaying them
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(2);//amount of stored up frames ready before pushing to screen
			return;
		}
		g = bs.getDrawGraphics();
		
		//Clear Screen
		g.clearRect(0, 0, width, height);
		
		//Draw Here
		if (State.getState() != null) {
			State.getState().render(g);
		}
		
		//End Draw
		bs.show();
		g.dispose();
	}
	
	@Override
	public void run() {
		init();
		int targetFps = 144;
		double timePerTick = 1000000000 / targetFps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		long deltaTime;//better to have this be a long that converts to int or pass a long into tick()?
		long deltaLastTime = System.nanoTime();
		long deltaNow;
		
		
		while (running) {
			now = System.nanoTime();
			timer += now - lastTime;
			delta += (now - lastTime) / timePerTick;
			
			lastTime = now;
			
			if (delta >= 1) {//this should avoid lost or gained frames from speeding up or slowing down the game
				deltaNow = System.nanoTime();
				deltaTime = deltaNow - deltaLastTime;
				
				tick(deltaTime / 1000000);//converts nano to milli
				render();
				ticks++;
				delta--;
				tickCount++;
				deltaLastTime = deltaNow;
			}
			
			if (timer >= 1000000000) {
				System.out.println("Ticks and Frames: " + ticks);
				fps = ticks;
				ticks = 0;
				timer = 0;
			}
		}
		stop();
	}
	
	//Creates the thread
	public synchronized void start() {
		if (running == true) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	//Stops the code cleanly
	public synchronized void stop() {
		if (!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Getters and Setters
	public State getGameState() {
		return gameState;
	}
	
	public void setGameState(State gameState) {
		this.gameState = gameState;
	}
	
	public State getMenuState() {
		return menuState;
	}
	
	public void setMenuState(State menuState) {
		this.menuState = menuState;
	}
	
	public State getSpriteViewerState() {
		return spriteViewerState;
	}
	
	public void setSpriteViewerState(State spriteViewerState) {
		this.spriteViewerState = spriteViewerState;
	}
	
	public int getFPS() {
		return fps;
	}
	
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	public MouseManager getMouseManager() {
		return mouseManager;
	}
	
	public GameCamera getGameCamera() {
		return gameCamera;
	}
	
	public RuleManager getRuleManager() {
		return ruleManager;
	}
	
	public TimerManager getTimerManager() {
		return timerManager;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
}
