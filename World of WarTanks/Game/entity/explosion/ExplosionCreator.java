package Game.entity.explosion;

import Game.graphics.Sprite;
import Game.level.Level;

public class ExplosionCreator {
	public ExplosionCreator( int x ,int y, int duration, Sprite sprite, Level level) {
		level.addExplosion(new Explosion(x,y, duration, sprite, level));
	}
}
