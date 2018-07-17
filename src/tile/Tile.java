package tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
  	
	

	public static Tile[] tiles = new Tile[256];
	public static Tile grassTile = new GrassTile(0);
	public static Tile groundTile = new GroundTile(1);
	public static Tile rockTile = new RockTile(2);
	public static Tile treeTile = new TreeTile(3);
	public static Tile bushTile = new BushTile(4);
	public static Tile stoneTile = new StoneTile(5);
	public static Tile cuttreeTile = new CutTreeTile(6);
	public static Tile spookytreeTile = new SpookyTreeTile(7);
	public static Tile bigrockTile = new BigRockTile(8);
	
	public static final int TILE_WIDTH = 32, TILE_HEIGHT = 32;
	
	protected BufferedImage texture;
	protected final int id;
	
	public Tile(BufferedImage texture, int id  ) {
	    this.texture = texture;
	    this.id = id;
	    
	    tiles[id] = this;
    }
	
	public void update(){
		
	}
	
	public void render(Graphics g, int x, int y){
		g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
	}
	
	public int getId(){
		return id;
	}
	
	public boolean isSolid(){
		return false;
	}

}
