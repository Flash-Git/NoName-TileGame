package dev.flash.tilegame.entities.units.buildings;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.entities.units.creatures.Builder;
import dev.flash.tilegame.entities.units.creatures.Wizard;
import dev.flash.tilegame.gfx.Animation;
import dev.flash.tilegame.gfx.Assets;

import java.awt.*;

public class Barracks extends Building {
	
	public Barracks(Handler handler, float x, float y, int team) {
		super(handler, x, y, Building.DEFAULT_WIDTH * 2, (int) (Building.DEFAULT_HEIGHT * 1.5), team);
		
		uiCommandBox = handler.getGameUserInterface().getUiBarracksCommandBox();
	}
	
	@Override
	protected void getAssets() {
		animIdle = new Animation(1000, Assets.barracks_idle);
		animDead = new Animation(1000, Assets.tower_dead);
	}
	
	//VARIABLES
	@Override
	protected void initialiseVariables() {
		super.initialiseVariables();
		ranged = false;
		melee = false;
		cost = 250;
		type = "Barracks";
		bounds = new Rectangle(8, 12, 48, 36);
	}
	
	@Override
	protected void setBaseVariables() {
		super.setBaseVariables();
		baseHealth = 15;
		baseHealthRegen = 0.5f;
		baseDamage = 0f;
		baseAttackSpeed = 1f;
		baseAccuracy = 100;
		baseAttackRange = 0;
		baseDetectionRange = 0;
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
	
	@Override
	public void doCommandAction() {
		super.doCommandAction();
		if (commandNum == 1) {//Wizard
			spawnCreature(new Wizard(handler, getCenterX(), getCenterY() + height, team));
			objective = false;
			defcon = 5;
			return;
		}
		if (commandNum == 2) {//Builder
			spawnCreature(new Builder(handler, getCenterX(), getCenterY() + height, team));
			objective = false;
			defcon = 5;
			return;
		}
	}
	
	
}
