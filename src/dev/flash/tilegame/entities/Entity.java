package dev.flash.tilegame.entities;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.tiles.Chunk;
import dev.flash.tilegame.timers.Timer;

import java.awt.*;
import java.util.ArrayList;

public abstract class Entity {
	
	//DEFAULT ENTITY VARIABLES
	protected static final int DEFAULT_WIDTH = 32;
	protected static final int DEFAULT_HEIGHT = 32;
	
	protected static final float DEFAULT_HEALTH = 10;
	protected static final float DEFAULT_HEALTH_REGEN = 0.5f;
	protected static final float DEFAULT_SPEED = 0f;
	
	//GLOBAL ENTITY VARIABLES
	protected Handler handler;
	protected int width, height;
	protected Chunk chunk;
	
	//MOVEMENT
	protected float x, y;
	protected float relativeX, relativeY;//x and y only change at end of tick
	protected double vX, vY;
	protected Rectangle bounds;
	
	//COMBAT
	protected float health;
	protected float maxHealth;
	protected float healthRegen;//health gained every second
	protected Timer healthRegenTimer;
	protected float speed;
	protected boolean alive;
	protected boolean persistent;
	protected Timer deathTimer;//Time after Death before Removal
	protected Timer lifeTimer;//Time before death
	protected int team;
	
	//BASE
	protected float baseHealth;
	protected float baseHealthRegen;
	protected int baseLifeTime;
	protected int baseDeathTime;
	
	//PLAYER INTERACTION
	protected boolean selected;
	protected String type;
	
	//ArrayList of this Entity's timers
	protected ArrayList<Timer> timers = new ArrayList<Timer>();
	
	public Entity(Handler handler, float x, float y, int width, int height, int team) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.team = team;
		bounds = new Rectangle(0, 0, width, height);
		//initialise();//TODO check if this initialises all variables properly
	}
	
	//INITIALISATION
	
	protected void initialise() {
		initialiseVariables();
		addTimers();
		
		updateVariables();
		handler.getTimerManager().addToAddList(timers);
	}
	
	protected void initialiseVariables() {
		setBaseVariables();
		
		type = "Entity";
		persistent = false;
		selected = false;
		alive = true;
		
		relativeX = 0;
		relativeY = 0;
	}
	
	protected void addTimers() {
		timers.add(healthRegenTimer = new Timer(200));
		timers.add(lifeTimer = new Timer(false));
		timers.add(deathTimer = new Timer(false));
	}
	
	protected void setBaseVariables() {
		baseHealth = 10;
		baseHealthRegen = 0.3f;
		baseLifeTime = 0;
		baseDeathTime = 10000;
	}
	
	protected void updateVariables() {
		health = baseHealth;
		healthRegen = baseHealthRegen;
		lifeTimer.setDelay(baseLifeTime);
		deathTimer.setDelay(baseDeathTime);
	}
	
	//TICK RENDER ACCEPT
	
	public void tick(double delta) {
		//If entity has been alive longer than lifetime then kill, else, regen
		if (alive) {
			if (lifeTimer.isDone()) {
				die();
			} else {
				regenHealth();
			}
		} else if (deathTimer.getDelay() == 0 || deathTimer.isDone()) {
			//TODO it appears to keep ticking dead things, at least the timers
			handler.getEntityManager().addToRemoveList(this);
		}
		
		chunkHandling();
		
		x += relativeX;
		y += relativeY;
	}
	
	public abstract void render(Graphics g);
	
	public abstract void accept(EntityVisitor visitor);
	
	//CLASS METHODS
	
	//HEALTH REGEN
	protected void regenHealth() {
		if (health < maxHealth) {
			if ((int) health > 0) {//TODO
				if (healthRegenTimer.isDone()) {
					modHealth(health + healthRegen / 5);//DIVIDED BY 5 BECAUSE IT IS APPLIED 5TIMES A SECOND
				}
			}
		}
	}
	
	//CHUNK HANDLING
	protected void chunkHandling() {
		if (chunk == null) {
			chunk = handler.getChunkManager().getChunk(getCenterX(), getCenterY());
			if (chunk != null) {
				chunk.addEntity(this);
			} else {
				System.err.println("nullchunk after get method " + getCenterX() + " " + getCenterY());
			}
		}
		if (!chunk.equals(handler.getChunkManager().getChunk(getCenterX(), getCenterY()))) {
			chunk.removeEntity(this);
			chunk = handler.getChunkManager().getChunk(getCenterX(), getCenterY());
			if (chunk != null) {
				chunk.addEntity(this);
			} else {
				System.err.println("nullchunk after get method " + getCenterX() + " " + getCenterY());
			}
		}
	}
	
	
	public void die() {
		alive = false;
		deathTimer.activate();//time until their corpse is removed from the game
	}
	
	public void modHealth(float health) {
		if ((int) health <= 0) {
			die();
			this.health = 0;
		} else {
			if (health > maxHealth) {
				this.health = maxHealth;
			} else {
				this.health = health;
			}
		}
	}
	
	public void select() {
		if (!selected) {
			selected = true;
			handler.getEntityManager().setSelected(this);
		}
	}
	
	//Getters and Setters
	
	public int getLifeTime() {
		return (int) lifeTimer.getDelay();
	}
	
	public void setLifeTime(int delay) {
		lifeTimer.setDelay(delay);
	}
	
	public int getDeathTime() {
		return (int) deathTimer.getDelay();
	}
	
	public void setDeathTime(int delay) {
		deathTimer.setDelay(delay);
	}
	
	public Rectangle getCollisionBounds(float xOffset, float yOffset) {
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public int getCenterX() {
		return (int) (bounds.getCenterX() + x);
	}
	
	public int getCenterY() {
		return (int) (bounds.getCenterY() + y);
	}
	
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public float getHealth() {
		return health;
	}
	
	public void setHealth(float health) {
		this.maxHealth = health;
		this.health = health;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isPersistent() {
		return persistent;
	}
	
	public void setPersistent(boolean persistent) {
		this.persistent = persistent;
	}
	
	public float getHealthRegen() {
		return healthRegen;
	}
	
	public void setHealthRegen(float healthRegen) {
		this.healthRegen = healthRegen;
	}
	
	public float getMaxHealth() {
		return maxHealth;
	}
	
	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public Chunk getChunk() {
		return chunk;
	}
	
	public void setChunk(Chunk chunk) {
		this.chunk = chunk;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public double getvX() {
		return vX;
	}
	
	public void setvX(double d) {
		this.vX = d;
	}
	
	public double getvY() {
		return vY;
	}
	
	public void setvY(double vY) {
		this.vY = vY;
	}
	
	public float getRelativeX() {
		return relativeX;
	}
	
	public void setRelativeX(float relativeX) {
		this.relativeX = relativeX;
	}
	
	public float getRelativeY() {
		return relativeY;
	}
	
	public void setRelativeY(float relativeY) {
		this.relativeY = relativeY;
	}
	
	public int getTeam() {
		return team;
	}
	
	public void setTeam(int team) {
		this.team = team;
	}
	
	public Handler getHandler() {
		return handler;
	}
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public Timer getHealthRegenTimer() {
		return healthRegenTimer;
	}
	
	public void setHealthRegenTimer(Timer healthRegenTimer) {
		this.healthRegenTimer = healthRegenTimer;
	}
	
	public Timer getDeathTimer() {
		return deathTimer;
	}
	
	public void setDeathTimer(Timer deathTimer) {
		this.deathTimer = deathTimer;
	}
	
	public Timer getLifeTimer() {
		return lifeTimer;
	}
	
	public void setLifeTimer(Timer lifeTimer) {
		this.lifeTimer = lifeTimer;
	}
	
	public ArrayList<Timer> getTimers() {
		return timers;
	}
	
	public void setTimers(ArrayList<Timer> timers) {
		this.timers = timers;
	}
}
