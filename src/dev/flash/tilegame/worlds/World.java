package dev.flash.tilegame.worlds;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.entities.EntityManager;
import dev.flash.tilegame.entities.EntitySorter;
import dev.flash.tilegame.entities.projectiles.Projectile;
import dev.flash.tilegame.entities.units.buildings.Barracks;
import dev.flash.tilegame.entities.units.buildings.Tower;
import dev.flash.tilegame.entities.units.creatures.Builder;
import dev.flash.tilegame.entities.units.creatures.Creature;
import dev.flash.tilegame.entities.units.creatures.Mudcrab;
import dev.flash.tilegame.input.KeyManager;
import dev.flash.tilegame.pathfinding.Node;
import dev.flash.tilegame.rules.Rule;
import dev.flash.tilegame.rules.RuleManager;
import dev.flash.tilegame.tiles.*;
import dev.flash.tilegame.timers.Timer;
import dev.flash.tilegame.timers.TimerManager;
import dev.flash.tilegame.utils.Utils;
import dev.flash.tilegame.waves.Wave;

import java.awt.*;
import java.util.ArrayList;

public class World {
	
	private Handler handler;
	private int width, height;
	private int spawnX, spawnY;
	private int gold;
	
	private int[][] tiles;
	
	int waveIncr;
	
	//Managers
	private EntityManager entityManager;
	private ChunkManager chunkManager;
	
	private TileManager tileManager;
	private RuleManager ruleManager;
	private KeyManager keyManager;//is it a good idea to import rather just going through handler?
	private TimerManager timerManager;
	private Timer goldGen;
	
	//private boolean loading = false;//TODO
	
	public static ArrayList<Chunk> chunks = new ArrayList<>();
	
	public static ArrayList<Node> allNodes = new ArrayList<>();//All Nodes
	
	public World(Handler handler, String path) {
		this.handler = handler;
		handler.setWorld(this);
		TileChecker.handler = handler;
		entityManager = new EntityManager(handler);
		tileManager = new TileManager(handler);
		chunkManager = new ChunkManager(handler);
		
		timerManager = handler.getTimerManager();
		ruleManager = handler.getRuleManager();//better than just using handler.xyz every time?
		keyManager = handler.getKeyManager();
		
		//	loading=true;
		loadWorld(path);
		//	loading=false;
		
		
	}
	
	//Loads a world based on text file
	private void loadWorld(String path) {
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		tiles = new int[width][height];
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
				Tile t = getTile(x, y);
				t.setX(x);
				t.setY(y);
				
				tileManager.addTile(t);
				allNodes.add(new Node(x, y, null));
				System.out.println(t.getName() + " " + t.getX() + " " + t.getY());
				
				if (x % 5 == 0 && y % 5 == 0) {
					chunks.add(new Chunk(handler, x * Tile.TILEHEIGHT, y * Tile.TILEHEIGHT, 5 * Tile.TILEWIDTH, 5 * Tile.TILEHEIGHT));
					System.out.println("Chunk " + x + ", " + y);
				}
			}
		}
		chunkManager.setChunks(chunks);
		System.out.println(chunks.size() + " chunks on map");
		
		System.out.println(tileManager.getTiles().size() + " tiles on map");
		System.out.println("Map Successfully loaded.");
		
		//temp
		entityManager.addToAddList(new Tower(handler, 16 * 32, 14 * 32, 1));
		entityManager.addToAddList(new Tower(handler, 33 * 32, 14 * 32, 1));
		entityManager.addToAddList(new Tower(handler, 16 * 32, 1 * 32, 1));
		entityManager.addToAddList(new Tower(handler, 33 * 32, 1 * 32, 1));
		entityManager.addToAddList(new Barracks(handler, 24 * 32, 1 * 32, 1));
		
		Builder builder = new Builder(handler, 29 * 32, 5 * 32, 1);
		entityManager.addToAddList(builder);
		
		Mudcrab crab = new Mudcrab(handler, 27 * 32, 5 * 32, 1);
		entityManager.addToAddList(crab);
		
		setGold(10000);
		handler.getTimerManager().addToAddList(goldGen = new Timer(5000));
		
		//TODO temp
		wave = new Wave(handler, 1, 50);
		waveIncr = 1;
	}
	
	//TODO temp
	private Wave wave;
	
	public void tick(double delta) {
		Rule r = ruleManager.getRule("paused");
		if (r.getBoolVar() == false) {
			entityManager.tick(delta);
			timerManager.tick(delta);
//			//TODO temp
			wave.tick();
			if (wave.endTimer.isDone()) {
				waveIncr += 1;
				wave = new Wave(handler, waveIncr, 3);
			}
			if (goldGen.isDone()) {
				gold += 5;
			}
		} else {
			timerManager.globalTick(delta);
		}
		getInput();
		
	}
	
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return Tile.grassTile;
		}
		Tile t = Tile.tiles[tiles[x][y]];
		if (t == null) {
			return Tile.grassTile;
		}
		return t;
	}
	
	public void render(Graphics g) {
		//render only the tiles on the screen
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH);
		int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
		int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);
		for (int y = yStart; y < yEnd; y++) {
			for (int x = xStart; x < xEnd; x++) {
				getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()), (int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
			}
		}
		chunkManager.renderChunks(g);
		//Entities
		entityManager.render(g);
	}
	
	private void getInput() {
		//moving camera
		if (entityManager.getControlled() == null) {
			boolean up = handler.getKeyManager().up;
			boolean down = handler.getKeyManager().down;
			boolean left = handler.getKeyManager().left;
			boolean right = handler.getKeyManager().right;
			
			int speed = 5;
			if (up) {
				handler.getGameCamera().move(0, -speed);
			}
			if (down) {
				handler.getGameCamera().move(0, speed);
			}
			if (left) {
				handler.getGameCamera().move(-speed, 0);
			}
			if (right) {
				handler.getGameCamera().move(speed, 0);
			}
		}
		
		//keys
		if (keyManager.c) {
			EntitySorter sorter = new EntitySorter();
			for (Creature e : entityManager.getEntitySorter().creatures) {
				e.accept(sorter);
			}
			for (Projectile e : entityManager.getEntitySorter().projectiles) {
				e.accept(sorter);
			}
			entityManager.removeList(sorter.entities);
		}
		
		Rule r;
		
		if (keyManager.p) {
			r = ruleManager.getRule("paused");
			r.swapBoolVar();
		}
		
		if (keyManager.b) {
			r = ruleManager.getRule("bounds");
			r.swapBoolVar();
		}
		
		if (keyManager.e) {
			r = ruleManager.getRule("entity collision");
			r.swapBoolVar();
		}
	}
	
	//Getters and setters
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public ChunkManager getChunkManager() {
		return chunkManager;
	}
	
	public TileManager getTileManager() {
		return tileManager;
	}
	
	public int getSpawnX() {
		return spawnX;
	}
	
	public void setSpawnX(int spawnX) {
		this.spawnX = spawnX;
	}
	
	public int getSpawnY() {
		return spawnY;
	}
	
	public void setSpawnY(int spawnY) {
		this.spawnY = spawnY;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getGold() {
		return gold;
	}
	
	public void setGold(int gold) {
		this.gold = gold;
	}
}
