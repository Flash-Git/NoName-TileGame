package dev.flash.tilegame.entities.units.buildings;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.entities.projectiles.Fireball;
import dev.flash.tilegame.gfx.Animation;
import dev.flash.tilegame.gfx.Assets;

import java.awt.*;

public class MageTower extends Building {
	
	public MageTower(Handler handler, float x, float y, int team) {
		super(handler, x, y, Building.DEFAULT_WIDTH, Building.DEFAULT_HEIGHT * 2, team);
		
		uiCommandBox = handler.getGameUserInterface().getUiMagetowerCommandBox();
	}
	
	@Override
	protected void getAssets() {
		animIdle = new Animation(1000, Assets.mageTower_idle);
		animDead = new Animation(1000, Assets.tower_dead);
	}
	
	//VARIABLES
	@Override
	protected void initialiseVariables() {
		super.initialiseVariables();
		ranged = true;
		melee = false;
		cost = 300;
		type = "Mage Tower";
		bounds = new Rectangle(8, 12, 16, 20 + 32);
	}
	
	@Override
	protected void setBaseVariables() {
		super.setBaseVariables();
		baseHealth = 15;
		baseHealthRegen = 0.5f;
		baseDamage = 2f;
		baseAttackSpeed = 1f;
		baseAccuracy = 100;
		baseAttackRange = 10;
		baseDetectionRange = 10;
		baseSpeed = 0f;
		baseLifeTime = 0;
		baseDeathTime = 10000;
		baseIdleTime = 0;
	}
	
	@Override
	protected void setScalingVariables() {
		super.setScalingVariables();
		scalingHealth = 3f;
		scalingHealthRegen = 0.1f;
		scalingDamage = 1f;
		scalingAttackSpeed = 0.1f;
		scalingAccuracy = 10;
		scalingAttackRange = 0;
		scalingDetectionRange = 0;
		scalingSpeed = 0f;
		scalingLifeTime = 0;
		scalingDeathTime = 0;
		scalingIdleTime = 0;
	}
	
	@Override
	public void tick(double delta) {
		super.tick(delta);
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
	}
	
	@Override
	protected void fireProjectile(int tX, int tY) {
		Fireball fireball = new Fireball(handler, getCenterX(), getCenterY(), damage, this, tX, tY);
		fireball.setLifeTime(3000);
		fireball.getLifeTimer().setDelay(3000);//TODO timers
		fireball.setX(fireball.getX() - 6);//TODO
		fireball.setY(fireball.getY() - 6);
		fireball.setWidth(fireball.getWidth() * 2);
		fireball.setHeight(fireball.getHeight() * 2);
		fireball.getBounds().setBounds(fireball.getWidth() / 2 - 6, fireball.getHeight() / 2 - 6, 12, 12);
		handler.getEntityManager().addToAddList(fireball);
	}
	
	
}
