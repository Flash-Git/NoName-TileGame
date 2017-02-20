package dev.flash.tilegame.tiles;

import dev.flash.tilegame.gfx.Assets;

public class WallTile2 extends Tile {
	
	public WallTile2(int id) {
		super(Assets.wall2, id);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
	
	@Override
	public String getName() {
		return "Wall2";
	}
	
}
