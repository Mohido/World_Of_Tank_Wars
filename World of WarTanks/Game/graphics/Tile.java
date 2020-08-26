package Game.graphics;

public class Tile {
	private Sprite sprite;
	private boolean collidable = false;
	private int width , height;
	
	public static final Tile flower_grass1 = new Tile(new Sprite(SpriteSheet.TILES_SHEET, 0, 0, 16, 16), false);
	public static final Tile flower_grass2 = new Tile(new Sprite(SpriteSheet.TILES_SHEET, 1, 0, 16, 16), false);
	public static final Tile grass =  new Tile(new Sprite(SpriteSheet.TILES_SHEET, 2, 0, 16, 16), false);
	
	public static final Tile wall1 = new Tile(new Sprite(SpriteSheet.TILES_SHEET, 0, 1, 16, 16), false);
	public static final Tile wall2 = new Tile(new Sprite(SpriteSheet.TILES_SHEET, 1, 1, 16, 16), false);

	public static final Tile mud1 = new Tile(new Sprite(SpriteSheet.TILES_SHEET, 0, 2, 16, 16), false);
	public static final Tile mud2 = new Tile(new Sprite(SpriteSheet.TILES_SHEET, 1, 2, 16, 16), false);
	public static final Tile water =  new Tile(new Sprite(SpriteSheet.TILES_SHEET, 3, 2, 16, 16), false);
	
	public Tile(Sprite sprite , boolean collidable ){
		this.sprite = sprite;
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
		this.collidable = collidable;
	}
	
	public Sprite getSprite() {return this.sprite;}
	public boolean collidable() {return collidable;}
	
	public void renderTile() {
		
	}
	
}
