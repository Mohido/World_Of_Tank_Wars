package Game.graphics;

public class Sprite {
	private int width, height;
	private SpriteSheet sheet;
	private int[] pixels;
	
	
	public static final Sprite flower_grass1 = new Sprite(SpriteSheet.TILES_SHEET, 0, 0, 16, 16);
	public static final Sprite flower_grass2 = new Sprite(SpriteSheet.TILES_SHEET, 1, 0, 16, 16);
	public static final Sprite grass =  new Sprite(SpriteSheet.TILES_SHEET, 2, 0, 16, 16);
	
	public static final Sprite wall1 = new Sprite(SpriteSheet.TILES_SHEET, 0, 1, 16, 16);
	public static final Sprite wall2 = new Sprite(SpriteSheet.TILES_SHEET, 1, 1, 16, 16);

	public static final Sprite mud1 = new Sprite(SpriteSheet.TILES_SHEET, 0, 2, 16, 16);
	public static final Sprite mud2 = new Sprite(SpriteSheet.TILES_SHEET, 1, 2, 16, 16);
	public static final Sprite water =  new Sprite(SpriteSheet.TILES_SHEET, 2, 2, 16, 16);
	
	
	
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
