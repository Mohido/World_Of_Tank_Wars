package Game.entity;

import Game.graphics.Sprite;
import Game.level.Level;

public abstract class Entity {
	protected int x,y;
	protected boolean removed = false;
	protected Sprite sprite;
	protected Level level;
	
	public void update() {}
	public void render() {}
	
	public void move(int xShift, int yShift) {}
	
	public boolean collidable() {return false;}
	public void remove() {removed = true;}
	public boolean isRemoved() {return removed;}
	public Sprite getSprite() {return this.sprite;}
	public int getX() {return this.x;}
	public int getY() {return this.y;}
}
