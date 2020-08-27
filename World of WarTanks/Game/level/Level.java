package Game.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Game.Window;
import Game.entity.Entity;
import Game.entity.character.Charact;
import Game.entity.character.Player;
import Game.graphics.GameCanvas;
import Game.graphics.Sprite;
import Game.graphics.Tile;
import Game.inputs.Keyboard;

public class Level {
	private int xShift, yShift;
	private int width , height, size;
	private int[] map;
	private BufferedImage mapImage;
	GameCanvas canvas = null;
	
	private Player hero;
	//private List<NPC> npc;
	
	public Level(String path) {
		try {
			mapImage = ImageIO.read(new File(path));
			this.width = mapImage.getWidth();
			this.height = mapImage.getHeight();
			this.size = this.width * this.height;
			map = new int [this.size];
			mapImage.getRGB(0, 0, this.width, this.height, map, 0, this.width);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.xShift = 0;
		this.yShift = 0;
		this.hero = new Player(this, Sprite.player_forward1, 10 ,15);
	}
	
/// ________ Game Loop
	private int anim= 0;
	public void update() {
//		int x= 0, y = 0;
//		if(Window.keyboard.pressedKeys[Keyboard.A]) {
//			x++;
//		}
//		if(Window.keyboard.pressedKeys[Keyboard.D]) {
//			x--;
//		}
//		if(Window.keyboard.pressedKeys[Keyboard.W] == true) {
//			y++;
//		}
//		if(Window.keyboard.pressedKeys[Keyboard.S]) {
//			y--;
//		}
//		
//		shift(x , y);
		hero.update();
		//anim++;
	}
	
	public void render() {
		renderTiles();
		hero.render();
	}
	
	
/// ____ rendering Tiles functionality
	
	public void renderTiles() {
		for(int y = 0 - canvas.getHeight() / 48 ; y <= (canvas.getHeight() + 48) / 48 ; y++) {
			for(int x = 0 - canvas.getWidth() / 48; x <= (canvas.getWidth() + 48) / 48; x++) {
				getTile(x + xShift/48, y + yShift / 48).renderTile(this, x * 48 - xShift % 48 , y * 48 - yShift % 48);
			}
		}
	}
	
	public Tile getTile(int x , int y) {
		if(x >= this.width || y >= this.height || x < 0 || y < 0) return Tile.water;
		if(map[x + y * this.width] == 0xffa4ff00) return Tile.flower_grass1;
		if(map[x + y * this.width] == 0xff3b612f) return Tile.flower_grass2;
		if(map[x + y * this.width] == 0xffd1ff00) return Tile.wall1;
		if(map[x + y * this.width] == 0xff00083e) return Tile.wall2;
		if(map[x + y * this.width] == 0xff06ff00) return Tile.grass;
		if(map[x + y * this.width] == 0xff1bd8c6) return Tile.water;
		if(map[x + y * this.width] == 0xff76683d) return Tile.mud1;
		if(map[x + y * this.width] == 0xff430505) return Tile.mud2;
	
		//if(map[x + y * this.width] == 0xffa4ff00) return Tile.flower_grass1;
		return Tile.water;
	}
	
	
	public void renderTile(int x , int y, Tile tile) {
		for(int yp = 0 ; yp < tile.getSprite().getHeight() * 3  ; yp++) {
			int ya = y + yp;
			for(int xp = 0 ; xp < tile.getSprite().getWidth() * 3 ; xp++) {
				int xa = x + xp;
				if(xa < 0) xa = 0;
				if(xa > canvas.getWidth() || xa < 0 || ya > canvas.getHeight() || ya < 0) break;
				canvas.pixels[ xa + ya * canvas.getWidth()] = tile.getSprite().getPixels()[xp/3 + yp/3 * tile.getSprite().getWidth()];
			}
		}
	}

///_________ character rendering
	public void renderCharacter(Charact character ) {
		Sprite temp = character.getSprite();
		
		for(int y = 0; y < temp.getHeight() * 3; y++) {
			int ya = character.getY() + y - yShift;
			for(int x = 0 ; x < temp.getWidth() * 3 ; x++) {
				int xa = character.getX() + x - xShift;
				if(xa > canvas.getWidth() || xa < 0 || ya > canvas.getHeight() || ya < 0) continue;
				if(temp.getPixels()[x/3+y/3*temp.getWidth()] == 0xffff00ff) continue;
				else canvas.pixels[xa + ya * canvas.getWidth()] = temp.getPixels()[ (x/3) + (y/3) * temp.getWidth()];
			}
		}
	}
	
	

/// ____ Positioning functionality
	public void shift(int xPixels, int yPixels) {
		this.xShift += xPixels;
		this.yShift += yPixels;
		//System.out.println("shifter");
	}
	public boolean checkCollision(int x , int y, Entity e) {
		return false;
	}
	
	
///Engine functionality___ once we set the canvas we make it centralised so the Player sets in the middle of the player
	public void setCanvas(GameCanvas gameCanvas) {
		this.canvas = gameCanvas;
		shift(-(gameCanvas.getWidth()/2 - 48/2) , -(gameCanvas.getHeight()/2 - 48/2));
	}

	
}
