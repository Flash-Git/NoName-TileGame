package dev.flash.tilegame.tiles;

import dev.flash.tilegame.gfx.Assets;

public class WallTile extends Tile {
	
	public WallTile(int id) {
		super(Assets.wall1, id);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
	
	public String getName() {
		return "Wall";
	}
	
}
