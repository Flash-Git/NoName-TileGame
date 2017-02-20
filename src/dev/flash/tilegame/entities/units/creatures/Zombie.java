package dev.flash.tilegame.entities.units.creatures;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.gfx.Animation;
import dev.flash.tilegame.gfx.Assets;

import java.awt.*;

public class Zombie extends Creature {
	
	public Zombie(Handler handler, float x, float y, int team) {
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
		cost = 100;
		type = "Zombie";
	}
	
	@Override
	protected void setBaseVariables() {
		super.setBaseVariables();
		baseHealth = 15;
		baseHealthRegen = 0.2f;
		baseDamage = 2f;
		baseAttackSpeed = 1f;
		baseAccuracy = 100;
		baseAttackRange = 1;
		baseDetectionRange = 8;
		baseSpeed = 0.6f;
		baseLifeTime = 0;
		baseDeathTime = 3000;
		baseIdleTime = 5000;
	}
	
	@Override
	protected void setScalingVariables() {
		super.setScalingVariables();
		scalingHealth = 3f;
		scalingHealthRegen = 0.05f;
		scalingDamage = 0.5f;
		scalingAttackSpeed = 0.2f;
		scalingAccuracy = 10;
		scalingAttackRange = 0;
		scalingDetectionRange = 0;
		scalingSpeed = 0.06f;
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
