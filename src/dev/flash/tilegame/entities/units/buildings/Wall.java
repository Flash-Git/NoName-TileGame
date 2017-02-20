package dev.flash.tilegame.entities.units.buildings;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.gfx.Animation;
import dev.flash.tilegame.gfx.Assets;

import java.awt.*;

public class Wall extends Building {
	
	public Wall(Handler handler, float x, float y, int team) {
		super(handler, x, y, Building.DEFAULT_WIDTH, Building.DEFAULT_HEIGHT, team);
		
		uiCommandBox = handler.getGameUserInterface().getUiWallCommandBox();
	}
	
	@Override
	protected void getAssets() {
		animIdle = new Animation(1000, Assets.tower_idle);
		animDead = new Animation(1000, Assets.tower_dead);
	}
	
	//VARIABLES
	@Override
	protected void initialiseVariables() {
		super.initialiseVariables();
		ranged = false;
		melee = false;
		cost = 200;
		type = "Wall";
		bounds = new Rectangle(0, 0, 32, 32);
	}
	
	@Override
	protected void setBaseVariables() {
		super.setBaseVariables();
		baseHealth = 40;
		baseHealthRegen = 0.02f;
		baseDamage = 0f;
		baseAttackSpeed = 1f;
		baseAccuracy = 100;
		baseAttackRange = 0;
		baseDetectionRange = 0;
		baseSpeed = 0f;
		baseLifeTime = 0;
		baseDeathTime = 0;
		baseIdleTime = 0;
	}
	
	@Override
	protected void setScalingVariables() {
		super.setScalingVariables();
		scalingHealth = 30f;
		scalingHealthRegen = 0.01f;
		scalingDamage = 0f;
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
}
