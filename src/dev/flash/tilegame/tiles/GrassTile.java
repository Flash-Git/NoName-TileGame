package dev.flash.tilegame.tiles;

import dev.flash.tilegame.gfx.Assets;

public class GrassTile extends Tile {
	
	public GrassTile(int id) {
		super(Assets.grass1, id);
		weight = 1;
		
	}
	
	public String getName() {
		return "Grass";
	}
	
	
}
