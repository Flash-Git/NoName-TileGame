package dev.flash.tilegame.ui.objects;

import java.awt.*;

public class Text {
	
	private String string;
	private int x, y;
	private Color color;
	private float size;
	
	public Text(String string, int x, int y, Color color, float size) {
		this.string = string;
		this.x = x;
		this.y = y;
		this.color = color;
		this.size = size;
	}
	
	public String getString() {
		return string;
	}
	
	public void setString(String string) {
		this.string = string;
	}
	
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
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public float getSize() {
		return size;
	}
	
	public void setSize(float size) {
		this.size = size;
		
	}
	
}
