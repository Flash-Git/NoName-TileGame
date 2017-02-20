package dev.flash.tilegame.worlds;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.gfx.Assets;
import dev.flash.tilegame.gfx.Sprite;
import dev.flash.tilegame.timers.Timer;

import java.awt.*;
import java.util.ArrayList;

public class SpriteViewerWorld {//massive mess but works flawlessly
	
	private Handler handler;
	private ArrayList<Sprite> sprites;
	private int iterator = 0;
	private int frame = 0;
	private Sprite sprite;
	
	private Timer timer;
	private Timer getInputTimer;
	private Timer useInputTimer;
	
	private int playSpeed = 150;
	
	private boolean play = false;
	
	private boolean right;
	private boolean left;
	private int zoom = 1;
	private Color color = Color.gray;
	
	public SpriteViewerWorld(Handler handler) {
		this.handler = handler;
		handler.setSpriteViewerWorld(this);
		sprites = Assets.getSprites();
		sprite = sprites.get(0);
		
		timer = new Timer(playSpeed);
		useInputTimer = new Timer(150);
		getInputTimer = new Timer(150);
	}
	
	
	public void tick(double delta) {
		
		//Input
		timer.tick(delta);
		getInputTimer.tick(delta);
		useInputTimer.tick(delta);
		useInput();
		if (play) {
			if (timer.isDone()) {
				nextFrame();
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(handler.getWidth() / 2 - 128 - 64, handler.getHeight() / 2 - 128, 256 + 128, 256);
		g.setColor(Color.black);
		sprite = sprites.get(iterator);
		g.drawImage(sprite.getFrames()[frame], handler.getWidth() / 2 - sprite.getWidth() * zoom / 2, handler.getHeight() / 2 - sprite.getHeight() * zoom / 2, sprite.getWidth() * zoom, sprite.getHeight() * zoom, null);
		
		g.fillRect(handler.getWidth() / 2 - 1, handler.getHeight() / 2 - 128, 2, 256);
		g.fillRect(handler.getWidth() / 2 - 128 - 64, handler.getHeight() / 2 - 1, 256 + 128, 2);
		
		g.fillRect(handler.getWidth() / 2 - 17, handler.getHeight() / 2 - 128, 2, 256);
		g.fillRect(handler.getWidth() / 2 + 15, handler.getHeight() / 2 - 128, 2, 256);
		
		g.fillRect(handler.getWidth() / 2 - 128 - 64, handler.getHeight() / 2 - 17, 256 + 128, 2);
		g.fillRect(handler.getWidth() / 2 - 128 - 64, handler.getHeight() / 2 + 15, 256 + 128, 2);
		
		g.fillRect(handler.getWidth() / 2 - 49, handler.getHeight() / 2 - 128, 2, 256);
		g.fillRect(handler.getWidth() / 2 + 47, handler.getHeight() / 2 - 128, 2, 256);
		
		g.fillRect(handler.getWidth() / 2 - 128 - 64, handler.getHeight() / 2 - 49, 256 + 128, 2);
		g.fillRect(handler.getWidth() / 2 - 128 - 64, handler.getHeight() / 2 + 47, 256 + 128, 2);
	}
	
	public void zoom() {
		zoom -= 1;
		zoom += 1;
		zoom = zoom % 3;
		zoom += 1;
	}
	
	public void changeBColor() {
		if (color.equals(Color.gray)) {
			color = Color.DARK_GRAY;
			return;
		}
		if (color.equals(Color.DARK_GRAY)) {
			color = Color.white;
			return;
		}
		if (color.equals(Color.white)) {
			color = Color.gray;
			return;
		}
	}
	
	public void nextSprite() {
		if (iterator < sprites.size() - 1) {
			sprite = sprites.get(iterator += 1);
		} else {
			sprite = sprites.get(iterator = 0);
		}
		frame = 0;
	}
	
	public void lastSprite() {
		if (iterator > 0) {
			sprite = sprites.get(iterator -= 1);
		} else {
			sprite = sprites.get(iterator = sprites.size() - 1);
		}
		frame = 0;
	}
	
	public void nextFrame() {
		if (frame < sprite.getLength() - 1) {
			frame = frame + 1;
		} else {
			frame = 0;
		}
	}
	
	public void lastFrame() {
		if (frame > 0) {
			frame = frame - 1;
		} else {
			frame = sprite.getLength() - 1;
		}
	}
	
	//INPUT
	
	public void keyPressed() {
		getInput();
	}
	
	public void keyReleased() {
		getInput();
	}
	
	private void useInput() {
		if (left) {
			if (useInputTimer.isDone()) {
				lastFrame();
			}
		}
		
		if (right) {
			if (useInputTimer.isDone()) {
				nextFrame();
			}
		}
	}
	
	private void getInput() {
		if (handler.getKeyManager().left) {
			left = true;
			play = false;
			if (useInputTimer.isDone()) {//prevents commands getting skipped
				lastFrame();
			}
		} else {
			left = false;
		}
		
		if (handler.getKeyManager().right) {
			right = true;
			play = false;
			if (useInputTimer.isDone()) {//prevents commands getting skipped
				nextFrame();
			}
		} else {
			right = false;
		}
		
		if (getInputTimer.isDone()) {
			if (handler.getKeyManager().space) {
				play = !play;
			}
		}
	}
	//Getters and setters
	
	
	public Sprite getSprite() {
		return sprite;
	}
	
	
}
