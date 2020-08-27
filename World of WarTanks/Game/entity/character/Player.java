package Game.entity.character;

import Game.Window;
import Game.graphics.Sprite;
import Game.inputs.Keyboard;
import Game.level.Level;

public class Player extends Charact {
	
	
	///Coordinates Format, (Tile base). 
	public Player(Level level, Sprite sprite, int xCoord, int yCoord) {
		this.x = xCoord * 48 ;
		this.y = yCoord * 48 ;
		this.sprite = sprite;
		this.level = level;
		this.level.shift(this.x, this.y);
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
		this.move(0,ny);
		this.move(nx,0);
		//this.level.shift(nx, ny);
	}
	
	public void render() {
		this.level.renderCharacter(this);
	}
}
