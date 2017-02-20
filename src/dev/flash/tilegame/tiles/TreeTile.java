package dev.flash.tilegame.tiles;

import dev.flash.tilegame.gfx.Assets;

public class TreeTile extends Tile {
	
	public TreeTile(int id) {
		super(Assets.wall2, id);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
	
	public String getName() {
		return "Stone";
	}
	
	
}
