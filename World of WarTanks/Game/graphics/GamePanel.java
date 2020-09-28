package Game.graphics;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import Game.entity.character.Player;
import Game.inputs.Keyboard;
import Game.level.Level;

public class GamePanel extends JPanel implements MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int width, height, size;
	public int MouseX, MouseY;
	private Level level;
	
	private Canvas canvas = null;
	
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
		this.setBorder(BorderFactory.createLineBorder(Color.gray));
		//this.setMaximumSize(new Dimension(width, height));
		this.setBounds(0, 0, width, height);	
	}
	
	
/// Main Updates and rendering functions----------------
	public void update() {
		if(this.canvas != null && this.canvas instanceof GameCanvas) {
			((GameCanvas)this.canvas).update();
		}
		if(this.canvas != null && this.canvas instanceof MapCanvas) {
			((MapCanvas)this.canvas).update();
		}
	}
	
	public void render() {
		if(canvas != null && this.canvas instanceof GameCanvas) {
			((GameCanvas)this.canvas).render();
		}
		if(canvas != null && this.canvas instanceof MapCanvas) {
			((MapCanvas)this.canvas).render();
		}
	}
	
/// More functionality:-
	public void createCanvasComponent() { ///Adding a full canvas upon the jpanel component
		this.canvas = new GameCanvas(this.width, this.height);
		this.canvas.addMouseListener(((GameCanvas)this.canvas));
		this.canvas.addMouseMotionListener(((GameCanvas)this.canvas));
		this.removeMouseListener(this);
		this.add(canvas);
		this.setLayout(new GridLayout());
	}
	
	public void createMapComponent(String path) {
		if(this.level == null) return;
		this.setFocusable(false);
		this.removeMouseListener(this);
		this.canvas = new MapCanvas(path, this.level, this.width, this.width);
		this.setLayout(new GridLayout(2,1)) ;
		this.add(this.canvas);
	}

	public void setLevel(Level level) {
		this.level = level;
		if(canvas != null && this.canvas instanceof GameCanvas) {
			((GameCanvas)this.canvas).setLevel(level);
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
		 //System.out.println("Mouse entered"); 
	 }
	  
	 @Override public void mouseExited(MouseEvent e) { 
		 //System.out.println("Mouse exited"); 
	 }


	public void setPlayer(Player player) {
		// TODO Auto-generated method stub
		this.level.setPlayer(player);
	}
	 
}
