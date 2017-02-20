package dev.flash.tilegame.entities.statics;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.gfx.Assets;
import dev.flash.tilegame.tiles.Tile;

import java.awt.*;

public class Tree extends StaticEntity {
	
	public Tree(Handler handler, float x, float y) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT * 2);
		
		bounds.x = 10;
		bounds.y = 42;
		bounds.width = width - 20;
		bounds.height = 8;
	}
	
	@Override
	public void tick(double delta) {
		
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.tree1, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
//		if(handler.getRule(null)){
//			g.setColor(Color.red);
//			g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()), (int) (y + bounds.y - handler.getGameCamera().getyOffset()), 
//					bounds.width, bounds.height);
//		}
	}
	
	
}
