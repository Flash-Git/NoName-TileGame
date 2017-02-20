package dev.flash.tilegame.entities.units.buildings;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.entities.EntityVisitor;
import dev.flash.tilegame.entities.units.Unit;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Building extends Unit {
	
	protected static final int DEFAULT_WIDTH = 32;
	protected static final int DEFAULT_HEIGHT = 32;
	
	public Building(Handler handler, float x, float y, int width, int height, int team) {
		super(handler, x, y, width, height, team);
		bounds = new Rectangle(0, 0, width, height);
	}
	
	@Override
	protected void initialiseVariables() {
		super.initialiseVariables();
		
		cost = 100;
		
		type = "Building";
	}
	
	@Override
	protected void setBaseVariables() {
		super.setBaseVariables();
		baseSpeed = 0;
		baseIdleTime = 0;
	}
	
	@Override
	protected void setScalingVariables() {
		super.setScalingVariables();
	}
	
	@Override
	public void setLevel(int level) {
		super.setLevel(level);
	}
	
	@Override
	public void tick(double delta) {
		super.tick(delta);
	}
	
	@Override
	public void render(Graphics g) {
		if (alive) {
			//health bars
			g.setColor(Color.RED);
			g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - 5 - handler.getGameCamera().getyOffset()),
					(int) ((health * width) / maxHealth), 4);
			
			//Lvl
			g.setColor(Color.BLACK);
			g.drawString("Lvl " + Integer.toString(level), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - 7 - handler.getGameCamera().getyOffset()));
			
			//Target
			if (targetIsUnit) {
				g.setColor(Color.RED);
				g.fillRect((int) (getCenterX() - 2 - handler.getGameCamera().getxOffset()), (int) (y - 16 - handler.getGameCamera().getyOffset()), 4, 8);
			}
		}
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}
	
	@Override
	public void accept(EntityVisitor visitor) {
		visitor.visit(this);
	}
	
	//ANIMATION
	
	@Override
	protected void animate() {
		if (alive) {
			animIdle.tick();
		} else {
			animDead.tick();
		}
	}
	
	//MOVEMENT
	
	@Override
	protected void idle(double delta) {
		
	}
	
	@Override
	public void command(double delta) {
		doCommandAction();
	}
	
	protected boolean collisionWithTile(int x, int y) {
		return handler.getWorld().getTile(x, y).isSolid();
	}
	
	protected BufferedImage getCurrentAnimationFrame() {
		if (alive) {
			return animIdle.getCurrentFrame();
		}
		return animDead.getCurrentFrame();
	}
	
	@Override
	public void onKill(int expReward) {
	}
	
	public int getExpReward() {
		return this.level * 40;//TODO temp
	}
}
