package Game.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Game.level.Level;

public class MapCanvas extends Canvas{

	private static final long serialVersionUID = 1L;
	private int width, height, displayWidth, displayHeight, size;
	private int hx , hy; //determines the hero positions to be rendered on the map
	private BufferedImage map; ///map will store an image of the file so we can copy the pixels whenever we want to the pixels array
	private BufferedImage image; ///This is a buffer that the pixels will be attached to .. we will render this image later
	private int[] pixels;
	private Level level;
	
	public MapCanvas (String map_path, Level level, int displayWidth , int displayHeight) {
		this.level = level;
		this.displayHeight = displayHeight;
		this.displayWidth = displayWidth;
		try {
			this.map = ImageIO.read(new File(map_path));
			this.width = map.getWidth();
			this.height = map.getHeight();
			image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
			pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
			map.getRGB(0, 0, this.width, this.height, this.pixels, 0, this.width);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.size = this.width * this.height;
		this.setSize(displayWidth, displayHeight);
		this.setFocusable(false);
		this.setPreferredSize(new Dimension(displayWidth, displayHeight));
	}

	
///_______________ Game functionality
	public void update() {
		if( (level.getHeroX() + 24) / (16*3) < this.width 
				&& (level.getHeroY() + 24) / (16*3) < this.height
				&& (level.getHeroX() + 24) / (16*3) > 0 
				&& (level.getHeroY() + 24) / (16*3) > 0) {
			this.hx = (level.getHeroX() + 24) / (16*3);
			this.hy = (level.getHeroY() + 24) / (16*3);
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		this.clear(); /// will make the pixels equal to the map pixels
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(this.image, 0, 0, this.displayWidth, this.displayHeight, null);
		g.dispose();
		bs.show();
	}
	
	///Helpers functionality
	private void clear() {
		map.getRGB(0, 0, this.width, this.height, this.pixels, 0, this.width);
		this.pixels[ this.hx + this.hy * this.width] = 0xffffffff;
	}
	
	public int getWidth() {return this.displayWidth;}
	public int geHeight() {return this.displayHeight;}
}
