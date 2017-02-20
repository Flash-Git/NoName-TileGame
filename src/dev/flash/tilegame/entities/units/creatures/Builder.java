package dev.flash.tilegame.entities.units.creatures;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.entities.units.buildings.Barracks;
import dev.flash.tilegame.entities.units.buildings.Building;
import dev.flash.tilegame.entities.units.buildings.MageTower;
import dev.flash.tilegame.entities.units.buildings.Tower;
import dev.flash.tilegame.gfx.Animation;
import dev.flash.tilegame.gfx.Assets;

import java.awt.*;

public class Builder extends Creature {
	
	Building building;
	
	public Builder(Handler handler, float x, float y, int team) {
		super(handler, x, y, Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT, team);
		
		uiCommandBox = handler.getGameUserInterface().getUiBuilderCommandBox();
	}
	
	@Override
	protected void getAssets() {
		animUp = new Animation(150, Assets.builder_up);
		animDown = new Animation(150, Assets.builder_down);
		animLeft = new Animation(150, Assets.builder_left);
		animRight = new Animation(150, Assets.builder_right);
		animDead = new Animation(1500, Assets.builder_right);
	}
	
	//VARIABLES
	@Override
	protected void initialiseVariables() {
		super.initialiseVariables();
		ranged = false;
		melee = true;
		cost = 100;
		type = "Builder";
	}
	
	@Override
	protected void setBaseVariables() {
		super.setBaseVariables();
		baseHealth = 10;
		baseHealthRegen = 0.2f;
		baseDamage = 1f;
		baseAttackSpeed = 1f;
		baseAccuracy = 50;
		baseAttackRange = 1;
		baseDetectionRange = 3;
		baseSpeed = 1f;
		baseLifeTime = 0;
		baseDeathTime = 10000;
		baseIdleTime = 2000;
	}
	
	@Override
	protected void setScalingVariables() {
		super.setScalingVariables();
		scalingHealth = 2f;
		scalingHealthRegen = 0.05f;
		scalingDamage = 0.5f;
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
	public void doCommandAction() {
		super.doCommandAction();
		if (commandNum == 1) {//tower
			build(new Tower(handler, objectiveX / 32 * 32, objectiveY / 32 * 32, team));
		}
		if (commandNum == 2) {//barracks
			build(new Barracks(handler, objectiveX / 32 * 32, objectiveY / 32 * 32, team));
		}
		if (commandNum == 3) {//mage tower
			build(new MageTower(handler, objectiveX / 32 * 32, objectiveY / 32 * 32, team));
		}
	}
	
	public void build(Building building) {
		if (handler.getEntityManager().getUnitCollision(building, this) != null) {//second parameter allows builder to build on himself
			
			return;
		}
		
		if (handler.getWorld().getTile((int) (building.getBounds().getX() + building.getX()) / 32, (int) (building.getBounds().getY() + building.getY()) / 32).isSolid()) {
			System.out.println("Trying to build " + building.getType() + " on solid tiles.");
			objective = false;
			defcon = 5;
			return;
		}
		
		if (handler.getWorld().getTile((int) (building.getBounds().getX() + building.getBounds().getWidth() + building.getX()) / 32, (int) (building.getBounds().getY() + building.getBounds().getHeight() + building.getY()) / 32).isSolid()) {
			System.out.println("Trying to build " + building.getType() + " on solid tiles.");
			objective = false;
			defcon = 5;
			return;
		}
		
		int gold = handler.getWorld().getGold();
		
		if (gold > building.getCost()) {
			handler.getEntityManager().addToAddList(building);
			handler.getWorld().setGold(handler.getWorld().getGold() - building.getCost());
		} else {
			System.out.println("Not enough gold for " + building.getType() + ".");
		}
		objective = false;
		defcon = 5;
		path.clear();
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
