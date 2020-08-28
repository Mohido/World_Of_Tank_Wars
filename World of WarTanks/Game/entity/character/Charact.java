package Game.entity.character;
import Game.entity.Entity;
import Game.graphics.Sprite;

public class Charact extends Entity {
	
	public enum Direction{
		N,E,S,W;
	}
	
	protected Direction dir = null;
	
	public void move(int x, int y) {
		if(this.level.checkCollision(this.x + x, this.y + y, this) == false) {
			this.x += x;
			this.y += y;
			
			if(x == -1) dir = Direction.W;
			if(x == 1 ) dir = Direction.E;
			if(y == -1) dir = Direction.N;
			if(y == 1 ) dir = Direction.S;
			this.level.shift(x, y);
		}
	}
}
