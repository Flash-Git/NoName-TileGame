package dev.flash.tilegame.tiles;

import dev.flash.tilegame.gfx.Assets;

public class SandTile extends Tile {
	
	public SandTile(int id) {
		super(Assets.grass1, id);
		weight = 1.2f;
		
	}
	
	public String getName() {
		return "Sand";
	}
	
	
}
