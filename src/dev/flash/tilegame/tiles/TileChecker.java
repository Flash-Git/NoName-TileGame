package dev.flash.tilegame.tiles;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.entities.Entity;
import dev.flash.tilegame.entities.units.Unit;

public class TileChecker {
	
	public static Handler handler;
	
	public static boolean outOfMap(int x, int y) {
		if(x<0||x>handler.getWorld().getWidth()*32||y<0||y>handler.getWorld().getHeight()*32){
			return true;
		}
		return false;
	}

	public static boolean isSolid(int x, int y) {
		if(handler.getWorld().getTile(x/32, y/32).isSolid()){
			return true;
		}
		return false;
	}
	
	public static boolean entitiesOnTile(int x, int y) {
		if(handler.getEntityManager().getEntitiesOnTile(x, y).size()!=0){
			return true;
		}
		return false;
	}

	public static boolean unitsOnTile(int x, int y) {
		if(handler.getEntityManager().getUnitsOnTile(x, y).size()!=0){
			return true;
		}
		return false;
	}
	
	public static boolean unitsOnTile(int x, int y, Unit unit) {
		if(handler.getEntityManager().getUnitsOnTile(x, y, unit).size()!=0){
			return true;
		}
		return false;
	}
	
	public static boolean unitsOnTile(int x, int y, Unit[] unit) {
		if(handler.getEntityManager().getUnitsOnTile(x, y, unit).size()!=0){
			return true;
		}
		return false;
	}

	public static boolean buildingsOnTile(int x, int y) {
		if(handler.getEntityManager().getBuildingsOnTile(x, y).size()!=0){
			return true;
		}
		return false;
	}
	
	public static boolean buildingsOnTile(int x, int y, Unit unit) {
		if(handler.getEntityManager().getBuildingsOnTile(x, y, unit).size()!=0){
			return true;
		}
		return false;
	}
	
	public static boolean buildingsOnTile(int x, int y, Unit[] unit) {
		if(handler.getEntityManager().getBuildingsOnTile(x, y, unit).size()!=0){
			for(Entity e : handler.getEntityManager().getBuildingsOnTile(x, y, unit)){
				System.out.println(e.getType());
			}
			return true;
		}
		return false;
	}

	

	
}
