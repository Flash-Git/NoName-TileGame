package dev.flash.tilegame.ui.objects;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.input.MouseManager;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class UIObject {
	
	protected Handler handler;
	
	protected float x, y;
	protected int width, height;
	protected Rectangle bounds;
	protected boolean hovering = false;
	protected MouseManager mouseManager;
	protected String name;
	
	public UIObject(Handler handler, float x, float y, int width, int height) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle((int) x, (int) y, width, height);
		mouseManager = handler.getMouseManager();
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void onClick(int button);
	
	public abstract void pressed(int button);
	
	
	public void onMouseMove(MouseEvent e) {
		System.out.println(e.getX() + " " + e.getY());
		hovering = bounds.contains(e.getX(), e.getY());
	}
	
	public void onMousePressed(MouseEvent e, int button) {
		if (hovering) {
			hovering = false;
			pressed(button);
		}
		//onMouseMove(e);
		hovering = false;
	}
	
	
	public void onMouseRelease(MouseEvent e, int button) {
		if (hovering) {
			onClick(button);
		}
		//onMouseMove(e);
		hovering = false;
	}
	
	//GETTERS AND SETTERS
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
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
	
	public boolean isHovering() {
		return hovering;
	}
	
	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
}
