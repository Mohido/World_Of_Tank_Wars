package Game.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class GameCanvas extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width, height, size;
	private BufferedImage image;
	public int[] pixels;
	
	public GameCanvas (int width , int height) {
		this.height = height;
		this.width = width;
		this.size = height*width;
		this.setFocusable(false);
		this.setSize(width, height);
		image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	}
	
	
	
	public void clear() {
		for(int i = 0 ; i< this.size ; i++) pixels[i] = 0;
	}
	
	
	public void update() {
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, this.width, this.height);
		//g.drawImage(this.image, 0, 0, this.width, this.height, null);
		
		g.dispose();
		bs.show();
		
//		for(int y = 0 ; y < sheet.getHeight() ; y++) {
//			//int ya = yoffset + y;
//			for(int x = 0; x < sheet.getWidth() ; x++) {
//				//int xa = x + xoffset;
//				pixels[xa + ya * this.width] = sheet.getPixels()[ (x ) + (y ) * sheet.getWidth()];
//			}
//		}
	}
	
	
	
	
	public void render(int xoffset , int yoffset) {
		
		this.clear();
		
		
		/*for(int y = 0 ; y < SpriteSheet.TILES_SHEET.getHeight() * 3 ; y++) {
			int ya = yoffset + y;
			for(int x = 0; x < SpriteSheet.TILES_SHEET.getWidth() * 3 ; x++) {
				int xa = x + xoffset;
			pixels[xa + ya * this.width] = SpriteSheet.TILES_SHEET.getPixels()[  (x/3) + (y/3) * SpriteSheet.TILES_SHEET.getWidth()];
			}
		}*/
		
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.drawImage(this.image, 0, 0, this.width, this.height, null);
		
		g.dispose();
		bs.show();
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
