package dev.flash.tilegame.entities.units.creatures;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.entities.projectiles.Fireball;
import dev.flash.tilegame.gfx.Animation;
import dev.flash.tilegame.gfx.Assets;

import java.awt.*;

public class Wizard extends Creature {
	
	public Wizard(Handler handler, float x, float y, int team) {
		super(handler, x, y, Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT, team);
		
		uiCommandBox = handler.getGameUserInterface().getUiWizardCommandBox();
	}
	
	//INITIALISATION
	
	@Override
	protected void getAssets() {
		animUp = new Animation(220, Assets.wizard_up);
		animDown = new Animation(220, Assets.wizard_down);
		animLeft = new Animation(220, Assets.wizard_left);
		animRight = new Animation(220, Assets.wizard_right);
		animDead = new Animation(2200, Assets.wizard_right);
	}
	
	//VARIABLES
	@Override
	protected void initialiseVariables() {
		super.initialiseVariables();
		ranged = true;
		melee = false;
		cost = 100;
		type = "Wizard";
	}
	
	@Override
	protected void setBaseVariables() {
		super.setBaseVariables();
		baseHealth = 10;
		baseHealthRegen = 0.15f;
		baseDamage = 5f;
		baseAttackSpeed = 1f;
		baseAccuracy = 200;
		baseAttackRange = 8;
		baseDetectionRange = 8;
		baseSpeed = 0.8f;
		baseLifeTime = 0;
		baseDeathTime = 10000;
		baseIdleTime = 2000;
	}
	
	@Override
	protected void setScalingVariables() {
		super.setScalingVariables();
		scalingHealth = 2f;
		scalingHealthRegen = 0.05f;
		scalingDamage = 1f;
		scalingAttackSpeed = 0.1f;
		scalingAccuracy = 10;
		scalingAttackRange = 0;
		scalingDetectionRange = 0;
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
	
	@Override
	protected void fireProjectile(int tX, int tY) {
		handler.getEntityManager().addToAddList(
				new Fireball(handler, getCenterX(), getCenterY(), damage, this, tX, tY));
	}
}
