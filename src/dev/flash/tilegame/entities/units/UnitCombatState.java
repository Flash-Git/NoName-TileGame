package dev.flash.tilegame.entities.units;

import dev.flash.tilegame.tiles.Tile;

import java.awt.geom.Line2D;

public class UnitCombatState extends UnitState {
	public UnitCombatState(Unit unit) {
		super(unit);
		
	}
	
	@Override
	public void tick(double delta) {
		catchError();//Temp?
		tryToAttack(delta);
	}
	
	private void catchError() {
		if (!unit.isRanged() && !unit.isMelee()) {
			System.out.println("peaceful unit in combat state");
			return;
		}
		
		if (unit.hasNoTarget()) {
			System.out.println("no target");
			return;
		}
	}
	
	
	private void tryToAttack(double delta) {
		int targetX = unit.getTargetX();
		int targetY = unit.getTargetY();
		
		int distanceToTarget = (int) Math.sqrt(Math.pow(targetX - unit.getCenterX(), 2) + Math.pow(targetY - unit.getCenterY(), 2)) / Tile.TILEWIDTH;
		
		boolean mobile = unit.isMobile();
		
		if (unit.getAttackRange() < distanceToTarget) {
			if (!mobile) {
				return;
			} else {
				unit.pathTo(delta, targetX, targetY, 3);
				return;
			}
		} else {
			Line2D.Double lOS = unit.getlOS();
			if (unit.intersectsWithSolids(lOS)) {
				if (!mobile) {
					return;
				} else {
					unit.pathTo(delta, targetX, targetY, 3);
					return;
				}
			}
		}
		unit.attack(delta);
	}
}
