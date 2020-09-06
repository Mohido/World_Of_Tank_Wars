package Game.entity.character;
import Game.entity.Entity;
import Game.graphics.Sprite;

public class Charact extends Entity {
	
	public enum Direction{
		N,E,S,W;
	}
	
	protected Direction dir = null;
	protected int health;
	protected int speed;
	
	public void move(int x, int y) {
		if(this.level.checkCollision(this.x + x, this.y + y, this) == false && this.level.checkCollisionNPC(this.x + x, this.y + y, this) == false) {
			this.x += x;
			this.y += y;
			
			if(x < 0) dir = Direction.W;
			if(x > 0 ) dir = Direction.E;
			if(y < 0) dir = Direction.N;
			if(y > 0 ) dir = Direction.S;
			this.level.shift(x, y);
		}
	}
	
	public void dealDamage(int damage) {}
	
}
