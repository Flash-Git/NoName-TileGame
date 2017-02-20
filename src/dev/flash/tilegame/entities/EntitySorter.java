package dev.flash.tilegame.entities;

import dev.flash.tilegame.entities.projectiles.Projectile;
import dev.flash.tilegame.entities.statics.StaticEntity;
import dev.flash.tilegame.entities.units.Unit;
import dev.flash.tilegame.entities.units.buildings.Building;
import dev.flash.tilegame.entities.units.creatures.Creature;

import java.util.ArrayList;

public class EntitySorter implements EntityVisitor {
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Creature> creatures = new ArrayList<Creature>();
	public ArrayList<Building> buildings = new ArrayList<Building>();
	public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public ArrayList<StaticEntity> staticEntities = new ArrayList<StaticEntity>();
	public ArrayList<Unit> units = new ArrayList<Unit>();
	
	@Override
	public void visit(Unit entity) {
		units.add(entity);
		entities.add(entity);
	}
	
	@Override
	public void visit(Creature entity) {
		creatures.add(entity);
		units.add(entity);
		entities.add(entity);
	}
	
	@Override
	public void visit(Building entity) {
		buildings.add(entity);
		units.add(entity);
		entities.add(entity);
	}
	
	@Override
	public void visit(Projectile entity) {
		projectiles.add(entity);
		entities.add(entity);
	}
	
	@Override
	public void visit(StaticEntity entity) {
		staticEntities.add(entity);
		entities.add(entity);
	}
	
	public void clearAll() {
		entities.clear();
		units.clear();
		creatures.clear();
		buildings.clear();
		projectiles.clear();
		staticEntities.clear();
	}
	
}
