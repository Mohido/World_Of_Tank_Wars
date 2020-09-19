package Game.level;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import Game.Window;
import Game.dataStructures.Node;
import Game.entity.Entity;
import Game.entity.character.Charact;
import Game.entity.character.Charact.Direction;
import Game.entity.character.Foe;
import Game.entity.character.Player;
import Game.entity.explosion.Explosion;
import Game.entity.particles.Particle;
import Game.entity.projectile.Projectile;
import Game.graphics.GameCanvas;
import Game.graphics.Sprite;
import Game.graphics.Tile;
import Game.inputs.Keyboard;
import Game.inputs.Mouse;

public class Level {
	private int xShift, yShift;
	private int width , height, size;
	

	private int[] map;
	private BufferedImage mapImage;
	GameCanvas canvas = null;
	
	private Player hero;
	private List<Particle> particles = new ArrayList<Particle>();
	private List<Explosion> explosions = new ArrayList<Explosion>();
	private List<Foe> foes = new ArrayList<Foe>();
	
	public Level(String path) {
		try {
			mapImage = ImageIO.read(new File(path));
			this.width = mapImage.getWidth();
			this.height = mapImage.getHeight();
			this.size = this.width * this.height;
			map = new int [this.size];
			mapImage.getRGB(0, 0, this.width, this.height, map, 0, this.width);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.xShift = 0;
		this.yShift = 0;
		//this.hero = new Player(this, Sprite.player_forward1, 10 ,15);
	}
	
/// ________ Game Loop
	public void update() {
		hero.update();
		
		for(int i = 0 ; i < this.particles.size() ; i++) {
			if(this.particles.get(i).isRemoved()) this.particles.remove(i);
			else this.particles.get(i).update();
		}
		
		for(int i = 0 ; i < this.explosions.size() ; i++) {
			if(this.explosions.get(i).isRemoved()) this.explosions.remove(i);
			else this.explosions.get(i).update();
		} 
		
		for(int i = 0 ; i < this.foes.size() ; i++) {
			if(this.foes.get(i).isRemoved()) this.foes.remove(i);
			else this.foes.get(i).update();
		} 
	}
	
	public void render() {
		renderTiles();
		hero.render();
		

		for(int i = 0 ; i < this.particles.size(); i++) this.particles.get(i).render();
		for(int i = 0 ; i < this.explosions.size(); i++) this.explosions.get(i).render();
		for(int i = 0 ; i < this.foes.size() ; i++) {if(this.foes.get(i).isVisible()) this.foes.get(i).render();} 
	}
	
	
/// ____ rendering Tiles functionality
	
	private void renderTiles() {
		for(int y = 0 - canvas.getHeight() / 48 ; y <= (canvas.getHeight() + 48) / 48 ; y++) {
			for(int x = 0 - canvas.getWidth() / 48; x <= (canvas.getWidth() + 48) / 48; x++) {
				getTile(x + xShift/48, y + yShift / 48).renderTile(this, x * 48 - xShift % 48 , y * 48 - yShift % 48);
			}
		}
	}
	
	
	public void renderTile(int x , int y, Tile tile) {
		for(int yp = 0 ; yp < tile.getSprite().getHeight() * 3  ; yp++) {
			int ya = y + yp;
			for(int xp = 0 ; xp < tile.getSprite().getWidth() * 3 ; xp++) {
				int xa = x + xp;
				if(xa < 0) xa = 0;
				if(xa > canvas.getWidth() || xa < 0 || ya > canvas.getHeight() || ya < 0) break;
				canvas.pixels[ xa + ya * canvas.getWidth()] = tile.getSprite().getPixels()[xp/3 + yp/3 * tile.getSprite().getWidth()];
			}
		}
	}

///_________ character rendering
	public void renderCharacter(Charact character ) {
		Sprite temp = character.getSprite();
		
		
		///Simply: 
		for(int y = 0; y < temp.getHeight() * 3; y++) {
			int ya = character.getY() + y - yShift;
			for(int x = 0 ; x < temp.getWidth() * 3 ; x++) {
				int xa = character.getX() + x - xShift;
				if(xa > canvas.getWidth() || xa < 0 || ya > canvas.getHeight() || ya < 0) continue;
				if(temp.getPixels()[x/3+y/3*temp.getWidth()] == 0xffff00ff) continue;
				else canvas.pixels[xa + ya * canvas.getWidth()] = temp.getPixels()[ (x/3) + (y/3) * temp.getWidth()];
			}
		}
	}
	
	

/// ____ Positioning functionality
	public void shift(int xPixels, int yPixels) {
		this.xShift += xPixels;
		this.yShift += yPixels;
		//System.out.println("shifter");
	}
	public boolean checkCollision(int x , int y, Entity e) {
		
		if(getTile( (x + e.getSprite().getBorderWidth() * 3)/48 , (y+ e.getSprite().getBorderWidth() * 3)/48).solid()) ///checking up and left
			return true;

		if(getTile((x + e.getSprite().getWidth() * 3 - e.getSprite().getBorderWidth() * 3)/48 , (y + e.getSprite().getHeight() * 3 - e.getSprite().getBorderWidth() * 3)/48).solid()) //checking down and right 
			return true;
		
		if(getTile((x + e.getSprite().getBorderWidth() * 3)/48 , (y + e.getSprite().getHeight() * 3 -e.getSprite().getBorderWidth()*3)/48).solid()) /// checking down and left 
			return true;
		
		if(getTile((x + e.getSprite().getWidth() * 3 - e.getSprite().getBorderWidth() * 3)/48 , (y + e.getSprite().getBorderWidth() * 3)/48).solid())  /// checking up and right
			return true;
		
		return false;
	}
	public boolean checkCollisionNPC(int x, int y, Entity e) {
		boolean collidision = false;
		
		Sprite s;
		int fx , fy;
		
		
		for(int i = 0 ; i < this.foes.size() ; i++) {
			s = this.foes.get(i).getSprite();
			fx = this.foes.get(i).getX(); fy = this.foes.get(i).getY();
			if(!this.foes.get(i).isVisible() || 
					( Math.abs(x - fx) > 48 || Math.abs(y - fy) > 48 ) ) 
				continue;
			 
			if(e instanceof Foe)
				if(this.foes.get(i).getID() == ((Foe)e).getID()) continue;
			
			
			//top left collision
			if( fx + s.getBorderWidth() * 3 <= x + e.getSprite().getBorderWidth() * 3 && x + e.getSprite().getBorderWidth() * 3 <= fx + s.getWidth()*3 - s.getBorderWidth() * 3 
			&& fy + s.getBorderWidth() * 3 <= y + e.getSprite().getBorderWidth() * 3 && y + e.getSprite().getBorderWidth() * 3 <= fy + s.getHeight()*3 - s.getBorderWidth() * 3) collidision = true;
			//top right collision
			if( fx + s.getBorderWidth() * 3 <= x + e.getSprite().getWidth()*3 - e.getSprite().getBorderWidth() * 3 && x + e.getSprite().getWidth()*3 - e.getSprite().getBorderWidth() * 3 <= fx + s.getWidth()*3 - s.getBorderWidth() * 3 
					&& fy + s.getBorderWidth() * 3 <= y + e.getSprite().getBorderWidth() * 3 && y + e.getSprite().getBorderWidth() * 3 <= fy + s.getHeight()*3 - s.getBorderWidth() * 3) collidision = true; 
			//bottom left collision
			if( fx + s.getBorderWidth() * 3 <= x + e.getSprite().getBorderWidth() * 3 && x + e.getSprite().getBorderWidth() * 3 <= fx + s.getWidth()*3 - s.getBorderWidth() * 3 
					&& fy + s.getBorderWidth() * 3 <= y + e.getSprite().getHeight()*3 - e.getSprite().getBorderWidth()*3 && y + e.getSprite().getHeight()*3 - e.getSprite().getBorderWidth()*3 <= fy + s.getHeight()*3 - s.getBorderWidth() * 3) collidision = true; 
			//bottom right collision
			if( fx + s.getBorderWidth() * 3 <= x + e.getSprite().getWidth()*3 - e.getSprite().getBorderWidth() * 3 && x + e.getSprite().getWidth()*3 - e.getSprite().getBorderWidth() * 3 <= fx + s.getWidth()*3 - s.getBorderWidth() * 3 
					&& fy + s.getBorderWidth() * 3 <= y + e.getSprite().getHeight()*3 - e.getSprite().getBorderWidth()*3 && y + e.getSprite().getHeight()*3 - e.getSprite().getBorderWidth()*3 <= fy + s.getHeight()*3 - s.getBorderWidth() * 3) collidision = true;
					
			///__________________ checking the opposite ( the npc to the entity ) ____________
			//top left collision
			if( x + e.getSprite().getBorderWidth()*3 <= fx + s.getBorderWidth()*3
					&&  fx + s.getBorderWidth()*3 <=  x + e.getSprite().getWidth()*3 - e.getSprite().getBorderWidth()*3
			&&  y + e.getSprite().getBorderWidth()*3 <= fy + s.getBorderWidth()*3
					&& fy + s.getBorderWidth()*3 <= y + e.getSprite().getHeight()*3 - e.getSprite().getBorderWidth()*3) collidision = true;; 
			//top right collision
			if( x + e.getSprite().getBorderWidth()*3 <= fx + s.getWidth()*3 - s.getBorderWidth()*3
					&&  fx + s.getWidth()*3 - s.getBorderWidth()*3 <=  x + e.getSprite().getWidth()*3 - e.getSprite().getBorderWidth()*3
			&&  y + e.getSprite().getBorderWidth()*3 <= fy + s.getBorderWidth()*3
					&& fy + s.getBorderWidth()*3 <= y + e.getSprite().getHeight()*3 - e.getSprite().getBorderWidth()*3) collidision = true;
			//bottom left collision
			if( x + e.getSprite().getBorderWidth()*3 <= fx + s.getBorderWidth()*3
					&&  fx + s.getBorderWidth()*3 <=  x + e.getSprite().getWidth()*3 - e.getSprite().getBorderWidth()*3
			&&  y + e.getSprite().getBorderWidth()*3 <= fy + s.getHeight()*3 - s.getBorderWidth()*3
					&& fy + s.getHeight()*3 - s.getBorderWidth()*3 <= y + e.getSprite().getHeight()*3 - e.getSprite().getBorderWidth()*3) collidision = true;
			//bottom right collision
			if( x + e.getSprite().getBorderWidth()*3 <= fx + s.getWidth()*3 - s.getBorderWidth()*3
					&&  fx + s.getWidth()*3 - s.getBorderWidth()*3 <=  x + e.getSprite().getWidth()*3 - e.getSprite().getBorderWidth()*3
			&&  y + e.getSprite().getBorderWidth()*3 <= fy + s.getHeight()*3 - s.getBorderWidth()*3
					&& fy + s.getHeight()*3 - s.getBorderWidth()*3 <= y + e.getSprite().getHeight()*3 - e.getSprite().getBorderWidth()*3) collidision = true; 
			///_____________________ Dealing damage if the entity is a projectile
			
			if( e instanceof Projectile && collidision) this.foes.get(i).dealDamage( ((Projectile) e).getDamage() );
			
			if(collidision) return collidision;
		}
		return collidision;
	}
	
	///Well be done next...
    public boolean checkCollisionHero(int x, int y, Entity e) {
    	return true;
    }
	
	
	public List<Direction> findHero(Foe f){
		
		int f_x_cord = (f.getX() + (f.getSprite().getWidth() * 3)/2) 
						/ 
						(this.getTile(0, 0).getSprite().getWidth() * 3);
		int f_y_cord =  ( f.getY() + ((f.getSprite().getHeight() * 3)/2))
						 /
						 (this.getTile(0, 0).getSprite().getHeight() * 3);
		int h_x_cord = ( this.hero.getX() + (this.hero.getSprite().getWidth()*3) / 2)
							/
						(this.getTile(0, 0).getSprite().getWidth() * 3);
		int h_y_cord = (this.hero.getY() + (this.hero.getSprite().getHeight()*3) / 2) 
							/ 
						(this.getTile(0, 0).getSprite().getHeight() * 3);
		
		
		Node last = new Node(h_x_cord , h_y_cord, null , this.getTile(h_x_cord , h_y_cord), getGoalCost(f, this.hero), 0.0);
		
		
		List<Node> open = new ArrayList<Node>(); /// List of all the possible places to go to
		List<Node> closed = new ArrayList<Node>(); /// List of the places that were taken in consideration already
		List<Direction> directions = new ArrayList<Direction>();
		
		open.add(new Node( f_x_cord, f_y_cord, null, this.getTile(f_x_cord , f_y_cord), 0.0 , getGoalCost(f, this.hero) ) );
		
		while(true) {
			///In case we of stucking in a room ( no possible tiles left in the open list to consider )
			if(open.size() == 0) {
				System.out.println("open finishd");
				return null;
			}
			
			Collections.sort(open);
			Node current = open.get(0); /// grabing the current tile we are on so we add it to the closed list and keep its information
			open.remove(0);
			closed.add(current);
			
			/// the current information is used to check if the current == to the hero position
			if ( current.equals(new Node(h_x_cord , h_y_cord, null, this.getTile(0, 0), getGoalCost(f, this.hero) , 0.0) ) )
				{	System.out.println("found goal");
					while( current.getParent() != null) {
						this.addNodeDirection(current , directions);
						current = current.getParent();
						
					}
					return directions;
				}
			
			/// Checking the neighbors and add them if they were not solid tiles or they are not in the closed list ( we have checkind them already ) 
			for (int x = 0 ; x < 3 ; x++) {
				for(int y = 0 ; y < 3 ; y++) {
					
					Node neighbor = new Node( current.getxCord() + (x - 1) , current.getyCord() + (y - 1) , null , this.getTile( current.getxCord() + (x - 1), current.getyCord() + (y - 1)), 0.0 , 0.0);
					
					/// we are skipping when x == 1 and y == 1, because it will be equal to the "current" tile-node-postion
					if ( neighbor.traversable() || closed.contains(neighbor) || (x == 1 && y == 1) )
						continue;		

					/// THE NEIGBOR IS GOOD TO BE CHECKED AND ADDED TO THE OPEN LIST
					neighbor.sethCost(this.getNodesDistance(neighbor, current)); /// setting the neighbor hCost and gCost since it is good to be added tot he open list
					neighbor.setgCost(this.getNodesDistance(neighbor, last));
					
					int i = open.indexOf(neighbor);/// returns the index if found and -1 if not
					
					/// we are checking if the neighbor is already in the open list or not
					if( i == -1 ) {
						neighbor.setParent(current);// if it is not, add a parent to the neighbor and push it to the list
						open.add(neighbor);
					}else {// if it is already in the open list: check if it needs updating
						if(open.get(i).gethCost() > neighbor.gethCost())
							open.get(i).sethCost(neighbor.gethCost());
					}
				}
			}
		}
	}

private void addNodeDirection(Node node, List<Direction> directions) {
		// TODO Auto-generated method stub
		if(node.getxCord() - node.getParent().getxCord() == -1 && 
				node.getyCord() - node.getParent().getyCord() == -1)
			directions.add(Direction.NW);
		if(node.getxCord() - node.getParent().getxCord() == 0 && 
				node.getyCord() - node.getParent().getyCord() == -1)
			directions.add(Direction.N);
		if(node.getxCord() - node.getParent().getxCord() == 1 && 
				node.getyCord() - node.getParent().getyCord() == -1)
			directions.add(Direction.NE);
		
		if(node.getxCord() - node.getParent().getxCord() == -1 && 
				node.getyCord() - node.getParent().getyCord() == 0)
			directions.add(Direction.W);
		if(node.getxCord() - node.getParent().getxCord() == 1 && 
				node.getyCord() - node.getParent().getyCord() == 0 )
			directions.add(Direction.E);
		
		if(node.getxCord() - node.getParent().getxCord() == -1 && 
				node.getyCord() - node.getParent().getyCord() == 1)
			directions.add(Direction.SW);
		if(node.getxCord() - node.getParent().getxCord() == 0 && 
				node.getyCord() - node.getParent().getyCord() == 1)
			directions.add(Direction.S);
		if(node.getxCord() - node.getParent().getxCord() == 1 && 
				node.getyCord() - node.getParent().getyCord() == 1)
			directions.add(Direction.SE);
		
	}

private double getGoalCost(Foe f, Player h) {
//		// TODO Auto-generated method stub
	int f_x_cord = (f.getX() + (f.getSprite().getWidth() * 3)/2) 
			/ 
			(this.getTile(0, 0).getSprite().getWidth() * 3);
	int f_y_cord =  ( f.getY() + ((f.getSprite().getHeight() * 3)/2))
				 /
				 (this.getTile(0, 0).getSprite().getHeight() * 3);
	int h_x_cord = ( this.hero.getX() + (this.hero.getSprite().getWidth()*3) / 2)
					/
				(this.getTile(0, 0).getSprite().getWidth() * 3);
	int h_y_cord = (this.hero.getY() + (this.hero.getSprite().getHeight()*3) / 2) 
					/ 
				(this.getTile(0, 0).getSprite().getHeight() * 3);
	
	
		int xx = f_x_cord - h_x_cord;
		int yy = f_y_cord - h_y_cord;
		double distance = Math.sqrt(xx * xx + yy * yy);

		return distance;
	}

private double getNodesDistance(Node first, Node last) {
	int xx = first.getxCord() - last.getxCord();
	int yy = first.getyCord() - last.getyCord();
	double distance = Math.sqrt(xx * xx + yy * yy);
	return distance;
}

	///Engine functionality___ once we set the canvas we make it centralised so the Player sets in the middle of the player
	public void setCanvas(GameCanvas gameCanvas) {
		this.canvas = gameCanvas;
		shift(-(gameCanvas.getWidth()/2 - 48/2) , -(gameCanvas.getHeight()/2 - 48/2));
	}
	
	public void setPlayer(Player player) {
		this.hero = player;
	}

	public void renderEntity(int x , int y , Entity projectile) {
		// TODO Auto-generated method stub
		//System.out.println(projectile.getSprite().getHeight() );
		for(int yp = 0; yp < projectile.getSprite().getHeight() * 3 ; yp++) {
			int ya = y + yp - yShift;
			for(int xp = 0 ; xp < projectile.getSprite().getWidth() * 3; xp++) {
				int xa = x + xp - xShift;
				if( ya <= 0 || ya >= this.canvas.geHeight() || xa <= 0 || xa >= this.canvas.getWidth() || (projectile.getSprite().getPixels()[xp / 3 + (yp / 3) * projectile.getSprite().getWidth()] == 0xffff00ff) ) continue;
				this.canvas.pixels[xa + ya * this.canvas.getWidth()] = projectile.getSprite().getPixels()[xp/3 + (yp/3) * projectile.getSprite().getWidth()];
			}
		}
	}

	
	
///Mouse functionality
	public void mousePressed(int x, int y, int button) {
		// TODO Auto-generated method stub
		this.hero.setMouse(new Mouse(x, y, button));
		
	}

	public void mouseReleased() {
		// TODO Auto-generated method stub
		this.hero.setMouse(null);
	}
	
	public int getX() {return this.xShift;}
	public int getY() {return this.yShift;}
	
	public int getCanvasWidth() {
		return this.canvas.getWidth();
	}

	public int getCanvasHeight() {
		return this.canvas.getHeight();
	}
	public Tile getTile(int x , int y) {
		if(x >= this.width || y >= this.height || x < 0 || y < 0) return Tile.water;
		if(map[x + y * this.width] == 0xffa4ff00) return Tile.flower_grass1;
		if(map[x + y * this.width] == 0xff3b612f) return Tile.flower_grass2;
		if(map[x + y * this.width] == 0xffd1ff00) return Tile.wall1;
		if(map[x + y * this.width] == 0xff00083e) return Tile.wall2;
		if(map[x + y * this.width] == 0xff06ff00) return Tile.grass;
		if(map[x + y * this.width] == 0xff1bd8c6) return Tile.water;
		if(map[x + y * this.width] == 0xff76683d) return Tile.mud1;
		if(map[x + y * this.width] == 0xff430505) return Tile.mud2;
		return Tile.water;
	}

	public void addParticle(Particle particle) {
		// TODO Auto-generated method stub
		this.particles.add(particle);
	}

	public void addExplosion(Explosion explosion) {
		// TODO Auto-generated method stub
		this.explosions.add(explosion);
	}

	public void addFoe(Foe foe) {
		// TODO Auto-generated method stub
		this.foes.add(foe);
	}
	
}
