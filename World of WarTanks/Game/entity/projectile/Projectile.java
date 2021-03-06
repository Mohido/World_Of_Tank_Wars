package Game.entity.projectile;

import Game.entity.Entity;
import Game.graphics.Sprite;
import Game.level.Level;

public class Projectile extends Entity{
	double x, y;
	final double dx, dy;
	final int speed;
	double range;
	final int damage, xOrigin, yOrigin;
	final int rateOfFire;
	
	public Projectile(int x, int y, double dx ,double dy, int range, int speed, int rateOfFire, int damage, Sprite sprite, Level level){
		this.x = x;
		this.y = y;
		xOrigin = x;
		yOrigin = y;
		this.dx = dx * speed;
		this.dy = dy * speed;
		this.level = level;
		this.speed = speed; 
		this.range = range;
		this.sprite = sprite;
		this.damage = damage;
		this.rateOfFire = rateOfFire; /// frames per one shot
	}
	public Projectile(int range, int speed, int rateOfFire, int damage, Sprite sprite, Level level){
		this.dy = 0 ;
		this.dx = 0;
		this.xOrigin = 0;
		this.yOrigin = 0;
		this.level = level;
		this.speed = speed; 
		this.range = range;
		this.sprite = sprite;
		this.damage = damage;
		this.rateOfFire = rateOfFire; /// frames per one shot
	}
	public Projectile(int x, int y, double dx ,double dy, Projectile proj) {
		this.x = x;
		this.y = y;
		xOrigin = x;
		yOrigin = y;
		this.dx = dx;
		this.dy = dy;
		this.level = proj.level;
		this.speed = proj.speed; 
		this.damage = proj.damage;
		this.sprite = proj.sprite;
		this.range = proj.range;
		this.rateOfFire = proj.rateOfFire;
	}
	
	
	public void update(){
		for(int i = 0 ; i < this.speed ; i++) {
			if(!this.level.checkCollision( (int)(this.x + this.dx*i), (int)(this.y + this.dy*i), this) ) { 
				if(!this.level.checkCollisionNPC( (int)(this.x + this.dx*i), (int)(this.y + this.dy*i), this)) {	
					if(getCurrentDistance() < this.range) this.move();
					else {
						this.remove(); //adding an explosion after the removed entity and checking enemies collision!
					}
				}else {
					this.x += this.dx*i;
					this.y += this.dy*i;
					this.remove();
				}
			}
			else {
				this.remove();
			}
		}
	}
	
	public void render() {
		this.level.renderEntity((int)x , (int)y ,this);
	}
	
/// more internal functionality
	private void move() {
		this.x += this.dx;
		this.y += this.dy;
	}
	
	private double getCurrentDistance() {
		double xa = (this.dx + this.x) - this.xOrigin;
		double ya = (this.dy + this.y) - this.yOrigin;
		return Math.sqrt( xa*xa + ya * ya );
	}
	public int getFireRate() {return this.rateOfFire;}
	public void setRange(int range) { this.range = range;}
	public int getX() { return (int)x;}
	public int getY() { return (int)y;}
	public int getDamage() {return this.damage;}
}
