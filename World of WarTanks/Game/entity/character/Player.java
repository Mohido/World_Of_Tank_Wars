package Game.entity.character;

import java.util.ArrayList;
import java.util.List;

import Game.Window;
import Game.entity.projectile.Projectile;
import Game.graphics.Sprite;
import Game.inputs.Keyboard;
import Game.inputs.Mouse;
import Game.level.Level;

public class Player extends Charact {
	private int anim = 0;
	private int index = 0;
	private int rate = 0;
	
	private Sprite[] player_animation = null;
	private int spriteRows;
	private int spriteColumns;
	
	private Projectile projectile = null;
	private Mouse mouse = null;
	
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	
	public Player(Level level, int xCoord, int yCoord, int spriteColumns, int spriteRows) {///more functional constructor that allows the player to create his own sprites and then add them to the player_animation array
		this.x = xCoord * 48 ;
		this.y = yCoord * 48 ;
		this.level = level;
		this.level.shift(this.x, this.y);
		this.player_animation = new Sprite[spriteRows * spriteColumns];
		this.spriteRows = spriteRows;
		this.spriteColumns = spriteColumns;
	}
	
	
	public void update() {
		int nx = 0, ny = 0;
		if(Window.keyboard.pressedKeys[Keyboard.A]) {
			nx--;
		}
		if(Window.keyboard.pressedKeys[Keyboard.D]) {
			nx++;
		}
		if(Window.keyboard.pressedKeys[Keyboard.W] == true) {
			ny--;
		}
		if(Window.keyboard.pressedKeys[Keyboard.S]) {
			ny++;
		}
		
		
		if(this.level.checkCollision(this.x + nx, this.y + ny, this) == true || (nx == 0 && ny == 0)) dir = null;
		this.move(nx,0);
		this.move(0,ny);
		
		this.updateAnimation();
		this.shoot();
		
		for(int i = 0; i < this.projectiles.size() ; i++) {
			if(this.projectiles.get(i).isRemoved()) this.projectiles.remove(i);
			else this.projectiles.get(i).update();
		}
		
		anim++;
	}
	
	
	
	
	public void render() {
		this.level.renderCharacter(this);
		for(int i = 0; i < this.projectiles.size() ; i++) {
			this.projectiles.get(i).render();
		}
	}
	
	
/// Functions called in the update method
	private void updateAnimation(){
		if( player_animation != null ) {
			if( dir == Direction.N) {
				if(anim % 10 == 0) {
					this.sprite = player_animation[index % this.spriteColumns + this.spriteColumns * 0];
					index++;
				}
			}
			if( dir == Direction.S) {
				if(anim % 10 == 0) {
					this.sprite = player_animation[index % this.spriteColumns + this.spriteColumns * 1];
					index++;
				}
			}
			if( dir == Direction.E) {
				if(anim % 10 == 0) {
					this.sprite = player_animation[index % this.spriteColumns + this.spriteColumns * 2];
					index++;
				}
			}
			if( dir == Direction.W) {
				if(anim % 10 == 0) {
					this.sprite = player_animation[index % this.spriteColumns + this.spriteColumns * 3];
					index++;
				}
			}
		}else {
			System.out.println("There is no Animation Array to update and render");
		}
	}
	
	private void shoot() {
		if(this.mouse != null && this.projectile != null) {
			if(rate % this.projectile.getFireRate() == 0) {
				///creating projectile
				// getting the ratio of the fire rate 
				double dy = (double ) (this.mouse.y - this.level.getCanvasHeight() / 2);
				double dx = (double ) (this.mouse.x - this.level.getCanvasWidth() / 2);
				
				double angle = Math.atan2(dy, dx);
				
				this.projectiles.add(new Projectile(this.x , this.y , Math.cos(angle) , Math.sin(angle), this.projectile) );
				//System.out.println(Math.cos(angle) + ", " + Math.sin(angle));
				
			}
			rate++;
		}else rate = 0;
	}

	
	
///setting things functionality
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

	
	public void setProjectile(Projectile projectile) {
		this.projectile = projectile;
	}
	
	
///recieving shoot projectile order from level
	public void setMouse(Mouse m) {
		this.mouse = m;
		// TODO Auto-generated method stub
		
	}
	
	
}

