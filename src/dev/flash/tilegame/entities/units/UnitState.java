package dev.flash.tilegame.entities.units;

import dev.flash.tilegame.Handler;

public abstract class UnitState {
	protected Handler handler;
	protected Unit unit;
	
	public UnitState(Unit unit) {
		this.handler = unit.getHandler();
		this.unit = unit;
	}
	
	public abstract void tick(double delta);
}
