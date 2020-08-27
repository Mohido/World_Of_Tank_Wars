package Game;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import Game.entity.character.Player;
import Game.graphics.GamePanel;
import Game.graphics.Sprite;
import Game.inputs.Keyboard;
import Game.level.Level;

public class Window implements Runnable{
	
	private static int WIDTH = 720;
	private static int HEIGHT = (WIDTH * 9) / 16;
	private int ups, fps;
	private boolean gameLoop = true;
	
	private static JFrame window;
	private Thread gameThread;
	public final static Keyboard keyboard = new Keyboard();

	private List<GamePanel> components = new ArrayList<GamePanel>(); ///Contains all the components

	///Default window with 16:9 ratio. And default width is 720
	Window() {
		gameThread = new Thread(this);
		//keyboard = new Keyboard();
		window = new JFrame();
		window.setVisible(true);
		window.setSize(WIDTH, HEIGHT);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("World of War-Tanks");	

		window.addKeyListener(keyboard);
	}
	
	//Wiindow with costum width and height
	Window(int width , int height) {
		WIDTH = width;
		HEIGHT = height;
		
		gameThread = new Thread(this);
		//keyboard = new Keyboard();
		window = new JFrame();
		
		window.setVisible(true);
		window.setSize(WIDTH, HEIGHT);
		window.setLocationRelativeTo(null);
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
	int x_shifter = 100 , y_shifter = 100;

	private void update() {
		if(this.keyboard.pressedKeys[Keyboard.A]) {
			x_shifter++;
		}
		if(this.keyboard.pressedKeys[Keyboard.D]) {
			x_shifter--;
		}
		if(this.keyboard.pressedKeys[Keyboard.W] == true) {
			y_shifter++;
		}
		if(this.keyboard.pressedKeys[Keyboard.S]) {
			y_shifter--;
		}
		
		
		components.get(0).update();
	}

	private void render() {
//		for(int i = 0 ; i < components.size() ; i++) {
//			components.get(i).render();
//		}
		components.get(0).render();
	}

///_________________ Game Thread functionality
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
				this.update();
			}
			this.render();
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

///____________________________ MAIN
	public static void main(String[] args) {
		int game_width = 500;
		Level level = new Level("../res/Sample_Level.png");
		
		
		Window w = new Window();
		GamePanel game = new GamePanel(game_width , HEIGHT);
		GamePanel ui = new GamePanel(WIDTH - game_width , HEIGHT);
		
		game.createCanvasComponent();
		game.setLevel(level);
		game.setPlayer(new Player(level, Sprite.player_forward1, 10 ,15));
		
		w.addComponent(game, BorderLayout.WEST);
		w.addComponent(ui, BorderLayout.EAST);
		
		w.packComponent();
		
		w.start();
	}
}
