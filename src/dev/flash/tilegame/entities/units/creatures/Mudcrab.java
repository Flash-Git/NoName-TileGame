package dev.flash.tilegame.entities.units.creatures;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.gfx.Animation;
import dev.flash.tilegame.gfx.Assets;

import java.awt.*;

public class Mudcrab extends Creature {
	
	public Mudcrab(Handler handler, float x, float y, int team) {
		super(handler, x, y, Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT, team);
		
		uiCommandBox = handler.getGameUserInterface().getUiMudcrabCommandBox();
	}
	
	//INITIALISATION
	
	@Override
	protected void getAssets() {
		//idle = new Animation(150, Assets.mudcrab_down);
		
		animUp = new Animation(150, Assets.mudcrab_up);
		animDown = new Animation(150, Assets.mudcrab_down);
		animLeft = new Animation(150, Assets.mudcrab_left);
		animRight = new Animation(150, Assets.mudcrab_right);
		animDead = new Animation(1000, Assets.mudcrab_down);
		
	}
	
	//VARIABLES
	@Override
	protected void initialiseVariables() {
		super.initialiseVariables();
		ranged = false;
		melee = true;
		cost = 100;
		type = "Mudcrab";
	}
	
	@Override
	protected void setBaseVariables() {
		super.setBaseVariables();
		baseHealth = 15;
		baseHealthRegen = 0.5f;
		baseDamage = 2f;
		baseAttackSpeed = 1f;
		baseAccuracy = 100;
		baseAttackRange = 1;
		baseDetectionRange = 7;
		baseSpeed = 1f;
		baseLifeTime = 0;
		baseDeathTime = 10000;
		baseIdleTime = 2000;
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
		scalingSpeed = 0.02f;
		scalingLifeTime = 0;
		scalingDeathTime = 0;
		scalingIdleTime = 0;
	}
	
	//TICK RENDER ACCEPT
	
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
//		if(path.size()>0){//this shows real time pathing
//			Node n = path.get(0);
//			g.setColor(Color.red);
//			g.drawRect((int) (n.getX()*Tile.TILEWIDTH- handler.getGameCamera().getxOffset()), (int) (n.getY()*Tile.TILEHEIGHT- handler.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT);
//		}
	}
	
}
