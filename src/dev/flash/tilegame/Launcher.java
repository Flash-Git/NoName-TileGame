package dev.flash.tilegame;

import dev.flash.tilegame.Game;

public class Launcher {
	public static void main(String[] args){
		Game game = new Game("Tile Game - Alpha v1.43", 1100, 600);
		game.start();
	}
}
