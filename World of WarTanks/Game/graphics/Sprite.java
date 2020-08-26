package Game.graphics;

public class Sprite {
	private int width, height;
	private SpriteSheet sheet;
	private int[] pixels;
	
	///Coordinates in Pixel form
	public Sprite(SpriteSheet sheet , int xCoordinates , int yCoordinates, int width , int height) {
		this.sheet = sheet;
		this.width = width;
		this.height = height;
		this.pixels = new int[this.width * this.height];
		sheet.crop(xCoordinates * SpriteSheet.PIXELS_PER_TILE, yCoordinates * SpriteSheet.PIXELS_PER_TILE, width, height, pixels);
	}
	
	public int[] getPixels() {return pixels;}
	public int getWidth () {return this.width;}
	public int getHeight () {return this.height;}
}
