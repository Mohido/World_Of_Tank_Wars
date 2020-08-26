package Game.entity;

abstract class Entity {
	int x,y;
	boolean removed = false;
	
	public void update() {}
	public void render() {}
	
	public void move(int xShift, int yShift) {}
	
	public boolean collidable() {return false;}
	public void remove() {removed = true;}
	public boolean removed() {return removed;}
	
	
}
