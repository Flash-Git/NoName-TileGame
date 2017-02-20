package dev.flash.tilegame.tiles;
//Fairly sure this is useless, but maybe I'll find a use for it??

import dev.flash.tilegame.Handler;

import java.util.ArrayList;

public class TileManager {
	
	private Handler handler;
	private ArrayList<Tile> tiles;
	
	public TileManager(Handler handler) {
		this.handler = handler;
		tiles = new ArrayList<Tile>();
	}
	
	public void addTile(Tile t) {
		tiles.add(t);
	}
	
	public void removeTile(Tile t) {
		tiles.remove(t);
	}
	
	public Tile getTile(int x, int y) {
		for (Tile t : tiles) {
			if (t.getX() == x && t.getY() == y) {
				return t;
			}
		}
		return null;
	}
	
	//getters and setter
	public Handler getHandler() {
		return handler;
	}
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public ArrayList<Tile> getTiles() {
		return tiles;
	}
	
	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = tiles;
	}
}
