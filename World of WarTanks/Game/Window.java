package Game;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import Game.entity.character.Foe;
import Game.entity.character.Player;
import Game.entity.projectile.Projectile;
import Game.graphics.GamePanel;
import Game.graphics.Sprite;
import Game.graphics.SpriteSheet;
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
	private void update() {
		for(int i = 0 ; i < components.size() ; i++) {
			components.get(i).update();
		}
	}

	private void render() {
		for(int i = 0 ; i < components.size() ; i++) {
			components.get(i).render();
		}
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
				this.render();
			}
			
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
		Window w = new Window();
		
		int game_width = 500;
		String map = "../res/Sample_Level.png";
		Level level = new Level(map);
		Projectile normalProj = new Projectile(100, 5, 15, 2,
				   new Sprite(new SpriteSheet("../res/Pojectiles_Sheet.png"), 0, 0, 16, 16, 3),
				   level);
		
		
		GamePanel game = new GamePanel(game_width , HEIGHT);
		GamePanel ui = new GamePanel(WIDTH - game_width , HEIGHT);
		
		game.createCanvasComponent();
		game.setLevel(level);
		
		ui.setLevel(level);
		ui.createMapComponent(map);
		
		///__________ Player Creation
		Player player = new Player(level, 7 ,4, 3, 4, 1, 100);
		Sprite[] forward = new Sprite[3];
		Sprite[] backward = new Sprite[3];
		Sprite[] right = new Sprite[3];
		Sprite[] left = new Sprite[3];
		int count = 0;
		for(int i = 2 ; i >= 0 ; i--) {
			forward[i] = new Sprite(SpriteSheet.TANK_SHEET, count, 0, 16, 16, 1);
			backward[i] = new Sprite(SpriteSheet.TANK_SHEET, count, 2, 16, 16, 1);
			right[i] = new Sprite(SpriteSheet.TANK_SHEET, count, 1, 16, 16, 1);
			left[i] = new Sprite(SpriteSheet.TANK_SHEET, count, 3, 16, 16, 1);
			count++;
		}
		///When setting a sprite: Make sure to make it in the following seequence: UP -> DOWN -> RIGHT -> LEFT
		player.addSpriteRow(forward);
		player.addSpriteRow(backward);
		player.addSpriteRow(right);
		player.addSpriteRow(left);
		player.setProjectile( normalProj );
		///____________ Foe creation
		Foe dumb = new Foe(level, 8 ,9, 3, 4, 0.5, 20, normalProj );
		dumb.setHeroSearchingRadius(200);
		count = 0;
		for(int i = 0 ; i < 3 ; i++) {
			forward[i] = new Sprite(SpriteSheet.TANK_SHEET_2, count, 0, 16, 16, 2);
			backward[i] = new Sprite(SpriteSheet.TANK_SHEET_2, count, 2, 16, 16, 2);
			right[i] = new Sprite(SpriteSheet.TANK_SHEET_2, count, 1, 16, 16, 2);
			left[i] = new Sprite(SpriteSheet.TANK_SHEET_2, count, 3, 16, 16, 2);
			count++;
		}
		dumb.addSpriteRow(forward);
		dumb.addSpriteRow(backward);
		dumb.addSpriteRow(right);
		dumb.addSpriteRow(left);
		
		
		level.addFoe(dumb);
		level.setPlayer(player);
		
		w.addComponent(game, BorderLayout.WEST);
		w.addComponent(ui, BorderLayout.EAST);
		
		w.packComponent();
		
		w.start();
	}
}
