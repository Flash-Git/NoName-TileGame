package dev.flash.tilegame.waves;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.entities.units.creatures.Creature;
import dev.flash.tilegame.entities.units.creatures.Mudcrab;
import dev.flash.tilegame.entities.units.creatures.Wizard;
import dev.flash.tilegame.entities.units.creatures.Zombie;
import dev.flash.tilegame.timers.Timer;

public class Wave {
	private int type;
	private int eNum;
	private int eCount;
	private Handler handler;
	private Timer spawnTimer;
	public Timer endTimer;
	
	public Wave(Handler handler, int type, int eNum) {
		this.handler = handler;
		this.type = type;
		this.eNum = eNum;
		eCount = 0;
		handler.getTimerManager().addToAddList(spawnTimer = new Timer(4000));
		handler.getTimerManager().addToAddList(endTimer = new Timer(false));
		
	}
	
	public void tick() {
		
		if (spawnTimer.isDone()) {
			if (eCount < eNum) {
				if (type < 5) {
					spawnCreature(new Mudcrab(handler, 0, 0, 2), 5 * 32, 5 * 32);
					spawnCreature(new Mudcrab(handler, 0, 0, 2), 5 * 32, 8 * 32);
					spawnCreature(new Mudcrab(handler, 0, 0, 2), 45 * 32, 5 * 32);
					spawnCreature(new Mudcrab(handler, 0, 0, 2), 45 * 32, 8 * 32);
					spawnCreature(new Mudcrab(handler, 0, 0, 2), 23 * 32, 25 * 32);
					spawnCreature(new Mudcrab(handler, 0, 0, 2), 26 * 32, 25 * 32);
					return;
				}
				if (type < 10) {
					spawnCreature(new Zombie(handler, 0, 0, 2), 5 * 32, 5 * 32);
					spawnCreature(new Zombie(handler, 0, 0, 2), 45 * 32, 5 * 32);
					spawnCreature(new Zombie(handler, 0, 0, 2), 25 * 32, 25 * 32);
					return;
				}
				spawnCreature(new Wizard(handler, 0, 0, 2), 5 * 32, 5 * 32);
				spawnCreature(new Wizard(handler, 0, 0, 2), 45 * 32, 5 * 32);
				spawnCreature(new Wizard(handler, 0, 0, 2), 25 * 32, 25 * 32);
				
			} else if (endTimer.isActive() == false) {
				endTimer.setDelay(15000);
				endTimer.activate();
			}
			
		}
	}
	
	public void spawnCreature(Creature creature, int x, int y) {
		
		if (handler.getEntityManager().getEntitiesOnTile(x, y).isEmpty()) {
			creature.setX(x);
			creature.setY(y);
			handler.getEntityManager().addToAddList(creature);
			//creature.setLevel(type); //TODO can't set level till after initialisation
			
			eCount += 1;
		}
	}
}
