package dev.flash.tilegame.entities.projectiles;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.entities.Entity;
import dev.flash.tilegame.entities.EntityVisitor;
import dev.flash.tilegame.entities.units.Unit;
import dev.flash.tilegame.tiles.TileChecker;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Projectile extends Entity {
	protected static final int DEFAULT_WIDTH = 16;
	protected static final int DEFAULT_HEIGHT = 16;
	
	protected static final float DEFAULT_HEALTH = 1;
	protected static final float DEFAULT_SPEED = 9f;
	protected static final float DEFAULT_DAMAGE = 1f;//should get overwritten
	protected static final int DEFAULT_TYPE = 10;
	protected static final int DEFAULT_PIERCE = 10;//applies to homing projectiles
	protected static final int DEFAULT_LIFETIME = 1000;
	
	//TARGET
	protected Unit targetUnit;
	protected boolean targetIsUnit;
	protected boolean noTarget = true;
	
	//COMBAT
	protected Unit shooter;
	protected float damage;
	protected int level;
	
	//BASE
	protected float baseLevel;
	protected float baseDamage;
	protected float baseSpeed;
	
	//SCALING
	protected int scalingLevel;
	protected float scalingHealth;
	protected float scalingHealthRegen;
	protected float scalingDamage;
	protected float scalingSpeed;
	protected int scalingDeathTime;
	
	protected int tX;
	protected int tY;
	
	public Projectile(Handler handler, float x, float y, int width, int height, float damage, Unit shooter, int tX, int tY) {
		super(handler, x, y, width, height, shooter.getTeam());//overwrite entity's declarator
		this.x = x - width / 2;
		this.y = y - height / 2;
		
		this.damage = damage;
		this.shooter = shooter;
		this.tX = tX;
		this.tY = tY;
		bounds.setBounds(width / 2 - 2, height / 2 - 2, 4, 4);
	}
	
	//VARIABLES
	@Override
	protected void initialise() {
		super.initialise();
		
		setLevel(1);
		
		float distance = (float) Math.sqrt(Math.pow((tX - x - bounds.x + bounds.width / 2), 2) + Math.pow((tY - y - bounds.y + bounds.height / 2), 2));
		//normal on x and y
		float nX = (tX - x - bounds.x + bounds.width / 2) / distance;
		float nY = (tY - y - bounds.y + bounds.height / 2) / distance;
		
		//factoring in the entity's speed
		vX = nX * speed;//this.speed;
		vY = nY * speed;//this.speed;
	}
	
	@Override
	protected void initialiseVariables() {
		super.initialiseVariables();
		setScalingVariables();
		
		type = "Projectile";
		persistent = false;
		selected = false;
	}
	
	@Override
	protected void setBaseVariables() {
		super.setBaseVariables();
		baseLevel = 1;
		baseHealth = 10;
		baseHealthRegen = 0.3f;
		baseDamage = damage;
		baseSpeed = 9f;
		baseLifeTime = (int) (shooter.getAttackRange() * 1000 / baseSpeed / 2);
		baseDeathTime = 500;
	}
	
	protected void setScalingVariables() {
		scalingLevel = 1;
		scalingHealth = 1f;
		scalingHealthRegen = 0.005f;
		scalingDamage = baseDamage / 2;
		scalingSpeed = 0.2f;
	}
	
	@Override
	protected void updateVariables() {
		setHealth(scalingHealth * (level - 1) + baseHealth);
		healthRegen = scalingHealthRegen * (level - 1) + baseHealthRegen;
		damage = scalingDamage * (level - 1) + baseDamage;
		speed = scalingSpeed * (level - 1) + baseSpeed;
		setLifeTime((int) (shooter.getAttackRange() * 1000 / speed / 2));
		setDeathTime(scalingDeathTime * (level - 1) + baseDeathTime);
	}
	
	public void setLevel(int level) {
		this.level = level;
		updateVariables();
	}
	
	//TICK RENDER ACCEPT
	
	@Override
	public void tick(double delta) {
		super.tick(delta);
		if (!alive) {
			return;
		}
		moveTowards(delta);
		
		animate();
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}
	
	
	@Override
	public void accept(EntityVisitor visitor) {
		visitor.visit(this);
	}
	
	//ANIMATION
	protected abstract void animate();
	
	//MOVEMENT
	//COMBAT
	
	private void attack(Unit t) {
		if (alive && t.isAlive()) {
			t.modHealth(t.getHealth() - this.damage);
			if (!t.isAlive()) {
				shooter.onKill(t.getExpReward());
			}
		}
	}
	
	private void moveTowards(double delta) {
		float tempX = (float) (x + vX * delta / 1000 * 60);
		float tempY = (float) (y + vY * delta / 1000 * 60);
		//averages seem to be about 3pixels, which is fine for all current collisions,
		//if it gets bad, either switch to a vector system or lengthen the bounds
		if (TileChecker.isSolid((int) tempX, (int) tempY)) {
			die();
			return;
		}
		boolean collided = checkCollision();
		x = tempX;
		y = tempY;
		if (collided == false) {
			checkCollision();
		}
	}
	
	protected boolean checkCollision() {//still not ideal for high speeds or fps drops
		Unit collider = handler.getEntityManager().getUnitCollision(this);
		if (collider != null) {
			if (collider.isAlive() && team != collider.getTeam()) {
				attack(collider);
				die();
				return true;
			}
		}
		return false;
	}
	
	protected abstract BufferedImage getCurrentAnimationFrame();
	
	//Getting and Setters
	
	public float getDamage() {
		return damage;
	}
	
	public void setDamage(float damage) {
		this.damage = damage;
	}
	
	
}
