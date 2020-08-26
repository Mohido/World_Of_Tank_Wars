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
	
///pixel coordinates
	public void crop(int xCoordinates, int yCoordinates , int width, int height , int[] pixels) {
		System.out.println(xCoordinates + ", " + yCoordinates);
		for(int y = 0; y < height ; y++) {
			int ny = y + yCoordinates;
			for(int x = 0 ; x < width ; x++) {
				int nx = x + xCoordinates;
				pixels[x + y * width] = this.pixels[nx + ny * this.width];
			}
		}
	}
	
	public int getWidth() {return this.width;}
	public int getHeight() {return this.height;}
	
}
