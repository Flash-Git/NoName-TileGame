package dev.flash.tilegame.entities.units;

public class UnitObjectiveState extends UnitState {
	public UnitObjectiveState(Unit unit) {
		super(unit);
	}
	
	@Override
	public void tick(double delta) {
		catchError();
		unit.command(delta);
		
	}
	
	private void catchError() {
		if (!unit.hasObjective()) {
			System.err.println("no objective");
			return;
		}
	}
}
