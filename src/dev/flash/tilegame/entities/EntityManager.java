package dev.flash.tilegame.entities;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.entities.projectiles.Projectile;
import dev.flash.tilegame.entities.statics.StaticEntity;
import dev.flash.tilegame.entities.units.Unit;
import dev.flash.tilegame.entities.units.buildings.Building;
import dev.flash.tilegame.entities.units.creatures.Creature;
import dev.flash.tilegame.rules.Rule;
import dev.flash.tilegame.tiles.Chunk;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class EntityManager {
	
	private Handler handler;
	private Entity selected;
	private Unit controlled;
	private Unit commanded;
	
	private ArrayList<Entity> entities;
	private ArrayList<Entity> toAdd;
	private ArrayList<Entity> toRemove;
	private EntitySorter entitySorter;
	
	//Sorts entities by their vertical position so they can get rendered properly TODO doesn't always work for some reason
	private Comparator<Entity> renderSorter = new Comparator<Entity>() {
		@Override
		public int compare(Entity a, Entity b) {
			if (a.getY() + a.getHeight() < b.getY() + b.getHeight()) {
				return -1;
			} else if (a.getY() + a.getHeight() < b.getY() + b.getHeight()) {
				return +1;
			} else {
				return 0;
			}
		}
	};
	
	public EntityManager(Handler handler) {//Maybe substitute handler for world
		this.handler = handler;
		entities = new ArrayList<Entity>();
		toAdd = new ArrayList<Entity>();
		toRemove = new ArrayList<Entity>();
		entitySorter = new EntitySorter();
	}
	
	public void tick(double delta) {
		entitySorter.clearAll();
		
		for (Entity e : entities) {
			e.accept(entitySorter);
		}
		
		for (Unit u : entitySorter.units) {
			if (u.isAlive()) {
				
				setTarget(u);
			}
		}
		
		for (Creature c : entitySorter.creatures) {
			if (c.getPushWeight() > 0) {
				push(delta, c);
			}
			c.tick(delta);
		}
		
		for (Building b : entitySorter.buildings) {
			b.tick(delta);
		}
		
		for (Projectile p : entitySorter.projectiles) {
			p.tick(delta);
		}
		for (StaticEntity s : entitySorter.staticEntities) {
			s.tick(delta);
		}
		
		if (!toAdd.isEmpty()) {
			toAddEntities();
			toAdd.clear();
		}
		
		if (!toRemove.isEmpty()) {
			toRemoveEntities();
			toRemove.clear();
		}
	}
	
	public void render(Graphics g) {
		entities.sort(renderSorter);//TODO is adding this here best option
		float xCamOffset = handler.getGameCamera().getxOffset();
		float yCamOffset = handler.getGameCamera().getyOffset();
		int windowWidth = handler.getWidth();
		int windowHeight = handler.getHeight();
		
		//Only render entities on screen
		for (Entity e : entities) {
			if (e.getX() < xCamOffset - 64) {
				continue;
			}
			if (e.getX() > windowWidth + xCamOffset + 64) {
				continue;
			}
			if (e.getY() < yCamOffset - 64) {
				continue;
			}
			if (e.getY() > windowHeight + yCamOffset + 64) {
				continue;
			}
			
			e.render(g);
			
			if (e.equals(selected)) {
				if (e.team == 1) {
					g.setColor(Color.GREEN);
				} else {
					g.setColor(Color.RED);
				}
				g.drawOval((int) (e.getX() - e.width / 2 - xCamOffset), (int) (e.getY() - e.height / 2 - yCamOffset), e.getWidth() * 2, e.getHeight() * 2);
			}
			if (e.equals(commanded)) {
				g.setColor(Color.BLUE);
				g.drawOval((int) (e.getX() - e.width / 2 - xCamOffset), (int) (e.getY() - e.height / 2 - yCamOffset), e.getWidth() * 2, e.getHeight() * 2);
			}
			Rule r = handler.getRuleManager().getRule("bounds");
			if (r.getBoolVar() == false) {
				continue;
			} else if (r.getBoolVar() == true) {
				
				g.setColor(Color.blue);
				
				g.fillRect(e.getCollisionBounds(0, 0).x - (int) xCamOffset, e.getCollisionBounds(0, 0).y - (int) yCamOffset, e.getCollisionBounds(0, 0).width, e.getCollisionBounds(0, 0).height);
			}
		}
	}
	
	private void push(double delta, Creature i) {//TODO Variables on movers is SO fucked, rethink them
		EntitySorter sorter = new EntitySorter();
		
		for (Entity e : handler.getChunkManager().getEntitiesFromNeighbours(i.getChunk())) {
			e.accept(sorter);
		}
		for (Creature p : sorter.creatures) {
			if (p.equals(i)) {
				continue;
			}
			if (i.getCollisionBounds(0, 0).intersects(p.getCollisionBounds(0, 0))) {
				if (p.getCollisionBounds(0, 0).getCenterX() < i.getCollisionBounds(0, 0).getCenterX()) {
					p.setRelativeX(p.getRelativeX() - 3);
				} else if (p.getCollisionBounds(0, 0).getCenterX() > i.getCollisionBounds(0, 0).getCenterX()) {
					p.setRelativeX(p.getRelativeX() + 3);
				}
//					else{
//					Random rand = new Random();
//					p.move(delta, p.getX()+(rand.nextInt(3)-1)*delta/1000*60, p.getY());//TODO REDO THIS BS
//				}
				
				if (p.getCollisionBounds(0, 0).getCenterY() < i.getCollisionBounds(0, 0).getCenterY()) {
					p.setRelativeY(p.getRelativeY() - 3);
				} else if (p.getCollisionBounds(0, 0).getCenterY() > i.getCollisionBounds(0, 0).getCenterY()) {
					p.setRelativeY(p.getRelativeY() + 3);
				}
//				else{
//					Random rand = new Random();
//					p.move(delta, p.getX(), p.getY()+ rand.nextInt(3)-1);
//				}
			}
		}
	}
	
	private void setTarget(Unit t) {
		Unit bestTarget;
		if (t.hasNoTarget()) {
			bestTarget = null;
		} else {
			bestTarget = t.getTargetUnit();
		}
		float lowestDistance = t.getDetectionRange();
		
		Unit tempT;
		
		for (Unit e : entitySorter.units) {
			if (e.equals(t)) {//Skips itself
				continue;
			}
			
			tempT = e;
			
			if (!tempT.isAlive()) {
				continue;
			}
			
			if (t.getTeam() == tempT.getTeam()) {//Skips same team
				continue;
			}
			
			//if target is within minimal distance and closer than previous best target (if present)
			//set it to best target
			
			//Distance in tiles
			float distance = (float) Math.sqrt(Math.pow(((int) tempT.getX() / 32 - t.getX() / 32), 2) + Math.pow(((int) tempT.getY() / 32 - t.getY() / 32), 2));
			if (distance < t.getDetectionRange() && distance < lowestDistance) {
				lowestDistance = (int) distance;
				bestTarget = tempT;//changing this to a possibleTarget as well as switching bestTarget to Creature changes functionality unexpectedly
			}
		}
//		if((bestTarget==null||!bestTarget.isAlive())){//if c is hostile and no target is in range, move towards the map goal
//			
//		}
		if (bestTarget != null && bestTarget.isAlive()) {
			t.setTargetUnit(bestTarget);
			return;
		}
		t.removeTarget();
	}
	
	public void removeList(ArrayList<Entity> entities) {//TODO
		for (Entity e : entities) {
			addToRemoveList(e);
		}
	}
	
	public void killEntities(ArrayList<Entity> list) {
		for (Entity e : list) {
			e.die();
		}
	}
	
	public void sortEntitiesOnTile(EntitySorter sorter, int x, int y) {
		for (Entity e : getEntitiesOnTile(x, y)) {
			e.accept(sorter);
		}
	}
	
	public void sortEntitiesOnTile(EntitySorter sorter, int x, int y, Entity skip) {
		for (Entity e : getEntitiesOnTile(x, y)) {
			if (skip.equals(e)) {
				continue;
			}
			e.accept(sorter);
		}
	}
	
	public void sortEntitiesOnTile(EntitySorter sorter, int x, int y, Entity[] skip) {
		outerloop:
		for (Entity e : getEntitiesOnTile(x, y)) {
			for (int i = 0; i < skip.length; i++) {
				if (skip[i].equals(e)) {
					continue outerloop;
				}
			}
			e.accept(sorter);
		}
	}
	
	public ArrayList<Unit> getUnitsOnTile(int x, int y) {
		EntitySorter sorter = new EntitySorter();
		sortEntitiesOnTile(sorter, x, y);
		return sorter.units;
	}
	
	public ArrayList<Unit> getUnitsOnTile(int x, int y, Unit skipped) {
		EntitySorter sorter = new EntitySorter();
		sortEntitiesOnTile(sorter, x, y, skipped);
		return sorter.units;
	}
	
	public ArrayList<Unit> getUnitsOnTile(int x, int y, Unit[] skipped) {
		EntitySorter sorter = new EntitySorter();
		sortEntitiesOnTile(sorter, x, y, skipped);
		return sorter.units;
	}
	
	public ArrayList<Creature> getCreaturesOnTile(int x, int y) {
		EntitySorter sorter = new EntitySorter();
		sortEntitiesOnTile(sorter, x, y);
		return sorter.creatures;
	}
	
	public ArrayList<Building> getBuildingsOnTile(int x, int y) {
		EntitySorter sorter = new EntitySorter();
		sortEntitiesOnTile(sorter, x, y);
		return sorter.buildings;
	}
	
	public ArrayList<Building> getBuildingsOnTile(int x, int y, Unit skipped) {
		EntitySorter sorter = new EntitySorter();
		sortEntitiesOnTile(sorter, x, y, skipped);
		return sorter.buildings;
	}
	
	public ArrayList<Building> getBuildingsOnTile(int x, int y, Unit[] skipped) {
		EntitySorter sorter = new EntitySorter();
		sortEntitiesOnTile(sorter, x, y, skipped);
		return sorter.buildings;
	}
	
	public ArrayList<Entity> getEntitiesOnTile(int x, int y) {
		ArrayList<Entity> list = new ArrayList<Entity>();
		Rectangle rect = new Rectangle(x / 32 * 32, y / 32 * 32, 32, 32);
		
		for (Entity e : handler.getChunkManager().getEntitiesFromNeighbours(handler.getChunkManager().getChunk(x, y))) {
			if (e.getCollisionBounds(0, 0).intersects(rect)) {
				list.add(e);
			}
		}
		return list;
	}
	
	public ArrayList<Entity> getEntitiesInRect(int x, int y, int width, int height) {
		ArrayList<Entity> list = new ArrayList<Entity>();
		Rectangle rect = new Rectangle(x, y, width, height);
		for (Entity e : handler.getChunkManager().getEntitiesFromNeighbours(handler.getChunkManager().getChunk(x, y))) {
			if (e.getCollisionBounds(0, 0).intersects(rect)) {
				list.add(e);
			}
		}
		return list;
	}
	
	public Unit getUnitCollision(Entity entity) {
		EntitySorter sorter = new EntitySorter();
		
		if (entity.getChunk() == null) {//this entity has not been ticked(probably not added to world yet, ex:being build)
			for (Entity e : handler.getChunkManager().getEntitiesFromNeighbours(handler.getChunkManager().getChunk(entity.getCenterX(), entity.getCenterY()))) {
				if (entity.equals(e)) {
					continue;
				}
				e.accept(sorter);
			}
		} else {
			for (Entity e : handler.getChunkManager().getEntitiesFromNeighbours(entity.getChunk())) {
				if (entity.equals(e)) {
					continue;
				}
				e.accept(sorter);
			}
		}
		for (Unit e : sorter.units) {
			
			//System.out.println(entity.getCenterX()+" "+entity.getCenterY()+" "+e.getCenterX()+" "+e.getCenterY());//TODO
//			if(Utils.rectCollides(entity.getCollisionBounds(0, 0),e.getCollisionBounds(0, 0))){
//				return e;
//			}
			if (entity.getCollisionBounds(0, 0).intersects(e.getCollisionBounds(0, 0))) {
				return e;
			}
		}
		return null;
	}
	
	public Unit getUnitCollision(Entity entity, Entity skipped) {
		EntitySorter sorter = new EntitySorter();
		Chunk chunk;
		if (entity.getChunk() == null) {//this entity has not been ticked(probably not added to world yet, ex:being build)
			chunk = handler.getChunkManager().getChunk(entity.getCenterX(), entity.getCenterY());
			for (Entity e : handler.getChunkManager().getEntitiesFromNeighbours(chunk)) {
				e.accept(sorter);
			}
		} else {
			for (Entity e : handler.getChunkManager().getEntitiesFromNeighbours(entity.getChunk())) {
				e.accept(sorter);
			}
		}
		for (Unit e : sorter.units) {
			if (entity.equals(e)) {
				continue;
			}
			if (skipped.equals(e)) {
				continue;
			}
			if (entity.getCollisionBounds(0, 0).intersects(e.getCollisionBounds(0, 0))) {
				return e;
			}
		}
		return null;
	}
	
	private void toAddEntities() {
		for (Entity e : toAdd) {
			addEntity(e);
			e.initialise();//TODO if normal initialisation doesn't prove to be effective
		}
	}
	
	private void toRemoveEntities() {
		for (Entity e : toRemove) {
			removeEntity(e);
		}
	}
	
	public void addToAddList(Entity e) {
		toAdd.add(e);
	}
	
	public void addToRemoveList(Entity e) {
		toRemove.add(e);
	}
	
	private void addEntity(Entity e) {
		entities.add(e);
	}
	
	public void removeEntity(Entity e) {
		if (e.isPersistent()) {
			return;
		}
		if (e.isPersistent() == false) {
			e.die();
			e.getChunk().removeEntity(e);
			handler.getTimerManager().addToRemoveList(e.getTimers());
			entities.remove(e);
		} else {
			System.err.println("Entity has no tag");
			System.exit(0);
		}
	}
	
	//getters and setter
	public Handler getHandler() {
		return handler;
	}
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public Unit getControlled() {
		return controlled;
	}
	
	public void setControlled(Unit unit) {
		if (controlled == null) {
			controlled = unit;
		} else {
			controlled.setControlled(false);
		}
		if (unit == null) {
			controlled = null;
			return;
		}
		controlled = unit;
		controlled.setControlled(true);
		controlled.getPath().clear();
	}
	
	public Unit getCommanded() {
		return commanded;
	}
	
	public void setCommanded(Unit unit) {
		if (commanded == null) {
			commanded = unit;
		} else {
			commanded.setCommanded(false);
		}
		commanded = unit;
		commanded.setCommanded(true);
	}
	
	public Entity getSelected() {
		return selected;
	}
	
	public void setSelected(Entity entity) {
		if (selected == null) {
			selected = entity;
		} else {
			selected.setSelected(false);
		}
		selected = entity;
		selected.setSelected(true);
	}
	
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}
	
	public EntitySorter getEntitySorter() {
		return entitySorter;
	}
	
	public void setEntitySorter(EntitySorter entitySorter) {
		this.entitySorter = entitySorter;
	}
	
}
