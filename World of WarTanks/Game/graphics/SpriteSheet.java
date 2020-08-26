package Game.graphics;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	public static int PIXELS_PER_TILE = 16;
	
	
	private BufferedImage image;
	private int width, height; 
	private final int[] pixels;
	
	public static final SpriteSheet TILES_SHEET  = new SpriteSheet("../res/Game_Level_Basic_Sheet.png"); ///for the default game (after you r finished with the game edit it to suit the game engine)
	public static final SpriteSheet TANK_SHEET  = new SpriteSheet("../res/Tank_Sheet.png");
	
	public SpriteSheet(String path){
		int[] tempPixels = null;
		try {
			image = ImageIO.read(new File(path));
			this.width = image.getWidth();
			this.height = image.getHeight();
			tempPixels = new int[this.width * this.height];
			image.getRGB(0, 0, this.width, this.height, tempPixels, 0, this.width);
			
			System.out.println("A spritesheet loaded, it width: " + this.width);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.pixels = tempPixels;
	}
	
	public int[] getPixels() {
		return this.pixels;
	}
	
	public void crop(int xCoordinates, int yCoordinates , int width, int height , int[] pixels) {
		int nx = 0, ny = 0;
		for(int y = yCoordinates ; y < height ; y++) {
			for(int x = xCoordinates ; x < width ; x++) {
				pixels[nx + ny * width] = this.pixels[x + y * this.width];
				nx++;
			}
			nx = 0;
			ny++;
		}
	}
	
	public int getWidth() {return this.width;}
	public int getHeight() {return this.height;}
	
}
