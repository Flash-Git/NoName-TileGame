package dev.flash.tilegame.entities;

import dev.flash.tilegame.entities.projectiles.Projectile;
import dev.flash.tilegame.entities.statics.StaticEntity;
import dev.flash.tilegame.entities.units.Unit;
import dev.flash.tilegame.entities.units.buildings.Building;
import dev.flash.tilegame.entities.units.creatures.Creature;

public interface EntityVisitor {
	void visit(Creature entity);
	
	void visit(Building entity);
	
	void visit(Projectile entity);
	
	void visit(StaticEntity entity);
	
	void visit(Unit unit);
}
