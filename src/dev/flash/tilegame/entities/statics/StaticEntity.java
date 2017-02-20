package dev.flash.tilegame.entities.statics;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.entities.Entity;
import dev.flash.tilegame.entities.EntityVisitor;

public abstract class StaticEntity extends Entity {
	
	public StaticEntity(Handler handler, float x, float y, int w, int h) {
		super(handler, x, y, w, h, 0);
	}
	
	@Override
	public void accept(EntityVisitor visitor) {
		visitor.visit(this);
	}
}
