package Game.entity.explosion;

import Game.entity.Entity;
import Game.graphics.Sprite;
import Game.level.Level;

public class Explosion extends Entity{
	
	int duration;
	public Explosion(int x, int y, int duration, Sprite sprite, Level level ) {
		this.sprite = sprite;
		this.level = level;
		this.x = x;
		this.y = y;
		this.duration = duration;
	}
	
	
	int count = 0;
	public void update() {
		if(count > duration) this.remove();
		count++;
	}
	
	public void render() {
		level.renderEntity(x, y, this);
	}
}
