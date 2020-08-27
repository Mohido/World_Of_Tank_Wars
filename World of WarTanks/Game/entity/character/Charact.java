package Game.entity.character;
import Game.entity.Entity;

public class Charact extends Entity {
	
	public void move(int x, int y) {
		if(this.level.checkCollision(x, y,this) == false) {
			this.x+=x;
			this.y+=y;
		}
	}
}
