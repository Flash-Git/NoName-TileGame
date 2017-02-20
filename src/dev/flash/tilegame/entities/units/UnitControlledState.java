package dev.flash.tilegame.entities.units;


public class UnitControlledState extends UnitState {
	
	boolean up = handler.getKeyManager().up;
	boolean down = handler.getKeyManager().down;
	boolean left = handler.getKeyManager().left;
	boolean right = handler.getKeyManager().right;
	
	public UnitControlledState(Unit unit) {
		super(unit);
	}
	
	@Override
	public void tick(double delta) {
		handler.getGameCamera().centerOnEntity(unit);
		
		getInput();
		setVelocity();
		unit.move(delta, unit.getX() + unit.getvX() * delta / 1000 * 60, unit.getY() + unit.getvY() * delta / 1000 * 60);
	}
	
	private void getInput() {
		up = handler.getKeyManager().up;
		down = handler.getKeyManager().down;
		left = handler.getKeyManager().left;
		right = handler.getKeyManager().right;
	}
	
	private void setVelocity() {
		double speed = unit.getSpeed();
		if (up) {
			unit.setvY(-speed);//this.speed;
		} else if (down) {
			unit.setvY(speed);//this.speed;
		} else {
			unit.setvY(0);
		}
		if (left) {
			unit.setvX(-speed);//this.speed;
		} else if (right) {
			unit.setvX(speed);//this.speed;
		} else {
			unit.setvX(0);
		}
		
		if (up && left) {
			unit.setvX(-speed * 0.74);//this.speed;
			unit.setvY(-speed * 0.74);//this.speed;
		}
		if (up && right) {
			unit.setvX(speed * 0.74);//this.speed;
			unit.setvY(-speed * 0.74);//this.speed;
		}
		if (down && left) {
			unit.setvX(-speed * 0.74);//this.speed;
			unit.setvY(speed * 0.74);//this.speed
		}
		if (down && right) {
			unit.setvX(speed * 0.74);//this.speed;
			unit.setvY(speed * 0.74);//this.speed;
		}
		
	}
}