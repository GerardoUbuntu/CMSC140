package entities;

import java.awt.Graphics;

import Imageloader.Assets;
import main.Handler;
import tile.Tile;

public class Letter extends StaticEntity {

	public String letter; 
	public int id, visible = 1;
	public Letter(Handler handler, float x, float y, int id, String letter ) {
		super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
		this.letter = letter;
		this.id = id;
		System.out.println(letter+ "letter");
	}
	
	
	

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g) {
		if(visible == 1) {
			if(this.letter.equals("s")) {
				g.drawImage(Assets.s, (int)(x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height,null);
			}else if(this.letter.equals("l")) {
				g.drawImage(Assets.l, (int)(x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height,null);
			}else if(this.letter.equals("e")) {
				g.drawImage(Assets.e, (int)(x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height,null);
			}else if(this.letter.equals("n")) {
				g.drawImage(Assets.n, (int)(x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height,null);
			}else if(this.letter.equals("d")) {
				g.drawImage(Assets.d, (int)(x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height,null);
			}else if(this.letter.equals("r")) {
				g.drawImage(Assets.r, (int)(x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height,null);
			}else if(this.letter.equals("m")) {
				g.drawImage(Assets.m, (int)(x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height,null);
			}else if(this.letter.equals("a")) {
				g.drawImage(Assets.a, (int)(x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height,null);
			}
		}
	}
    
}
