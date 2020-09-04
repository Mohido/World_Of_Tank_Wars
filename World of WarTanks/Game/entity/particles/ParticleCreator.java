package Game.entity.particles;

import java.util.ArrayList;
import java.util.List;

import Game.entity.Entity;
import Game.graphics.Sprite;
import Game.level.Level;

public class ParticleCreator {
	
	public ParticleCreator(int x, int y, int amount, int lifeSpan, Sprite sprite, Level level, int maxSpeed) {
		for(int i= 0 ; i < amount ; i++) {
			level.addParticle( new Particle(x,y, lifeSpan, sprite, level , maxSpeed) );
		}
	}
}
