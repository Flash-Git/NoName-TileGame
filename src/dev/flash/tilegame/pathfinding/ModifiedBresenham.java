package dev.flash.tilegame.pathfinding;

import dev.flash.tilegame.Handler;
import dev.flash.tilegame.worlds.World;

import java.awt.geom.Line2D;
import java.util.ArrayList;

public class ModifiedBresenham {
	
	private ModifiedBresenham() {
	}
	
	public static void generatePath(Handler handler, ArrayList<Node> path, int tileX1, int tileY1, int tileX2, int tileY2) {
		//reset static variables
		for (Node n : World.allNodes) {
			n.setParent(null);
			n.setWeight(0);
		}
		//System.out.println(tileX1+" "+tileY1+" "+tileX2+" "+tileY2+" ");
		path.clear();
		
		//get direction
		int facingX = tileX2 - tileX1;
		int facingY = tileY2 - tileY1;
		
		//on same tile
		if (facingX == 0 && facingY == 0) {
			return;
		}
		
		//Normalising
		if (facingX != 0) {
			facingX = facingX / Math.abs(facingX);
		}
		
		if (facingY != 0) {
			facingY = facingY / Math.abs(facingY);
		}
		
		Node parent = containsNode(tileX1, tileY1, World.allNodes);//Node that points to the Node that led to it's creation
		if (parent == null) {
			System.err.println("no node found on allNodes list at " + tileX1 + ", " + tileY1 + " for initial parent node");
			return;
		}
		
		Node.dest = containsNode(tileX2, tileY2, World.allNodes);//static destination node
		if (Node.dest == null) {
			System.err.println("no node found on allNodes list at " + tileX2 + ", " + tileY2 + " for destination node");
			return;
		}
		
		
		Line2D.Double lOS = new Line2D.Double(tileX1 * 32 + 16, tileY1 * 32 + 16, tileX2 * 32 + 16, tileY2 * 32 + 16);
		
		for (int q = 0; q < 100; ++q) {
			//if next step on X doesn't intersect with line, then it must on y
			
			if (facingX == 0) {
				if (checkNode(handler, parent.getX(), parent.getY() + facingY, parent)) {
					assemblePath(containsNode(parent.getX(), parent.getY() + facingY, World.allNodes), path);
					return;
				}
				parent = containsNode(parent.getX(), parent.getY() + facingY, World.allNodes);
				
			} else if (facingY == 0) {
				if (checkNode(handler, parent.getX() + facingX, parent.getY(), parent)) {
					assemblePath(containsNode(parent.getX() + facingX, parent.getY(), World.allNodes), path);
					return;
				}
				parent = containsNode(parent.getX() + facingX, parent.getY(), World.allNodes);
				
			} else {
				if (lOS.intersects((parent.getX() + facingX) * 32, parent.getY() * 32, 32, 32)) {
					if (checkNode(handler, parent.getX() + facingX, parent.getY(), parent)) {
						assemblePath(containsNode(parent.getX() + facingX, parent.getY(), World.allNodes), path);
						return;
					}
					parent = containsNode(parent.getX() + facingX, parent.getY(), World.allNodes);
					
				} else if (lOS.intersects(parent.getX() * 32, (parent.getY() + facingY) * 32, 32, 32)) {
					if (checkNode(handler, parent.getX(), parent.getY() + facingY, parent)) {
						assemblePath(containsNode(parent.getX(), parent.getY() + facingY, World.allNodes), path);
						return;
					}
					parent = containsNode(parent.getX(), parent.getY() + facingY, World.allNodes);
					
				} else {
					System.err.println("Line of sight didn't intersect either option???");
					path.clear();
					return;
				}
				
			}
		}
		System.err.println("Over 100 tiles away???");
	}
	
	private static boolean checkNode(Handler handler, int x, int y, Node parent) {
		Node n = containsNode(x, y, World.allNodes);
		
		if (n.getX() == Node.dest.getX() && n.getY() == Node.dest.getY()) {
			n.setParent(parent);
			parent = n;
			return true;
		}

//		if(TileChecker.outOfMap(x*32, y*32)){
//			return false;//if node is out of bounds
//		}
//		if(TileChecker.buildingsOnTile(x*32, y*32)){
//			return false;//if node is out of bounds
//		}
		n.setParent(parent);
		parent = n;
		
		return false;
	}
	
	private static void assemblePath(Node n, ArrayList<Node> path) {
		
		ArrayList<Node> open = new ArrayList<Node>();//open list of Nodes
		
		open.add(n);
		Node p = n.parent;
		
		while (p != null) {
			open.add(p);
			p = p.parent;
		}
		
		for (int i = open.size() - 1; i >= 0; --i) {
			path.add(open.get(i));
		}
		path.remove(0);//removing starting pos
	}
	
	//checks if a Node exists on the given list at x, y
	public static Node containsNode(int x, int y, ArrayList<Node> list) {
		for (Node n : list) {
			if (n.getX() == x && n.getY() == y) {
				return n;
			}
		}
		return null;//if Node isn't on list, return null
	}
}
