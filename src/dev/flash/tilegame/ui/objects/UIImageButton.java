package dev.flash.tilegame.ui.objects;

import dev.flash.tilegame.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIImageButton extends UIObject {
	
	private BufferedImage[] images;
	private ClickListener clicker;
	
	public UIImageButton(Handler handler, float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker) {
		super(handler, x, y, width, height);
		this.images = images;
		this.clicker = clicker;
	}
	
	public ClickListener getClicker() {
		return clicker;
	}
	
	public void setClicker(ClickListener clicker) {
		this.clicker = clicker;
	}
	
	@Override
	public void tick() {
	}
	
	@Override
	public void render(Graphics g) {
		if (hovering) {
			g.drawImage(images[1], (int) x, (int) y, width, height, null);
		} else {
			g.drawImage(images[0], (int) x, (int) y, width, height, null);
		}
	}
	
	@Override
	public void onClick(int button) {
		clicker.onClick(button);
	}
	
	@Override
	public void pressed(int button) {
		clicker.pressed(button);
	}
	
	public BufferedImage[] getImages() {
		return images;
	}
	
	public void setImages(BufferedImage[] images) {
		this.images = images;
	}
	
}
