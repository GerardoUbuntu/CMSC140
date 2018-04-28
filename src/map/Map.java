package map;

import java.awt.Graphics;

import Imageloader.Utils;
import entities.EntityManager;
import entities.Slender;
import main.Handler;
import tile.Tile;

public class Map {
    
	private int width,height;
	private int[][] tiles;
	private Handler handler;
	
	private EntityManager entityManager;
	public Map(Handler handler,String path) {
	   this.handler = handler;
	   entityManager = new EntityManager(handler, new Slender(handler, 100, 100));
	   loadMap(path);
	}
	
	public void update(){
	    entityManager.update();
	}
	
	public void render(Graphics g){
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILE_WIDTH);
		int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILE_WIDTH + 1);
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILE_HEIGHT);
		int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILE_HEIGHT + 1);
		
		for(int y = yStart;y < yEnd;y++){
			for(int x = xStart;x < xEnd;x++){
				getTile(x, y).render(g, (int) (x * Tile.TILE_WIDTH - handler.getGameCamera().getxOffset()),
						(int) (y * Tile.TILE_HEIGHT - handler.getGameCamera().getyOffset()));
			}
		}
		entityManager.render(g);
	}
	
	public Tile getTile(int x, int y){
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Tile.grassTile;
		
		Tile t = Tile.tiles[tiles[x][y]];
		if(t == null)
			return Tile.rockTile;
		return t;
	}
	
	private void loadMap(String path){
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
	
		System.out.println("Width " + width);
		System.out.println("Height " + height);
		tiles = new int[width][height];
		for(int y = 0;y < height;y++){
			for(int x = 0;x < width;x++){
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 2]);
			}
		}
	}
	

	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	

	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
}
	