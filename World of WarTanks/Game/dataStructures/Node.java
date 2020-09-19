package Game.dataStructures;

import Game.graphics.Tile;

public class Node implements Comparable<Node>{
	private int xCord,yCord ;
	private double hCost, fCost, gCost;
	private Tile tile;
	protected Node parent;
	
	
	
	public Node(int x , int y, Node parent, Tile tile, double hCost, double gCost){
		this.xCord = x;
		this.yCord = y;
		this.parent = parent;
		this.tile = tile;
		this.hCost = hCost;
		this.gCost = gCost;
		this.fCost = hCost + gCost;
	}
	
	public Node getParent() { return this.parent;}

	public double getfCost() {
		return fCost;
	}

	public void setfCost(double fCost) {
		this.fCost = fCost;
	}

	public int getxCord() {
		return xCord;
	}

	public int getyCord() {
		return yCord;
	}

	public void sethCost(double hCost) {
		this.hCost = hCost;
		this.fCost = this.hCost + this.gCost;
	}

	public void setgCost(double gCost) {
		this.gCost = gCost;
		this.fCost = this.hCost + this.gCost;
	}
	
	public double gethCost() {
		return this.hCost;
	}
	

	public boolean traversable() {
		return this.tile.solid();
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Node) 
			return (this.xCord == ((Node)obj).getxCord() && this.yCord == ((Node)obj).getyCord());
		else return false;
	}

	public void setParent(Node parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}

	@Override
	public int compareTo(Node o) {
		// TODO Auto-generated method stub
		if(o.getfCost() < this.getfCost()) return 1;
		if(o.getfCost() == this.getfCost()) return 0;
		if(o.getfCost() > this.getfCost()) return -1;
		
		return 0;
	}

	public Tile getTile() {
		// TODO Auto-generated method stub
		return this.tile;
	}
	
	
	
}
