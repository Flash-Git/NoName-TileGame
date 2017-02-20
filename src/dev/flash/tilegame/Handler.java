package dev.flash.tilegame;

import dev.flash.tilegame.entities.EntityManager;
import dev.flash.tilegame.gfx.GameCamera;
import dev.flash.tilegame.input.KeyManager;
import dev.flash.tilegame.input.MouseManager;
import dev.flash.tilegame.menu.Menu;
import dev.flash.tilegame.rules.RuleManager;
import dev.flash.tilegame.tiles.ChunkManager;
import dev.flash.tilegame.timers.TimerManager;
import dev.flash.tilegame.ui.GameUserInterface;
import dev.flash.tilegame.ui.UserInterface;
import dev.flash.tilegame.worlds.SpriteViewerWorld;
import dev.flash.tilegame.worlds.World;
import dev.flash.tilegame.worlds.WorldEditor;

public class Handler {
	private Game game;
	private World world;
	private SpriteViewerWorld spriteViewerWorld;
	private Menu menu;
	private UserInterface userInterface;
	private GameUserInterface gameUserInterface;
	private WorldEditor worldEditor;
	
	public Handler(Game game) {
		this.game = game;
	}
	
	public GameCamera getGameCamera() {
		return game.getGameCamera();
	}
	
	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}
	
	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}
	
	public RuleManager getRuleManager() {
		return game.getRuleManager();
	}
	
	public EntityManager getEntityManager() {
		return world.getEntityManager();
	}
	
	public ChunkManager getChunkManager() {
		return world.getChunkManager();
	}
	
	public TimerManager getTimerManager() {
		return game.getTimerManager();
	}
	
	public int getWidth() {
		return game.getWidth();
	}
	
	public int getHeight() {
		return game.getHeight();
	}
	
	public int getFPS() {
		return game.getFPS();
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public World getWorld() {
		return world;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public SpriteViewerWorld getSpriteViewerWorld() {
		return spriteViewerWorld;
	}
	
	public void setSpriteViewerWorld(SpriteViewerWorld spriteViewerWorld) {
		this.spriteViewerWorld = spriteViewerWorld;
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	public void setUserInterface(UserInterface userInterface) {
		this.userInterface = userInterface;
	}
	
	public UserInterface getUserInterface() {
		return userInterface;
	}
	
	public void setGameUserInterface(GameUserInterface gameUserInterface) {
		this.gameUserInterface = gameUserInterface;
		
	}
	
	public GameUserInterface getGameUserInterface() {
		return gameUserInterface;
	}
	
	public WorldEditor getWorldEditor() {
		return worldEditor;
	}
	
	public void setWorldEditor(WorldEditor worldEditor) {
		this.worldEditor = worldEditor;
	}
	
	
}
