package Game.entity.character;

import Game.Window;
import Game.graphics.Sprite;
import Game.inputs.Keyboard;
import Game.level.Level;

public class Player extends Charact {
	private int anim = 0;
	private int index = 0;
	private Sprite[] player_forward = new Sprite[3];
	private Sprite[] player_backward = new Sprite[3];
	private Sprite[] player_right = new Sprite[3];
	private Sprite[] player_left = new Sprite[3];
	
	///Coordinates Format, (Tile base). 
	public Player(Level level, Sprite sprite, int xCoord, int yCoord) {
		this.x = xCoord * 48 ;
		this.y = yCoord * 48 ;
		this.sprite = sprite;
		this.level = level;
		this.level.shift(this.x, this.y);
		
		player_forward[2]= Sprite.player_forward1;
		player_forward[1]= Sprite.player_forward2;
		player_forward[0]= Sprite.player_forward3;
		
		player_right[2] = Sprite.player_right1;
		player_right[1] = Sprite.player_right2;
		player_right[0] = Sprite.player_right3;
		
		player_left[2] = Sprite.player_left1;
		player_left[1] = Sprite.player_left2;
		player_left[0] = Sprite.player_left3;
		
		player_backward[2] = Sprite.player_backward1;
		player_backward[1] = Sprite.player_backward2;
		player_backward[0] = Sprite.player_backward3;
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
		
		
		anim++;
	}
	
	public void render() {
		this.level.renderCharacter(this);
	}
	
	
///Update movement animation functionality
	private void updateAnimation(){
		if( dir == Direction.N) {
			if(anim % 10 == 0) {
				this.sprite = player_forward[index % 3];
				index++;
			}
		}
		if( dir == Direction.S) {
			if(anim % 10 == 0) {
				this.sprite = player_backward[index % 3];
				index++;
			}
		}
		if( dir == Direction.E) {
			if(anim % 10 == 0) {
				this.sprite = player_right[index % 3];
				index++;
			}
		}
		if( dir == Direction.W) {
			if(anim % 10 == 0) {
				this.sprite = player_left[index % 3];
				index++;
			}
		}
	}
}
