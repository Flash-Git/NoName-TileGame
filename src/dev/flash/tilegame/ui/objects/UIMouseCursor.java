package dev.flash.tilegame.ui.objects;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.gfx.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIMouseCursor extends UIObject {
	
	private ClickListener clicker;
	protected Animation animCursor;
	protected int commandNum;
	
	public UIMouseCursor(Handler handler, float x, float y, int width, int height, BufferedImage[] images, String name, ClickListener clicker) {
		super(handler, x, y, width, height);
		this.handler = handler;
		this.clicker = clicker;
		this.animCursor = new Animation(400, images);
		commandNum = 0;
		this.name = name;
	}
	
	@Override
	public void tick() {
		bounds.setBounds((int) x, (int) y, width, height);
		animCursor.tick();
		if (mouseManager.isLeftPressed()) {
			pressed(1);
		}
		if (mouseManager.isRightPressed()) {
			pressed(3);
		}
		doTick();
	}
	
	//Method created to be overwritten
	public void doTick() {
		//template method, is empty
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(animCursor.getCurrentFrame(), (int) x, (int) y, width, height, null);
	}
	
	@Override
	public void onClick(int button) {
		clicker.onClick(button);
	}
	
	@Override
	public void pressed(int button) {
		clicker.pressed(button);
	}
	
	protected BufferedImage getCurrentAnimationFrame() {
		return animCursor.getCurrentFrame();
	}
	
	public void setCommandNum(int commandNum) {
		this.commandNum = commandNum;
	}
	
	public int getCommandNum() {
		return commandNum;
	}
}
