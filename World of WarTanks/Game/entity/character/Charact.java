package Game.entity.character;
import Game.entity.Entity;
import Game.graphics.Sprite;

public class Charact extends Entity {
	
	public enum Direction{
		N,E,S,W,NE ,NW, SW, SE;
	}
	
	protected Direction dir = null;
	protected int health;
	protected int speed;
	
	public void move(int x, int y) {
	}
	
	public void dealDamage(int damage) {}
	
}
