package main;

import entities.Entity;
import tile.Tile;

public class GameCamera {
	
	private float xOffset, yOffset;
	private Handler handler;
	
	public GameCamera(Handler handler,float xOffset, float yOffset) {
	    this.handler = handler;
		this.xOffset = xOffset;
	    this.yOffset = yOffset;
	}
	
	
	public void centerOnEntity(Entity e){
		xOffset = e.getX() - handler.getWidth() / 2 + e.getWidth()/2;
		yOffset = e.getY() - handler.getHeight() / 2 + e.getHeight()/2;
		checkBlankSpace();

	}
	
	public void checkBlankSpace(){
		if(xOffset < 0){
			xOffset = 0;
		}else if(xOffset > handler.getMap().getWidth() * Tile.TILE_WIDTH - handler.getWidth()){
			xOffset = handler.getMap().getWidth() * Tile.TILE_WIDTH - handler.getWidth();
		}
		
		if(yOffset < 0){
			yOffset = 0;
		}else if(yOffset > handler.getMap().getHeight() * Tile.TILE_HEIGHT - handler.getHeight()){
			yOffset = handler.getMap().getHeight() * Tile.TILE_HEIGHT - handler.getHeight();
		}
	}
	
	public void move(float xAmt, float yAmt){
		xOffset += xAmt;
		yOffset += yAmt;
		checkBlankSpace();
	}
	
	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}

}
