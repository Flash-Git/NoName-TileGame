package dev.flash.tilegame.ui.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.input.MouseManager;

public abstract class UIObject {

	protected float x, y;
	protected int width, height;
	protected Rectangle bounds;
	protected boolean hovering = false;
	protected Handler handler;
	protected MouseManager mouseManager;
	protected String name;
	
	public UIObject(Handler handler, float x, float y, int width, int height) {
		this.handler = handler;
		this.x= x;
		this.y= y;
		this.width= width;
		this.height= height;
		bounds = new Rectangle((int) x, (int) y, width, height);
		mouseManager = handler.getMouseManager();
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void onClick(int button);
	
	public abstract void pressed(int button);


	public void onMouseMove(MouseEvent e){
		if(bounds.contains(e.getX(), e.getY())){
			hovering = true;
		}else{
			hovering = false;
		}
	}
	
	public void onMousePressed(MouseEvent e, int button) {
		if(hovering){
			pressed(button);
		}
		//onMouseMove(e);
	}
	
	
	public void onMouseRelease(MouseEvent e, int button){
		if(hovering){
			hovering=false;
			onClick(button);
		}
		//onMouseMove(e);
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
