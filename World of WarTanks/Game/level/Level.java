package Game.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Game.Window;
import Game.graphics.GameCanvas;
import Game.graphics.Sprite;
import Game.graphics.SpriteSheet;
import Game.graphics.Tile;
import Game.inputs.Keyboard;

public class Level {
	private int xShift, yShift;
	private int width , height, size;
	private int[] map;
	private BufferedImage mapImage;
	GameCanvas canvas = null;
	
	//private Player hero;
	//private List<NPC> npc;
	
	public Level(String path) {
		this.xShift = 0;
		this.yShift = 0;
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
	}
	
/// ________ Game Loop
	private int anim= 0;
	public void update() {
		int x= 0, y = 0;
		if(Window.keyboard.pressedKeys[Keyboard.A]) {
			x++;
		}
		if(Window.keyboard.pressedKeys[Keyboard.D]) {
			x--;
		}
		if(Window.keyboard.pressedKeys[Keyboard.W] == true) {
			y++;
		}
		if(Window.keyboard.pressedKeys[Keyboard.S]) {
			y--;
		}
		
		shift(x , y);
		//anim++;
	}
	
	public void render() {
		renderTiles();
	}
	
	
/// ____ More functionality
	public void shift(int xPixels, int yPixels) {
		this.xShift += xPixels;
		this.yShift += yPixels;
		//System.out.println("shifter");
	}

	public void setCanvas(GameCanvas gameCanvas) {
		this.canvas = gameCanvas;
	}
	
	public void renderTiles() {
		for(int y = 0 - canvas.getHeight() / 16 ; y <= (canvas.getHeight() + 16) / 16 ; y++) {
			for(int x = 0 - canvas.getWidth() / 16; x <= canvas.getWidth() / 16; x++) {
				getTile(x + xShift/16, y + yShift / 16).renderTile(this, x * 16 - xShift % 16 , y * 16 - yShift % 16);
			}
		}
	}
	
	public Tile getTile(int x , int y) {
		if(x >= this.width || y >= this.height || x < 0 || y < 0) return Tile.water;
		if(map[x + y * this.width] == 0xffa4ff00 ) return Tile.flower_grass1;
		return Tile.water;
	}
	
	
	public void renderTile(int x , int y, Tile tile) {
		for(int yp = 0 ; yp < tile.getSprite().getHeight() ; yp++) {
			int ya = y + yp;
			for(int xp = 0 ; xp < tile.getSprite().getWidth() ; xp++) {
				int xa = x + xp;
				if(xa < 0) xa = 0;
				if(xa > canvas.getWidth() || xa < 0 || ya > canvas.getHeight() || ya < 0) break;
				canvas.pixels[ xa + ya * canvas.getWidth()] = tile.getSprite().getPixels()[xp + yp * tile.getSprite().getWidth()];
			}
		}
	}
	
}
