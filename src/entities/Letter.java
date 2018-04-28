package entities;

import java.awt.Graphics;

import main.Handler;
import tile.Tile;

public class Letter extends StaticEntity {

	private String letter; 
	public Letter(Handler handler, float x, float y, String letter) {
		super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
		this.letter = letter;
	}
	
	
	

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g) {
		
	}
    
}
