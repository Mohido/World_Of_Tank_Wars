package Game.graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JPanel;

import Game.inputs.Keyboard;

public class GamePanel extends JPanel implements MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width, height, size;
	private BufferedImage image;
	public int[] pixels;
	private int bgcolor;
	
	public int MouseX, MouseY;
	
	public GamePanel(int width, int height, int bgcolor){
		this.width = width;
		this.height = height;
		this.size = width * height;
		this.setSize(width, height);
		this.bgcolor = bgcolor;
		
		image = new BufferedImage(this.width, this.height,   BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		this.setMinimumSize(getSize());
		this.setMaximumSize(getSize());
		this.setPreferredSize(new Dimension(width, height));
		this.setVisible(true);
		
		for(int i = 0 ; i < this.size ; i++) {
			pixels[i] = bgcolor;
		}
		
		requestFocus();
		addMouseListener(this);
		
		
		repaint();
	}
	
	
	public void update() {
		
	}
	
	public void render() {
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) { ///painting the image into the JPanel (Cant use buffered strategy)
		super.paintComponent(g);
		g.drawImage(image, 	0, 0, width, height, null);
		g.dispose();
	}

	
	
	
	
	  @Override public void mouseClicked(MouseEvent e) { 
		  MouseX = e.getX(); 
		  MouseY = e.getY(); //System.out.println("Mouse Pressed"); 
	  }
	  
	  @Override public void mousePressed(MouseEvent e) {
		  MouseX = e.getX(); 
		  MouseY = e.getY();
	  }
	  
	  @Override public void mouseReleased(MouseEvent e) { MouseX = -1; MouseY = -1;
	  // TODO Auto-generated method stub
	  MouseX = -1; 
	  MouseY = -1;
	  
	  }
	  
	 @Override public void mouseEntered(MouseEvent e) { 
		 // TODO Auto-generated
		 //System.out.println("Mouse entered"); 
	 }
	  
	 @Override public void mouseExited(MouseEvent e) { 
		 //System.out.println("Mouse exited"); 
	 }
	 
}
