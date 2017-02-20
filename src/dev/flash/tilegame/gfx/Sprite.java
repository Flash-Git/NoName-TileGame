package dev.flash.tilegame.gfx;

import java.awt.image.BufferedImage;

public class Sprite {
	
	private BufferedImage[] images;
	private String name;
	private int width;
	private int height;
	
	public Sprite(BufferedImage[] images, String name, int width, int height) {
		this.name = name;
		this.images = images;
		this.width = width;
		this.height = height;
	}
	
	public Sprite(BufferedImage image, String name, int width, int height) {
		this.name = name;
		images = new BufferedImage[1];
		images[0] = image;
		this.width = width;
		this.height = height;
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public BufferedImage[] getFrames() {
		return images;
	}
	
	public int getLength() {
		return images.length;
	}
	
	
}
