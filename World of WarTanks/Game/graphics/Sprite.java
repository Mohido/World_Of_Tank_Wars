package Game.graphics;

public class Sprite {
	private int width, height;
	private int bgBorderWidth;
	private SpriteSheet sheet;
	private int[] pixels;
	
	
	///Given Level Sprites
	public static final Sprite flower_grass1 = new Sprite(SpriteSheet.TILES_SHEET, 0, 0, 16, 16);
	public static final Sprite flower_grass2 = new Sprite(SpriteSheet.TILES_SHEET, 1, 0, 16, 16);
	public static final Sprite grass =  new Sprite(SpriteSheet.TILES_SHEET, 2, 0, 16, 16);
	
	public static final Sprite wall1 = new Sprite(SpriteSheet.TILES_SHEET, 0, 1, 16, 16);
	public static final Sprite wall2 = new Sprite(SpriteSheet.TILES_SHEET, 1, 1, 16, 16);

	public static final Sprite mud1 = new Sprite(SpriteSheet.TILES_SHEET, 0, 2, 16, 16);
	public static final Sprite mud2 = new Sprite(SpriteSheet.TILES_SHEET, 1, 2, 16, 16);
	public static final Sprite water =  new Sprite(SpriteSheet.TILES_SHEET, 2, 2, 16, 16);
	
	
	//_________ Player's Sprites!
	public static final Sprite player_forward1 = new Sprite(SpriteSheet.TANK_SHEET, 0, 0 , 16, 16, 1);
	public static final Sprite player_forward2 = new Sprite(SpriteSheet.TANK_SHEET, 1, 0 , 16, 16, 1);
	public static final Sprite player_forward3 = new Sprite(SpriteSheet.TANK_SHEET, 2, 0 , 16, 16, 1);
	
	public static final Sprite player_right1 = new Sprite(SpriteSheet.TANK_SHEET, 0, 1 , 16, 16, 1);
	public static final Sprite player_right2 = new Sprite(SpriteSheet.TANK_SHEET, 1, 1 , 16, 16, 1);
	public static final Sprite player_right3 = new Sprite(SpriteSheet.TANK_SHEET, 2, 1 , 16, 16, 1);
	
	public static final Sprite player_backward1 = new Sprite(SpriteSheet.TANK_SHEET, 0, 2 , 16, 16, 1);
	public static final Sprite player_backward2 = new Sprite(SpriteSheet.TANK_SHEET, 1, 2 , 16, 16, 1);
	public static final Sprite player_backward3 = new Sprite(SpriteSheet.TANK_SHEET, 2, 2 , 16, 16, 1);
	
	public static final Sprite player_left1 = new Sprite(SpriteSheet.TANK_SHEET, 0, 3 , 16, 16, 1);
	public static final Sprite player_left2 = new Sprite(SpriteSheet.TANK_SHEET, 1, 3 , 16, 16, 1);
	public static final Sprite player_left3 = new Sprite(SpriteSheet.TANK_SHEET, 2, 3 , 16, 16, 1);
//	public static final Sprite player_backward1 = new Sprite[3];
//	public static final Sprite player_right1 = new Sprite[3];
//	public static final Sprite player_left1 = new Sprite[3];
	
	///Coordinates in Pixel form
	public Sprite(SpriteSheet sheet , int xCoordinates , int yCoordinates, int width , int height) {
		this.sheet = sheet;
		this.width = width;
		this.height = height;
		this.bgBorderWidth = 0;
		this.pixels = new int[this.width * this.height];
		sheet.crop(xCoordinates * SpriteSheet.PIXELS_PER_TILE, yCoordinates * SpriteSheet.PIXELS_PER_TILE, width, height, pixels);		
	}
	public Sprite(SpriteSheet sheet , int xCoordinates , int yCoordinates, int width , int height, int bgBorderWidth) {
		this.sheet = sheet;
		this.width = width;
		this.height = height;
		this.bgBorderWidth = bgBorderWidth;
		this.pixels = new int[this.width * this.height];
		sheet.crop(xCoordinates * SpriteSheet.PIXELS_PER_TILE, yCoordinates * SpriteSheet.PIXELS_PER_TILE, width, height, pixels);		
	}
	public Sprite(int width, int height, int color) {
		this.width = width;
		this.height = height;
		this.bgBorderWidth = 0;
		this.pixels = new int [this.width * this.height];
		for(int i = 0 ; i < width*height; i++) pixels[i] = color;
	}
	
	
	public int[] getPixels() {return pixels;}
	public int getWidth () {return this.width;}
	public int getHeight () {return this.height;}
	public int getBorderWidth() { return this.bgBorderWidth;}
}
