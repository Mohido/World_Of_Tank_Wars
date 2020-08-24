package Game;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import Game.graphics.GamePanel;

public class Window implements Runnable{
	
	private static int WIDTH = 720;
	private static int HEIGHT = (WIDTH * 9) / 16;
	private int ups, fps;
	
	private static JFrame window;
	private Thread gameThread = new Thread(this);
	private boolean gameLoop = true;
	
	private List<GamePanel> components = new ArrayList<GamePanel>();
	
	
	///Default window with 16:9 ratio. And default width is 720
	Window() {
		window = new JFrame();
		window.setVisible(true);
		window.setSize(WIDTH, HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("World of War-Tanks");	
	}
	
	//Wiindow with costum width and height
	Window(int width , int height) {
		WIDTH = width;
		HEIGHT = height;
		
		window = new JFrame();
		window.setVisible(true);
		window.setSize(WIDTH, HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("World of War-Tanks");
	}
	
	public void addComponent(GamePanel component, Object constraints) {
		components.add(component);
		window.add(component, constraints); //
	}
	
	///Helper functionality (API Functionality)
	public void packComponent() {
		window.pack();
	}
	private void start() {
		gameThread.start();
	}

	
	///____________________ GAME FUNCTIONALITY__________________
	private void update() {
	}
	
	private void render() {
	}
	
	
	@Override
	public void run() {
		long currentTime = System.nanoTime(); /// for calculating the time differences
		long currentTime2 = System.currentTimeMillis(); /// To know when the time passes a second
		long updates_per_second = 1000000000 / 60; /// Amount of updates ... nanoseconds per frame
		
		int updates = 0;
		int frames = 0;
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
		int game_width = 500;
		Window w = new Window();
		
		GamePanel game = new GamePanel(game_width , HEIGHT, 0xff00ff00);
		GamePanel ui = new GamePanel(WIDTH - game_width , HEIGHT, 0xffffff00);
		
		w.addComponent(game, BorderLayout.WEST);
		w.addComponent(ui, BorderLayout.EAST);
		
		w.packComponent();
		
		w.start();
		
	}
}
