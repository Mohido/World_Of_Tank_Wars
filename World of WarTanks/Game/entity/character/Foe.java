package Game.entity.character;

import java.util.List;

import Game.entity.character.Charact.Direction;
import Game.entity.particles.ParticleCreator;
import Game.entity.projectile.Projectile;
import Game.graphics.Sprite;
import Game.level.Level;

public class Foe extends Charact{
	private boolean visible = false;
	private int index = 0,  anim = 0, dirReset = 0;
	private static int foesCount = 0;
	
	private double speed = 1.0, path_finding_radius = 100; ///default speed
	private double xh,yh;
	private final int id;///Identifier for the Foes
	
	///sprite animations of moving
	private Sprite[] player_animation = null;
	private int spriteRows;
	private int spriteColumns;
	
	private Projectile projectile = null;
	private List<Direction> heroDirections = null;
	
	public Foe(Level level, int xCoord, int yCoord, int spriteColumns, int spriteRows, int health , Projectile proj) {///more functional constructor that allows the player to create his own sprites and then add them to the player_animation array
		this.x = xCoord * 48 ;
		this.y = yCoord * 48 ;
		this.xh = (double)this.x;
		this.yh = (double)this.y;
		
		this.level = level;
		this.projectile = proj;
		this.player_animation = new Sprite[spriteRows * spriteColumns];
		this.spriteRows = spriteRows;
		this.spriteColumns = spriteColumns;
		this.health = health;
		foesCount++;
		this.id = foesCount;
	}
	
	///with speed constructor
	public Foe(Level level, int xCoord, int yCoord, int spriteColumns, int spriteRows, double speed,  int health , Projectile proj) {///more functional constructor that allows the player to create his own sprites and then add them to the player_animation array
		this.x = xCoord * 48 ;
		this.y = yCoord * 48 ;
		this.xh = (double)this.x;
		this.yh = (double)this.y;
		
		this.level = level;
		this.projectile = proj;
		this.speed = speed;
		this.player_animation = new Sprite[spriteRows * spriteColumns];
		this.spriteRows = spriteRows;
		this.spriteColumns = spriteColumns;
		this.health = health;
		foesCount++;
		this.id = foesCount;
	}
	///___________ Update and render
	public void update() {
		this.updateVisibility();
		
		this.updateDirections();
		this.updateMovement();
		
		this.updateAnimation();
		
		anim++;
		
	}
	
	

	public void render() {
		if(this.visible) this.level.renderCharacter(this);
	}
	
	///________ private functionality
	private void updateAnimation() {
		if(anim % 10 == 0 && this.heroDirections != null) {
			if(dir == Direction.N) {
				this.sprite = player_animation[ index % this.spriteColumns + 0*this.spriteColumns];
				index++;
			}
			if(dir == Direction.S) {
				this.sprite = player_animation[ index % this.spriteColumns + 1*this.spriteColumns];
				index++;
			}
			if(dir == Direction.E) {
				this.sprite = player_animation[ index % this.spriteColumns + 2*this.spriteColumns];
				index++;
			}
			if(dir == Direction.W) {
				this.sprite = player_animation[ index % this.spriteColumns + 3*this.spriteColumns];
				index++;
			}
		}
		
	}
	
	
	private void updateDirections() {
		if (this.level.getHeroDistance(this) > this.path_finding_radius) {
			heroDirections = null ; 
			return;
		}
		
		if(anim % 30 == 0) {
			heroDirections = this.level.findHero(this);
			dirReset = 1;
			for(int i = heroDirections.size() - 1 ; i >= 0 ; i--)
				System.out.println(heroDirections.get(i));
		}else {
			dirReset++;
		}
		
		if(dirReset % 48 == 0 && heroDirections != null)
			if(heroDirections.size() > 0) heroDirections.remove(heroDirections.size() - 1);
	}
	
	private void updateMovement() {
		// TODO Auto-generated method stub
		if (heroDirections != null) {
			if(heroDirections.size() > 0) {
				switch(heroDirections.get(heroDirections.size() - 1)) {
					case N: this.move (0,-1); break;
					case NE: move(1,0); move(0,-1); break;
					case E: move (1,0); break;
					case SE: move(1,0); move(0,1); break;
					case S: move (0,1); break;
					case SW: move(-1,0); move(0,1); break;
					case W: move (-1,0); break;
					case NW: move(-1,0); move(0,-1); break;
					default: break;
				}
				
			}
		}
	}
	
	
	///________ public functionality
	public boolean isVisible() {return this.visible;}
	
	private int rows = 0;
	public void addSpriteRow(Sprite[] sprite) {
		if(sprite.length > 3) {System.out.println("Enter please sprites as the number of columns you have entered!");return;}
		if(rows < this.spriteRows) {
			for(int i = 0 ; i < this.spriteColumns ; i++) {
				this.player_animation[i + rows* this.spriteColumns] = sprite[i];
			}
			this.sprite = this.player_animation[0];
			rows++;
		}else {
			System.out.println("You have exceeded the sprites Limit that you have entered");
		}
	}
	
	public void dealDamage(int damage) {
		this.health -= damage;
		if(this.health <= 0) this.remove();
	}
	
	public void setHeroSearchingRadius(double radius) {
		this.path_finding_radius = radius;
	}
	
	///_____________________ private functionality
	private void updateVisibility() {
		if(this.x > this.level.getX() - 48 && this.x < this.level.getX() + this.level.getCanvasWidth() 
		&& this.y > this.level.getY() - 48 && this.y < this.level.getY() + this.level.getCanvasHeight()) {
			this.visible = true;
		}else{ this.visible = false;}
	}
	
	public void remove() {
		super.remove();
		new ParticleCreator(this.x + this.sprite.getWidth()*3/2 ,
												this.y + this.sprite.getHeight()*3/2 ,
												50, 60,
												new Sprite(2,2,0xfca503), this.level);
		new ParticleCreator(this.x + this.sprite.getWidth()*3/2 ,
				this.y + this.sprite.getHeight()*3/2 ,
				50, 60,
				new Sprite(2,2,0xe80536), this.level);
	}
	
	public void move(int x , int y) {

		
		
		if(this.level.checkCollision((int)(this.xh + x * this.speed) , (int)(this.yh + y * this.speed), this) == false &&
				this.level.checkCollisionNPC((int)(this.xh + x * this.speed) , (int)(this.yh + y * this.speed), this) == false ) {
			this.xh += x * this.speed;
			this.yh += y * this.speed;
			this.x = (int)this.xh;
			this.y = (int)this.yh;
			
			if(x < 0) dir = Direction.W;
			if(x > 0 ) dir = Direction.E;
			if(y < 0) dir = Direction.N;
			if(y > 0 ) dir = Direction.S;
		}
	}
	
	public int getID() {return this.id;}
}
