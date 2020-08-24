package Game;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import Game.graphics.GamePanel;

public class Window implements Runnable{
	

	private static int WIDTH = 720;
	private static int HEIGHT = (WIDTH * 9) / 14;
	private int ups, fps;
	
	private static JFrame window;
	private Thread gameThread = new Thread(this);
	private GamePanel game;
	private GamePanel ui;
	private boolean gameLoop = true;
	
	
	Window() {
		window = new JFrame();
		window.setVisible(true);
		window.setSize(WIDTH, HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("World of War-Tanks");
		game = new GamePanel(HEIGHT , HEIGHT, 0xff00ff00);
		ui = new GamePanel(WIDTH - HEIGHT , HEIGHT, 0xffffff00);
		
		window.add(game, BorderLayout.WEST);
		window.add(ui, BorderLayout.EAST);
		window.pack();
		this.start();
		//this.end();
	}
	
	private void start() {
		// TODO Auto-generated method stub
		gameThread.start();
	}

	private void update() {
		// TODO Auto-generated method stub
		
	}
	
	private void render() {
		//System.out.println(game.MouseX + ", " + game.MouseY);
		game.repaint();
	}
	
	
	@Override
	public void run() {
		long currentTime = System.nanoTime();
		long updates_per_second = 1000000000 / 60;
		
		int updates = 0;
		int frames = 0;
		
		long currentTime2 = System.currentTimeMillis();
		
		while(this.gameLoop) {
			
			long temp_time = System.nanoTime();
			if(temp_time - currentTime > updates_per_second) {
				currentTime = temp_time;
				updates++;
				update();
			}
			
			render();
			frames++;
			
			long temp = System.currentTimeMillis();
			if( temp - currentTime2 > 1000 ) {
				currentTime2 = temp;
				ups = updates;
				fps = frames;
				System.out.println("Ups: " + ups + ", Fps: " + fps);
				updates = 0;
				frames = 0;
			}
			
		}
	}
	

	public void end() {
		gameLoop = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void main(String[] args) {
		Window w = new Window();
		
		
	}
}
