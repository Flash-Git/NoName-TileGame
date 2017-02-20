package dev.flash.tilegame.entities.projectiles;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.entities.units.Unit;
import dev.flash.tilegame.gfx.Animation;
import dev.flash.tilegame.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Fireball extends Projectile {
	
	//Animations
	protected Animation animUp;
	protected Animation animDown;
	protected Animation animRight;
	protected Animation animLeft;
	
	public Fireball(Handler handler, float x, float y, float damage, Unit shooter, int tX, int tY) {
		super(handler, x, y, Projectile.DEFAULT_WIDTH, Projectile.DEFAULT_HEIGHT, damage, shooter, tX, tY);
		
		//Animations
		animUp = new Animation(40, Assets.projectile_fireball_up);
		animDown = new Animation(40, Assets.projectile_fireball_down);
		animLeft = new Animation(40, Assets.projectile_fireball_left);
		animRight = new Animation(40, Assets.projectile_fireball_right);
	}
	
	@Override
	protected void setBaseVariables() {
		super.setBaseVariables();
		baseDeathTime = 1500;
	}
	
	@Override
	public void tick(double delta) {
		super.tick(delta);
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
//		g.setColor(Color.RED);
//		g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y-10 - handler.getGameCamera().getyOffset()), (int) ((this.health*this.width)/this.maxHealth), 4);
	}
	
	@Override
	public void die() {//TODO
		super.die();
		setWidth(width * 2);
		setHeight(height * 2);
		setX(x - width / 2);
		setY(y - height / 2);
		getBounds().setBounds(width / 2 - 12, height / 2 - 12, 24, 24);
	}
	
	@Override
	protected void animate() {
		animUp.tick();
		animDown.tick();
		animLeft.tick();
		animRight.tick();
	}
	
	
	@Override
	protected BufferedImage getCurrentAnimationFrame() {
		if (alive) {
			if (vX > 0) {
				if (vX > Math.abs(vY)) {
					return animRight.getCurrentFrame();
				} else {
					if (vY < 0) {
						return animUp.getCurrentFrame();
					} else {
						return animDown.getCurrentFrame();
					}
				}
			} else {
				if (Math.abs(vX) > Math.abs(vY)) {
					return animLeft.getCurrentFrame();
				} else {
					if (vY < 0) {
						return animUp.getCurrentFrame();
					} else {
						return animDown.getCurrentFrame();
					}
				}
			}
		} else {
			return Assets.heart;
		}
	}
	
}
