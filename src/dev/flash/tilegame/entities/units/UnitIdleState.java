package dev.flash.tilegame.entities.units;


public class UnitIdleState extends UnitState {
	public UnitIdleState(Unit unit) {
		super(unit);
	}
	
	@Override
	public void tick(double delta) {
		if (unit.idleTimer.isDone()) {
			unit.idle(delta);
		} else {
			unit.followPath(delta);
		}
	}
}
