package Game.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Game.inputs.Keyboard;

public class GamePanel extends JPanel implements MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int width, height, size;
	public int MouseX, MouseY;
	
	private GameCanvas game = null;
	
	public GamePanel(int width, int height){
		this.width = width;
		this.height = height;
		this.size = width * height;
		this.setSize(width, height);
		this.setMinimumSize(getSize());
		this.setMaximumSize(getSize());
		this.setPreferredSize(new Dimension(width, height));
		this.setVisible(true);
		this.setBackground(Color.DARK_GRAY);
		addMouseListener(this);
		this.setBorder(BorderFactory.createLineBorder(Color.white));
		this.setMaximumSize(new Dimension(width, height));
		this.setBounds(0, 0, width, height);
		
	}
	
	
/// Main Updates and rendering funcitons----------------
	public void update() {
	}
	
	public void render() {
		if(game != null) {
			game.render();
		}
	}
	
/// More functionality:-
	public void createCanvasComponent() { ///Ading a full canvas upon the jpanel component
		this.game = new GameCanvas(this.width , this.height);
		this.game.addMouseListener(this);
		this.removeMouseListener(this);
		this.add(game);
		this.setLayout(new GridLayout());
	}
	
	public void render( int xoffset , int yoffset) {
		if(game != null) {
			game.render(xoffset, yoffset);
		}
	}
	
////________________ mouse functionality_________________
	@Override
	public void paintComponent(Graphics g) { ///painting the image into the JPanel (Cant use buffered strategy)
		super.paintComponent(g);
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
		 System.out.println("Mouse entered"); 
	 }
	  
	 @Override public void mouseExited(MouseEvent e) { 
		 System.out.println("Mouse exited"); 
	 }
	 
}
