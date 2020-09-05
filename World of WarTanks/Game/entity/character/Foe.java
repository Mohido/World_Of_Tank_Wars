package Game.entity.character;

import Game.entity.projectile.Projectile;
import Game.graphics.Sprite;
import Game.level.Level;

public class Foe extends Charact{
	private boolean visible = false;
	
	///sprite animations of moving
	private Sprite[] player_animation = null;
	private int spriteRows;
	private int spriteColumns;
	
	private Projectile projectile = null;
	
	
	public Foe(Level level, int xCoord, int yCoord, int spriteColumns, int spriteRows) {///more functional constructor that allows the player to create his own sprites and then add them to the player_animation array
		this.x = xCoord * 48 ;
		this.y = yCoord * 48 ;
		this.level = level;
		
		this.player_animation = new Sprite[spriteRows * spriteColumns];
		this.spriteRows = spriteRows;
		this.spriteColumns = spriteColumns;
		
		this.health = 20;
		
	}
	
	///___________ Update and render
	public void update() {
		this.updateVisibility();
	}
	
	public void render() {
		if(this.visible) this.level.renderCharacter(this);
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
	
	
	
	///_____________________ private functionality
	private void updateVisibility() {
		if(this.x > this.level.getX() - 48 && this.x < this.level.getX() + this.level.getCanvasWidth() 
		&& this.y > this.level.getY() - 48 && this.y < this.level.getY() + this.level.getCanvasHeight()) {
			this.visible = true;
		}else{ this.visible = false;}
	}
	
	
}
