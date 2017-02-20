package dev.flash.tilegame.tiles;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.entities.Entity;
import dev.flash.tilegame.timers.Timer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Chunk {
	
	Handler handler;
	int x, y, width, height;
	ArrayList<Entity> entities;
	public boolean selected = false;
	
	public Chunk(Handler handler, int x, int y, int width, int height) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		entities = new ArrayList<Entity>();
		handler.getTimerManager().addToAddList(selectTimer = new Timer(5000));
	}
	
	Timer selectTimer;
	
	public void render(Graphics g) {
		Random random = new Random();
		int rand = random.nextInt(5);
		if (rand < 1)
			g.setColor(Color.BLACK);
		if (rand == 2)
			g.setColor(Color.BLUE);
		if (rand == 3)
			g.setColor(Color.RED);
		if (rand == 4)
			g.setColor(Color.YELLOW);
		
		if (selected) {
			g.fillRect(x - (int) handler.getGameCamera().getxOffset(), y - (int) handler.getGameCamera().getyOffset(), width, height);
			if (selectTimer.isDone()) {
				selected = false;
			}
		}
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	public void removeEntity(Entity e) {
		entities.remove(e);
	}
	
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}
	
	//Getters and Setters
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
