package dev.flash.tilegame.entities;

import dev.flash.tilegame.entities.units.buildings.Building;
import dev.flash.tilegame.entities.units.creatures.Creature;
import dev.flash.tilegame.entities.projectiles.Projectile;
import dev.flash.tilegame.entities.statics.StaticEntity;
import dev.flash.tilegame.entities.units.Unit;

public interface EntityVisitor {
	public void visit(Creature entity);
	public void visit(Building entity);
	public void visit(Projectile entity);
	public void visit(StaticEntity entity);
	public void visit(Unit unit);
}
