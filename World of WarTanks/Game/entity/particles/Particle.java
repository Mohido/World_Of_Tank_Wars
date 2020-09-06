package Game.entity.particles;

import java.util.Random;

import Game.entity.Entity;
import Game.graphics.Sprite;
import Game.level.Level;

public class Particle extends Entity {
	
	private double dx,dy;
	private double x, y;
	private int lifeSpan , span;
	private Random rand;
	
	
	public Particle(int x, int y, int lifeSpan, Sprite sprite, Level level) {
		this.x = x;
		this.y = y;
		rand = new Random();
		this.sprite = sprite;
		this.level = level;
		
		this.dx = rand.nextGaussian();
		this.dy = rand.nextGaussian();
		this.lifeSpan = lifeSpan - 5 + rand.nextInt(10) + 1;
	}
	
	public void update() {
		if(span > lifeSpan) {
			this.remove();
			return;
		}
		if( !level.checkCollision( (int)(x + dx),  (int)(y + dy), this) &&
				!level.checkCollisionNPC( (int)(x + dx),  (int)(y + dy), this)) {
			this.move(dx , dy);
			dx *= 0.93;
			dy *= 0.93;
		}else {
			if(level.checkCollision( (int)(x + dx),  (int)(y), this) ) dx *= -1;
			if(level.checkCollision( (int)(x),  (int)(y + dy), this)) dy *= -1;
			if(level.checkCollisionNPC((int)(x),  (int)(y + dy), this)) dy *=-1;
			if( level.checkCollisionNPC( (int)(x + dx), (int)(y), this) ) dx *= -1;
		}		
		span++;
	}
	
	private void move(double dx , double dy) {
		this.x += dx ;
		this.y += dy ;
	}
	
	public void render() {
		level.renderEntity((int)this.x, (int)this.y, this);
	}
}
