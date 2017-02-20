package dev.flash.tilegame.entities.units.creatures;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.entities.EntityVisitor;
import dev.flash.tilegame.entities.units.Unit;
import dev.flash.tilegame.pathfinding.Node;
import dev.flash.tilegame.tiles.TileChecker;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Creature extends Unit {
	
	protected static final int WORLD3_GOALX = 25 * 32;//TODO temp, put it in world3.txt
	protected static final int WORLD3_GOALY = 5 * 32;
	
	//SCALING
	protected int scalingPushWeight;
	protected int scalingPathPatience;
	
	//BASE
	protected int basePushWeight;
	protected int basePathPatience;
	
	//MOVEMENT
	protected int pushWeight;
	
	//EXPERIENCE
	protected int exp, expToLvl;
	
	//Constructor
	public Creature(Handler handler, float x, float y, int width, int height, int team) {
		super(handler, x, y, width, height, team);//overwrite unit's declarator
	}
	
	
	@Override
	protected void initialiseVariables() {
		super.initialiseVariables();
		exp = 0;
		type = "Creature";
		persistent = false;
		name = "";
		
		bounds.setBounds(8, 16, 16, 16);
		
		if (team == 2) {
			objective = true;
			objectiveX = WORLD3_GOALX;
			objectiveY = WORLD3_GOALY;
		} else {
			objective = false;
		}
		
	}
	
	@Override
	protected void setBaseVariables() {
		super.setBaseVariables();
		baseIdleTime = 4000;
		basePathPatience = 1000;
		basePushWeight = 1;
	}
	
	@Override
	protected void setScalingVariables() {
		super.setScalingVariables();
		scalingPathPatience = 0;
		scalingPushWeight = 0;
	}
	
	@Override
	protected void updateVariables() {
		super.updateVariables();
		pathPatience = scalingPathPatience * level + basePathPatience;
		pathPatience = scalingPushWeight * level + basePushWeight;
	}
	
	@Override
	public void setLevel(int level) {
		super.setLevel(level);
		expToLvl = (int) (Math.pow(level, 1.3)) * 100;
		exp = 0;
	}
	
	//TICK RENDER ACCEPT
	@Override
	public void tick(double delta) {
		super.tick(delta);
	}
	
	@Override
	public void render(Graphics g) {
		if (alive) {
			//health bar
			g.setColor(Color.RED);
			g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - 5 - handler.getGameCamera().getyOffset()), (int) ((this.health * this.width) / this.maxHealth), 3);

//			attack rect
//			g.fillRect((int) (attackRectangle.x - handler.getGameCamera().getxOffset()), (int) (attackRectangle.y - handler.getGameCamera().getyOffset()), attackRectangle.width, attackRectangle.height);
			
			//Lvl
			g.setColor(Color.BLACK);
			g.drawString("Lvl " + Integer.toString(level), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - 7 - handler.getGameCamera().getyOffset()));
		}
		
		g.setColor(Color.WHITE);
		for (Node n : path) {
			g.drawString(Integer.toString((int) n.getWeight()), n.getX() * 32 + 12 - (int) handler.getGameCamera().getxOffset(), n.getY() * 32 + 12 - (int) handler.getGameCamera().getyOffset());
		}
	}
	
	public void accept(EntityVisitor visitor) {
		visitor.visit(this);
	}
	
	//ANIMATION
	@Override
	protected void animate() {
		if (alive) {
			//animIdle.tick();
			animUp.tick();
			animDown.tick();
			animLeft.tick();
			animRight.tick();
		} else {
			animDead.tick();
		}
	}
	
	protected BufferedImage getCurrentAnimationFrame() {
		if (vX < -0.5 * speed) {
			return animLeft.getCurrentFrame();
		} else if (vX > 0.5 * speed) {
			return animRight.getCurrentFrame();
		} else if (vY < -0.5 * speed) {
			return animUp.getCurrentFrame();
		} else if (vY > 0.5 * speed) {
			return animDown.getCurrentFrame();
		} else {
			return animDown.getCurrentFrame();
			//	return animIdle.getCurrentFrame();
		}
	}
	
	//MOVEMENT
	
	@Override
	protected void idle(double delta) {
		Random rand = new Random();
		int tX = (rand.nextInt(3) - 1);
		int tY = (rand.nextInt(3) - 1);
		
		int x = getCenterX() + tX * 32;
		int y = getCenterY() + tY * 32;
		
		if (TileChecker.outOfMap(x, y)) {
			return;
		}
		
		if (TileChecker.isSolid(x, y)) {
			return;
		}
		
		if (TileChecker.unitsOnTile(x, y, this)) {
			return;
		}
//		setObjectiveX(x);
//		setObjectiveY(y);
//		setObjective(true);
//		commandNum=0;
		pathTo(delta, x, y, 4);
	}
	
	//COMMAND
	
	@Override
	public void command(double delta) {//TODO
		pathTo(delta, getObjectiveX(), getObjectiveY(), defcon);
		if (defcon > 4) {
			if (getDistanceToGoal() < 0.2) {
				doCommandAction();
				return;
			}
		}
		if (getDistanceToGoal() < 1) {
			doCommandAction();
			
		}
	}
	
	//CLASS METHODS
	
	public void gainExp(int exp) {
		if (expToLvl - (exp + this.exp) > 0) {
			this.exp += exp;
			return;
		} else {
			int overflowExp = exp + this.exp - expToLvl;
			setLevel(level + 1);
			gainExp(overflowExp);
		}
	}
	
	public int getExpReward() {
		return (int) (Math.pow(level, 1.1)) * 10 + 5;
	}
	
	@Override
	public void die() {
		super.die();
		pushWeight = 0;
		path.clear();
		if (team == 1) {
			return;
		} else {
			handler.getWorld().setGold(handler.getWorld().getGold() + 4 + level);
		}
	}
	
	@Override
	public void onKill(int expReward) {
		gainExp(expReward);
	}
	
	
	//getters and setters
	@Override
	public boolean hasExp() {
		return true;
	}
	
	@Override
	public int getExp() {
		return exp;
	}
	
	public void setExp(int exp) {
		this.exp = exp;
	}
	
	@Override
	public int getExpToLvl() {
		return expToLvl;
	}
	
	public void setExpToLvl(int expToLvl) {
		this.expToLvl = expToLvl;
	}
	
	public int getPushWeight() {
		return pushWeight;
	}
	
	public void setPushWeight(int pushWeight) {
		this.pushWeight = pushWeight;
	}
}
