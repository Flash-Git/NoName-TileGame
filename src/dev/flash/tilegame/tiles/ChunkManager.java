package dev.flash.tilegame.tiles;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.entities.Entity;

import java.awt.*;
import java.util.ArrayList;

public class ChunkManager {
	
	
	private Handler handler;
	private ArrayList<Chunk> chunks;
	
	public ChunkManager(Handler handler) {//Maybe substitute handler for world
		this.handler = handler;
	}
	
	public void renderChunks(Graphics g) {
		for (Chunk chunk : chunks) {
			chunk.render(g);
		}
	}
	
	public void addChunk(Chunk chunk) {
		chunks.add(chunk);
	}
	
	public void removeChunk(Chunk chunk) {//TODO untested
		chunks.remove(chunk);
	}
	
	public Chunk getChunk(int x, int y) {
		for (Chunk chunk : chunks) {
			if (chunk.getX() <= x && chunk.getX() + chunk.getWidth() > x && chunk.getY() <= y && chunk.getY() + chunk.getHeight() > y) {
				return chunk;
			}
		}
		//System.err.println("getChunk("+x+", "+y+") on nonexistent chunk");
		return null;
	}
	
	public ArrayList<Entity> getEntitiesFromNeighbours(Chunk chunk) {
		if (chunk == null) {
			System.err.println("chunk is null at getEntitiesFromNeighbours");
		}
		int x = chunk.getX();
		int y = chunk.getY();
		
		ArrayList<Entity> entities = new ArrayList<Entity>();
		entities.addAll(chunk.getEntities());
		//chunk.selected = true;
		Chunk newChunk;
		
		newChunk = getChunk(x - (32 * 5), y);
		if (newChunk != null) {
			entities.addAll(newChunk.getEntities());
//			newChunk.selected=true;
		}
		newChunk = getChunk(x + (32 * 5), y);
		if (newChunk != null) {
			entities.addAll(newChunk.getEntities());
//			newChunk.selected=true;
		}
		newChunk = getChunk(x, y - (32 * 5));
		if (newChunk != null) {
			entities.addAll(newChunk.getEntities());
//			newChunk.selected=true;
		}
		newChunk = getChunk(x, y + (32 * 5));
		if (newChunk != null) {
			entities.addAll(newChunk.getEntities());
//			newChunk.selected=true;
		}
		return entities;
	}
	
	//getters and setter
	public Handler getHandler() {
		return handler;
	}
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public ArrayList<Chunk> getChunks() {
		return chunks;
	}
	
	public void setChunks(ArrayList<Chunk> chunks) {
		this.chunks = chunks;
	}
}
