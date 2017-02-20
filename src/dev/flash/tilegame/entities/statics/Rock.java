package dev.flash.tilegame.entities.statics;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.gfx.Assets;
import dev.flash.tilegame.tiles.Tile;

import java.awt.*;

public class Rock extends StaticEntity {
	
	public Rock(Handler handler, float x, float y) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = width;
		bounds.height = height;
	}
	
	@Override
	public void tick(double delta) {
		
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.rock1, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		g.setColor(Color.red);
		//g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()), (int) (y + bounds.y - handler.getGameCamera().getyOffset()),
		//	bounds.width, bounds.height);
	}
	
	
}
