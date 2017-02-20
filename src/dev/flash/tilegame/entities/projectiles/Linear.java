package dev.flash.tilegame.entities.projectiles;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.entities.units.Unit;
import dev.flash.tilegame.gfx.Animation;
import dev.flash.tilegame.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Linear extends Projectile {
	//Animations
	protected Animation animHori;
	protected Animation animVert;
	
	public Linear(Handler handler, float x, float y, float damage, Unit shooter, int tX, int tY) {
		super(handler, x, y, Projectile.DEFAULT_WIDTH, Projectile.DEFAULT_HEIGHT, damage, shooter, tX, tY);
		
		//Animations
		this.animVert = new Animation(40, Assets.projectile_vertical);
		this.animHori = new Animation(40, Assets.projectile_horizontal);
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
	protected void animate() {
		animVert.tick();
		animHori.tick();
	}
	
	@Override
	protected BufferedImage getCurrentAnimationFrame() {
		if (Math.abs(getvX()) > Math.abs(vY)) {
			return animHori.getCurrentFrame();
		} else {
			return animVert.getCurrentFrame();
		}
	}
	
}
