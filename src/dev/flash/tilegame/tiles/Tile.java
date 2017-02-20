package dev.flash.tilegame.tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Tile {
	
	//STATIC
	public static Tile[] tiles = new Tile[256];
	public static Tile grassTile = new GrassTile(0);
	public static Tile rockTile = new WallTile2(1);
	public static Tile treeTile = new TreeTile(2);
	public static Tile wallTile = new WallTile(3);
	public static Tile stoneTile = new StoneTile(4);
	
	//CLASS
	public static final int DEFAULT_COST_MULT = 1;
	
	public static final int TILEWIDTH = 32, TILEHEIGHT = 32;
	
	protected BufferedImage texture;
	protected int id;
	protected int x;
	protected int y;
	
	//weight this tile has on entity pathing
	protected float weight;
	
	public Tile(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;
		
		tiles[id] = this;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
	}
	
	//whether entities can travel on this tile
	public boolean isSolid() {
		return false;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public String getName() {
		return null;
	}
	
	public float getWeight() {
		return weight;
	}
	
	public void setWeight(float weight) {
		this.weight = weight;
	}
}
