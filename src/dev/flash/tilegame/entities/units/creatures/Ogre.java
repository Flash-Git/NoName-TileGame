package dev.flash.tilegame.entities.units.creatures;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.gfx.Animation;
import dev.flash.tilegame.gfx.Assets;

import java.awt.*;

public class Ogre extends Creature {
	
	
	public Ogre(Handler handler, float x, float y, int team) {
		super(handler, x, y, Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT, team);
		
		uiCommandBox = handler.getGameUserInterface().getUiZombieCommandBox();
	}
	
	@Override
	protected void getAssets() {
		animUp = new Animation(150, Assets.sicky_up);
		animDown = new Animation(150, Assets.sicky_down);
		animLeft = new Animation(150, Assets.sicky_left);
		animRight = new Animation(150, Assets.sicky_right);
		animDead = new Animation(2000, Assets.sicky_right);
	}
	
	//VARIABLES
	@Override
	protected void initialiseVariables() {
		super.initialiseVariables();
		ranged = false;
		melee = true;
		cost = 200;
		type = "Ogre";
	}
	
	@Override
	protected void setBaseVariables() {
		super.setBaseVariables();
		baseHealth = 30;
		baseHealthRegen = 0.5f;
		baseDamage = 5f;
		baseAttackSpeed = 0.4f;
		baseAccuracy = 50;
		baseAttackRange = 1.5f;
		baseDetectionRange = 10;
		baseSpeed = 1f;
		baseLifeTime = 0;
		baseDeathTime = 10000;
		baseIdleTime = 5000;
	}
	
	@Override
	protected void setScalingVariables() {
		super.setScalingVariables();
		scalingHealth = 5f;
		scalingHealthRegen = 0.25f;
		scalingDamage = 1f;
		scalingAttackSpeed = 0.04f;
		scalingAccuracy = 10;
		scalingAttackRange = 0f;
		scalingDetectionRange = 0f;
		scalingSpeed = 0.02f;
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
		if (alive) {
			g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		} else {
			g.drawImage(animDead.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		}
	}
	
}
