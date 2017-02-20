package dev.flash.tilegame.entities.units;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.entities.Entity;
import dev.flash.tilegame.entities.projectiles.Linear;
import dev.flash.tilegame.entities.units.creatures.Creature;
import dev.flash.tilegame.gfx.Animation;
import dev.flash.tilegame.pathfinding.ModifiedBresenham;
import dev.flash.tilegame.pathfinding.NewAStar;
import dev.flash.tilegame.pathfinding.Node;
import dev.flash.tilegame.tiles.Tile;
import dev.flash.tilegame.tiles.TileChecker;
import dev.flash.tilegame.timers.Timer;
import dev.flash.tilegame.ui.objects.UIRectangleContainer;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

public abstract class Unit extends Entity {
	
	//UI
	protected UIRectangleContainer uiCommandBox;
	
	//ANIMATIONS
	protected Animation animUp;
	protected Animation animDown;
	protected Animation animLeft;
	protected Animation animRight;
	protected Animation animIdle;
	protected Animation animDead;
	
	//UNIT STATES
	protected UnitState state;
	protected UnitCombatState combatState;
	protected UnitIdleState idleState;
	protected UnitControlledState controlledState;
	protected UnitObjectiveState objectiveState;
	
	//OBJECTIVE
	protected boolean objective;
	protected int commandNum;
	protected int objectiveX, objectiveY;
	
	//TARGET
	protected Unit targetUnit = null;
	protected boolean targetIsUnit = false;
	protected boolean noTarget = true;
	protected int targetX;
	protected int targetY;
	
	//ALARM STATE
	protected int defcon;
	
	//COMBAT
	protected float damage;
	protected Timer attackTimer;
	protected int accuracy;
	protected float attackRange;
	protected float detectionRange;
	protected int level;
	protected boolean ranged, melee;
	
	//BASE
	protected float baseLevel;
	protected float baseDamage;
	protected float baseAttackSpeed;
	protected int baseAccuracy;
	protected float baseAttackRange;
	protected float baseDetectionRange;
	protected float baseSpeed;
	protected int baseIdleTime;
	
	//SCALING
	protected int scalingLevel;
	protected float scalingHealth;
	protected float scalingHealthRegen;
	protected float scalingDamage;
	protected float scalingAttackSpeed;
	protected int scalingAccuracy;
	protected float scalingAttackRange;
	protected float scalingDetectionRange;
	protected float scalingSpeed;
	protected int scalingLifeTime;
	protected int scalingDeathTime;
	protected int scalingIdleTime;
	
	//Minimum time between Idle Movements
	protected Timer idleTimer;
	
	//Current Stored Path
	protected ArrayList<Node> path = new ArrayList<Node>();
	
	protected int pathPatience;
	protected Timer patienceTimer;
	
	//PLAYER INTERACTION
	protected boolean controlled;
	protected boolean commanded;
	protected String name;
	protected int cost;
	
	
	//Constructor
	public Unit(Handler handler, float x, float y, int width, int height, int team) {
		super(handler, x, y, width, height, team);//overwrite entity's declarator
		
		getAssets();
		
		combatState = new UnitCombatState(this);
		idleState = new UnitIdleState(this);
		controlledState = new UnitControlledState(this);
		objectiveState = new UnitObjectiveState(this);
		
		//handler.getTimerManager().addToAddList(patienceTimer = new Timer(pathPatience));
		//System.out.println(pathPatience);//TODO need to sort this out with an init(); method so timers don't have garbage in them
	}
	
	protected abstract void getAssets();
	
	//VARIABLES
	@Override
	protected void initialise() {
		super.initialise();
		setLevel(1);
	}
	
	@Override
	protected void initialiseVariables() {
		super.initialiseVariables();
		setScalingVariables();
		
		defcon = 5;
		ranged = true;
		controlled = false;
		commanded = false;
		selected = false;
		objective = false;
		
		//updateVariables();
	}
	
	@Override
	protected void addTimers() {
		super.addTimers();
		timers.add(idleTimer = new Timer(false));
		timers.add(attackTimer = new Timer(false));
		timers.add(patienceTimer = new Timer(false));
	}
	
	@Override
	protected void setBaseVariables() {
		super.setBaseVariables();
		baseLevel = 1;
		baseHealth = 10;
		baseHealthRegen = 0.3f;
		baseDamage = 3f;
		baseAttackSpeed = 1.5f;
		baseAccuracy = 50;
		baseAttackRange = 5;
		baseDetectionRange = 9;
		baseSpeed = 1f;
		baseLifeTime = 0;
		baseDeathTime = 10000;
		baseIdleTime = 2000;
	}
	
	protected void setScalingVariables() {
		scalingLevel = 1;
		scalingHealth = 1f;
		scalingHealthRegen = 0.005f;
		scalingDamage = 0.1f;
		scalingAttackSpeed = 0.1f;
		scalingAccuracy = 1;
		scalingAttackRange = 0;
		scalingDetectionRange = 0;
		scalingSpeed = 0f;
		scalingLifeTime = 0;
		scalingDeathTime = 0;
		scalingIdleTime = 0;
	}
	
	@Override
	protected void updateVariables() {
		setHealth(scalingHealth * (level - 1) + baseHealth);
		healthRegen = scalingHealthRegen * (level - 1) + baseHealthRegen;
		damage = scalingDamage * (level - 1) + baseDamage;
		setAttackSpeed(scalingAttackSpeed * (level - 1) + baseAttackSpeed);
		accuracy = scalingAccuracy * (level - 1) + baseAccuracy;
		attackRange = scalingAttackRange * (level - 1) + baseAttackRange;
		detectionRange = scalingDetectionRange * (level - 1) + baseDetectionRange;
		speed = scalingSpeed * (level - 1) + baseSpeed;
		setLifeTime(scalingLifeTime * (level - 1) + baseLifeTime);
		setDeathTime(scalingDeathTime * (level - 1) + baseDeathTime);
		setIdleTime(scalingIdleTime * (level - 1) + baseIdleTime);
	}
	
	public void setLevel(int level) {
		this.level = level;
		updateVariables();
	}
	
	//TICK RENDER ACCEPT
	
	@Override
	public void tick(double delta) {
		super.tick(delta);
		if (alive) {
			updateState();
			state.tick(delta);
		}
		animate();
	}
	
	@Override
	public abstract void render(Graphics g);
	
	//CLASS METHODS
	
	//ANIMATION
	protected abstract void animate();
	
	private void updateState() {
		if (isControlled()) {
			if (objective) {
				state = objectiveState;
				return;
			}
			state = controlledState;
			return;
		}
		if (!noTarget) {
			state = combatState;
			return;
		} else if (objective) {
			state = objectiveState;
			return;
		} else {
			state = idleState;
		}
	}
	
	//MOVEMENT
	
	//PATHING
	
	public void genPath(int tX, int tY) {
		//Check Line of sight
		if (!intersectsWithSolids(getlOS(tX, tY))) {
			ModifiedBresenham.generatePath(handler, path, getCenterX() / 32, getCenterY() / 32, tX / 32, tY / 32);
			//System.out.println("Bres Pathfinding");
		} else {
			NewAStar.generatePath(handler, path, getCenterX() / 32, getCenterY() / 32, tX / 32, tY / 32, this);
			//System.out.println("AStar Pathfinding");
		}
	}
	
	public void pathTo(double delta, int x, int y, int defcon) {
		if (!isMobile()) {
			System.err.println("immobile unit trying to move");
			return;
		}
		
		if (this.defcon > defcon) {
			//if bigger threat, override previous actions
			this.defcon = defcon;
			genPath(x, y);
			return;
		}
		
		if (path.isEmpty()) {
			this.defcon = defcon;
			genPath(x, y);
		} else {
			followPath(delta);
		}
	}
	
	protected void followPath(double delta) {
		if (path.isEmpty()) {
			return;
		}
		
		Node n = path.get(0);//n is next Node in path
		
		//translate Node x, y to pixel x, y
		int tX = n.getX() * 32 + 16;
		int tY = n.getY() * 32 + 16;
		
		float x = getCenterX();
		float y = getCenterY();
		
		//distance from entity to Node
		float distance = (float) Math.sqrt(Math.pow((tX - x), 2) + Math.pow((tY - y), 2));
		
		//normal on x and y
		double nX = (tX - x) / distance;
		double nY = (tY - y) / distance;
		//factoring in the entity's speed
		vX = (float) (nX * speed);//this.speed;
		vY = (float) (nY * speed);//this.speed;

//		if(handler.getEntityManager().checkEntityCollisionWithTypes(this, vX, vY, "Creature Projectile Building") == false){
		move(delta, this.x + vX * delta / 1000 * 60, this.y + vY * delta / 1000 * 60);

//		if(patienceTimer.isDone()){
//			path.clear();
//			return;
//		}
		if (distance < 6) {//precision in pixels 12
			path.remove(0);
			patienceTimer.reset();
		}
	}
	
	public void move(double delta, double mX, double mY) {
		if (!TileChecker.isSolid((int) mX + bounds.x, (int) y + bounds.y) && !TileChecker.isSolid((int) mX + bounds.x + bounds.width, (int) y + bounds.y)) {
			x = (float) mX;
		}
		
		if (!TileChecker.isSolid((int) x + bounds.x, (int) mY + bounds.y) && !TileChecker.isSolid((int) x + bounds.x, (int) mY + bounds.height + bounds.y)) {
			y = (float) mY;
		}
	}
	
	protected abstract void idle(double delta);
	
	//COMMAND
	
	
	//TODO
	public void doCommand(int commandNum) {
		this.commandNum = commandNum;
		objective = true;
		objectiveX = (int) ((handler.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset()) / 32) * 32 + 16;
		objectiveY = (int) ((handler.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset()) / 32) * 32 + 16;
	}
	
	public void doCommandAction() {
		if (commandNum == 0) {
			objective = false;
			defcon = 5;
			path.clear();
		}
	}
	
	public void spawnCreature(Creature creature) {
		creature.setX(creature.getX() - creature.getWidth() / 2);
		creature.setY(creature.getY() - creature.getHeight() / 2);
		
		int gold = handler.getWorld().getGold();
		
		if (gold >= creature.getCost()) {//TODO remove this
			handler.getEntityManager().addToAddList(creature);
			handler.getWorld().setGold(gold - creature.getCost());
		} else {
			System.out.println("Not enough gold for " + creature.getType() + ".");
		}
		objective = false;
		defcon = 5;
	}
	
	@Override
	public void select() {
		if (!selected) {
			selected = true;
			handler.getEntityManager().setSelected(this);
		} else {
			if (team == 1) {
				commanded = true;
				handler.getEntityManager().setCommanded(this);
				handler.getGameUserInterface().getHudUIManager().setUICommandBox(uiCommandBox);//TODO no instanceof in gameuserinterface but left with this..
			}
		}
	}
	
	//COMBAT
	protected void attack(double delta) {
		if (!attackTimer.isDone()) {
			idle(delta);
			return;
		}
		if (melee) {
			meleeAttack();
			return;
		}
		if (ranged) {
			attackTarget();
			return;
		}
	}
	
	public abstract void onKill(int expReward);
	
	public void meleeAttack() {
		Rectangle attackRectangle = new Rectangle((int) (x + bounds.getX()), (int) (y + bounds.getY()), (int) bounds.getWidth(), (int) bounds.getHeight());
		if (vX > 0) {
			attackRectangle.x = (int) (x + bounds.x);
			attackRectangle.y = (int) (y + bounds.y) - bounds.height;
			attackRectangle.width = bounds.width * 2;
			attackRectangle.height = bounds.height * 3;
		}
		if (vY > 0) {
			attackRectangle.x = (int) (x + bounds.x) - bounds.width;
			attackRectangle.y = (int) (y + bounds.y);
			attackRectangle.width = bounds.width * 3;
			attackRectangle.height = bounds.height * 2;
		}
		if (vX < 0) {
			attackRectangle.x = (int) (x + bounds.x) - bounds.width;
			attackRectangle.y = (int) (y + bounds.y) - bounds.height;
			attackRectangle.width = bounds.width * 2;
			attackRectangle.height = bounds.height * 3;
		}
		if (vY < 0) {
			attackRectangle.x = (int) (x + bounds.x) - bounds.width;
			attackRectangle.y = (int) (y + bounds.y) - bounds.height;
			attackRectangle.width = bounds.width * 3;
			attackRectangle.height = bounds.height * 2;
		}
		for (Entity e : handler.getChunkManager().getEntitiesFromNeighbours(chunk)) {
			if (!(e instanceof Unit)) {
				continue;
			}
			Unit t = (Unit) e;
			if (t.getTeam() == team) {
				continue;
			}
			if (!t.isAlive()) {
				continue;
			}
			
			if (attackRectangle.intersects(t.getCollisionBounds(0, 0)) || t.getCollisionBounds(0, 0).intersects(attackRectangle)) {
				t.modHealth(t.getHealth() - this.damage);
				if (!t.isAlive()) {
					onKill(t.getExpReward());
				}
			}
		}
	}
	
	public int applyAccuracy(float distance, int coord, int targetCoord) {
		
		Random random = new Random();
		float randomizer = ((random.nextFloat() * 10000 - 5000) / accuracy) * (distance / 32);
		
		return (int) (randomizer + targetCoord);
	}
	
	public void attackCoords(int tX, int tY) {
		if (!attackTimer.isDone()) {
			return;
		}
		float distanceToTarget = (float) (Math.sqrt(Math.pow(tX - getCenterX(), 2) + Math.pow(tY - getCenterY(), 2)) / Tile.TILEWIDTH);
		int targetX = applyAccuracy(distanceToTarget, getCenterX(), tX);
		int targetY = applyAccuracy(distanceToTarget, getCenterY(), tY);
		
		fireProjectile(targetX, targetY);
	}
	
	public void attackTarget() {
		float distanceToTarget = (float) (Math.sqrt(Math.pow(getTargetX() - getCenterX(), 2) + Math.pow(getTargetY() - getCenterY(), 2)) / Tile.TILEWIDTH);
		int tX = applyAccuracy(distanceToTarget, getCenterX(), getTargetX());
		int tY = applyAccuracy(distanceToTarget, getCenterY(), getTargetY());
		
		fireProjectile(tX, tY);
	}
	
	protected void fireProjectile(int tX, int tY) {
		handler.getEntityManager().addToAddList(new Linear(handler, getCenterX(), getCenterY(), damage, this, tX, tY));
	}
	
	
	public boolean intersectsWithSolids(Line2D.Double lOS) {
		
		int tileX1 = (int) lOS.getX1() / 32;
		int tileY1 = (int) lOS.getY1() / 32;
		
		int tileX2 = (int) lOS.getX2() / 32;
		int tileY2 = (int) lOS.getY2() / 32;
		
		
		int facingX = tileX2 - tileX1;
		int facingY = tileY2 - tileY1;
		
		//on same tile
		if (facingX == 0 && facingY == 0) {
			return true;//TODO idk
		}
		
		if (Math.abs(facingX) > 0) {
			facingX = facingX / Math.abs(facingX);
		}
		
		if (Math.abs(facingY) > 0) {
			facingY = facingY / Math.abs(facingY);
		}
		
		boolean collision = false;
		int checkerX = tileX1;
		int checkerY = tileY1;
		
		while (collision == false) {
			//if true, line of sight is broken
			if (TileChecker.outOfMap(checkerX * 32, checkerY * 32)) {
				System.err.println("out of the map in unit");
				collision = true;
				return true;
			}
			if (TileChecker.isSolid(checkerX * 32, checkerY * 32)) {
				collision = true;
				return true;
			}
			
			if (targetIsUnit) {
				
				Unit[] skipped = new Unit[2];
				skipped[0] = this;
				skipped[1] = targetUnit;
				if (TileChecker.buildingsOnTile(checkerX * 32, checkerY * 32, skipped)) {
					collision = true;
					return true;
				}
			} else {
				if (TileChecker.buildingsOnTile(checkerX * 32, checkerY * 32, this)) {
					collision = true;
					return true;
				}
			}
			
			//if next step on X doesn't intersect with line, then it must on y
			if (facingX == 0) {
				checkerY += facingY;
			} else if (facingY == 0) {
				checkerX += facingX;
			} else {
				if (lOS.intersects((checkerX + facingX) * 32, checkerY * 32, 32, 32)) {
					checkerX += facingX;
				} else {
					checkerY += facingY;
				}
			}
			//if it reaches the end tile, line of sight is unbroken
			if (checkerX == tileX2 && checkerY == tileY2) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isControlled() {
		return controlled;
	}
	
	public void setControlled(boolean controlled) {
		this.controlled = controlled;
	}
	
	//What this Unit gives the killing Unit, ie exp/team gold
	public abstract int getExpReward();
	
	//Getters and Setters
	
	public int getIdleTime() {
		return (int) idleTimer.getDelay();
	}
	
	public void setIdleTime(int delay) {
		idleTimer.setDelay(delay);
	}
	
	
	public void removeTarget() {
		noTarget = true;
		targetIsUnit = false;
		targetUnit = null;
	}
	
	public void setTargetIsUnit(boolean targetIsUnit) {
		this.targetIsUnit = targetIsUnit;
	}
	
	public boolean isTargetIsUnit() {
		return targetIsUnit;
	}
	
	public boolean hasNoTarget() {
		return noTarget;
	}
	
	public int getTargetX() {
		if (noTarget) {
			System.out.println(this + " has no target");
			return -1;
		} else if (targetIsUnit) {
			return targetUnit.getCenterX();
		} else {
			return targetX;
		}
	}
	
	public void setTargetX(int targetX) {
		this.targetX = targetX;
		noTarget = false;
		targetIsUnit = false;
	}
	
	public int getTargetY() {
		if (noTarget) {
			System.out.println(this + " has no target");
			return -1;
		} else if (targetIsUnit) {
			return targetUnit.getCenterY();
		} else {
			return targetY;
		}
	}
	
	public void setTargetY(int targetY) {
		this.targetY = targetY;
		noTarget = false;
		targetIsUnit = false;
	}
	
	public ArrayList<Node> getPath() {
		return path;
	}
	
	public void setPath(ArrayList<Node> path) {
		this.path = path;
	}
	
	public boolean hasObjective() {
		return objective;
	}
	
	public void setObjective(boolean objective) {
		this.objective = objective;
	}
	
	public int getObjectiveX() {
		return objectiveX;
	}
	
	public void setObjectiveX(int objectiveX) {
		this.objectiveX = objectiveX;
	}
	
	public int getObjectiveY() {
		return objectiveY;
	}
	
	public void setObjectiveY(int objectiveY) {
		this.objectiveY = objectiveY;
	}
	
	public UnitState getState() {
		return state;
	}
	
	public void setState(UnitState state) {
		this.state = state;
	}
	
	public float getDamage() {
		return damage;
	}
	
	public void setDamage(float damage) {
		this.damage = damage;
	}
	
	public float getAttackRange() {
		return attackRange;
	}
	
	public void setAttackRange(float attackRange) {
		this.attackRange = attackRange;
	}
	
	public float getDetectionRange() {
		return detectionRange;
	}
	
	public void setDetectionRange(float detectionRange) {
		this.detectionRange = detectionRange;
	}
	
	
	public int getAccuracy() {
		return accuracy;
	}
	
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	
	public int getLevel() {
		return level;
	}
	
	public float getAttackSpeed() {
		return (float) (1 / attackTimer.getDelay() * 1000);
	}
	
	public void setAttackSpeed(float attackSpeed) {
		attackTimer.setDelay(1 / attackSpeed * 1000);
		if (attackTimer.getDelay() != 0) {
			attackTimer.activate();
		} else {
			attackTimer.deactivate();
		}
	}
	
	public Line2D.Double getlOS() {
		if (noTarget) {
			return new Line2D.Double(getCenterX(), getCenterY(), getObjectiveX(), getObjectiveY());
		}
		return new Line2D.Double(getCenterX(), getCenterY(), getTargetX(), getTargetY());
		
	}
	
	private Line2D.Double getlOS(int tX, int tY) {
		return new Line2D.Double(getCenterX(), getCenterY(), tX, tY);
	}
	
	public boolean isRanged() {
		return ranged;
	}
	
	public void setRanged(boolean ranged) {
		this.ranged = ranged;
	}
	
	public boolean isMelee() {
		return melee;
	}
	
	public void setMelee(boolean melee) {
		this.melee = melee;
	}
	
	public boolean isMobile() {
		return !(speed == 0);
	}
	
	public Unit getTargetUnit() {
		return targetUnit;
	}
	
	public void setTargetUnit(Unit targetUnit) {
		this.targetUnit = targetUnit;
		noTarget = false;
		targetIsUnit = true;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public boolean isCommanded() {
		return commanded;
	}
	
	public void setCommanded(boolean commanded) {
		this.commanded = commanded;
	}
	
	public UIRectangleContainer getUICommandBox() {
		return uiCommandBox;
	}
	
	public void setUICommandBox(UIRectangleContainer uiCommandBox) {
		this.uiCommandBox = uiCommandBox;
	}
	
	//avoiding instanceof in gameuserinterface REVAMP EXP (allow weird ways of leveling through sections or any other OVERWRIDING METHOD)
	public boolean hasExp() {
		return false;
	}
	
	public int getExp() {
		return 0;
		
	}
	
	public int getExpToLvl() {
		return 0;
	}
	
	
	public void setDefcon(int defcon) {
		this.defcon = defcon;
		
	}
	
	
	public int getDistanceToGoal() {//TODO
		return (int) Math.sqrt(Math.pow((objectiveX + 16 - getCenterX()), 2) + Math.pow((objectiveY + 16 - getCenterY()), 2)) / Tile.TILEWIDTH;
	}
	
	
	public int getCommandNum() {
		return commandNum;
	}
	
	
	public void setCommandNum(int commandNum) {
		this.commandNum = commandNum;
	}
	
	
	public abstract void command(double delta);
	
}
