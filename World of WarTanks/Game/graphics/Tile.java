package Game.graphics;

import Game.level.Level;

public class Tile {
	private Sprite sprite;
	private boolean collidable = false;
	private int width , height;
	
	public static final Tile flower_grass1 = new Tile(Sprite.flower_grass1, false);
	public static final Tile flower_grass2 = new Tile(Sprite.flower_grass2, false);
	public static final Tile grass =  new Tile(Sprite.grass, false);
	
	public static final Tile wall1 = new Tile(Sprite.wall1, true);
	public static final Tile wall2 = new Tile(Sprite.wall2, true);

	public static final Tile mud1 = new Tile(Sprite.mud1, false);
	public static final Tile mud2 = new Tile(Sprite.mud2, false);
	public static final Tile water =  new Tile(Sprite.water, false);
	
	public Tile(Sprite sprite , boolean collidable ){
		this.sprite = sprite;
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
		this.collidable = collidable;
	}
	
	public Sprite getSprite() {return this.sprite;}
	public boolean collidable() {return collidable;}
	
	
///we can add a zoomer...!
	public void renderTile(Level level, int xCoord, int yCoord) {
		level.renderTile(xCoord , yCoord, this);
	}
	
}
