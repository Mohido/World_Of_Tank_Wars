package Game.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import Game.level.Level;

public class GameCanvas extends Canvas implements MouseListener, MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width, height, size;
	private BufferedImage image;
	public int[] pixels;
	private Level level = null; /// the level to render!
	private String player_HP = null;
	
	public GameCanvas (int width , int height) {
		this.height = height;
		this.width = width;
		this.size = height*width;
		this.setFocusable(false);
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width,height));
		image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	}

	
///_______________ Game functionality
	public void update() {
		if(level != null) {
			level.update();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}		
		this.clear();
		
		if(level != null) {
			level.render();
			//System.out.println("rendering the level");
		}
		
		Graphics g = bs.getDrawGraphics();
		
		//g.fillRect(0, 0, this.width, this.height);
		g.drawImage(this.image, 0, 0, this.width, this.height, null);
		if(this.player_HP != null) {
			g.setColor(Color.white);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
			g.drawString(this.player_HP, 20, 20);
		}
		g.dispose();
		bs.show();
	}
	
	
	///Helpers functionality
	private void clear() {
		for(int i = 0 ; i< this.size ; i++) pixels[i] = 0;
	}
	
	public void setLevel(Level level) {
		this.level = level;
		level.setCanvas(this);
	}
	
	public int getWidth() {return this.width;}
	public int geHeight() {return this.height;}


	
	
	
	
///Mouse functional events
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//this.level.mousePressed(e.getX() , e.getY(), e.getButton());
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		this.level.mousePressed(e.getX() , e.getY(), e.getButton());
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		this.level.mouseReleased();
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		this.level.mousePressed(e.getX() , e.getY(), e.getButton());
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		//this.level.mousePressed(e.getX() , e.getY(), e.getButton());
	}

	public void setPlayerHP(String s) { this.player_HP = s;}
}
