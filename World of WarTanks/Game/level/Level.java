package Game.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Game.graphics.GameCanvas;
import Game.graphics.Tile;

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
	public void update() {}
	
	public void render() {
		renderTiles();
	}
	
	
/// ____ More functionality
	public void shift(int xPixels, int yPixels) {
		this.xShift += xPixels;
		this.yShift += yPixels;
	}

	public void setCanvas(GameCanvas gameCanvas) {
		this.canvas = gameCanvas;
	}
	
	public void renderTiles() {
		for(int y = 0 ; y < canvas.geHeight() ; y++) {
			int yp = y + yShift;
			for(int x = 0 ; x < canvas.getWidth() ; x++) {
				int xp = x + xShift;
				//canvas.pixels[x + y * canvas.getWidth()] = this.getTile(xp , yp).getSprite().getPixels()[];
			}
		}
	}
	
	public Tile getTile(int x , int y) {
		if(map[x + y * this.width] == 0xff69ff00 ) return Tile.flower_grass1;
		return Tile.water;
	}
	
	
}
