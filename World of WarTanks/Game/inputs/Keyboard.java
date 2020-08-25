package Game.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	public static boolean[] pressedKeys = new boolean[256];
	
	public static final int LEFT = 37, UP = 38, RIGHT = 39, DOWN = 40;
	public static final int A = 65, W = 87, D = 68, S = 83;
	
	
	public Keyboard() {
		for(int i = 0 ; i < pressedKeys.length ; i++) {pressedKeys[i] = false;}
	}
	///Key Listener functionality
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("key: " + e.getKeyCode() + " pressed");
				pressedKeys[e.getKeyCode()] = true;
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("key: " + e.getKeyCode() + " released");
				pressedKeys[e.getKeyCode()] = false;
			}
}
