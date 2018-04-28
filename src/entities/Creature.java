package entities;


import main.Handler;
import tile.Tile;

public abstract class Creature extends Entity {
	
	public static final float DEFAULT_SPEED = 3.0f;
	protected float speed;
	public static final int DEFAULT_WIDTH = 32, DEFAULT_HEIGHT = 32;
    protected float xMove, yMove; 

	public Creature(Handler handler,float x, float y,int width, int height) {
		super(handler, x, y, width, height);
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
	}

    public void move(){
       moveX();
       moveY();
    }
    
    public void moveX(){
    	if(x + xMove + bounds.x +1 >= 0 && x + xMove + bounds.width +1 <= Tile.TILE_WIDTH * handler.getMap().getWidth()){
			if(xMove > 0){//Moving right
				int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH;
		        
				if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) &&
						!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)){
					x += xMove;
				}else{
					x = tx * Tile.TILE_WIDTH - bounds.x - bounds.width - 1;
				}
			}else if(xMove < 0){//Moving left
				int tx = (int) (x + xMove + bounds.x) / Tile.TILE_WIDTH;
				
				if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) &&
						!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)){
					x += xMove;
				}else{
					x = tx * Tile.TILE_WIDTH + Tile.TILE_WIDTH - bounds.x;
				}
			}
    	}
	}
	
	public void moveY(){
		if(y + yMove + bounds.y - 1 >= 0 && y + yMove + bounds.height -1 <= Tile.TILE_WIDTH * handler.getMap().getHeight()){
			if(yMove < 0){//Up
				int ty = (int) (y + yMove + bounds.y) / Tile.TILE_HEIGHT;
				
				if(!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) &&
						!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)){
					y += yMove;
				}else{
					y = ty * Tile.TILE_HEIGHT + Tile.TILE_HEIGHT - bounds.y;
				}
			}else if(yMove > 0){//Down
				int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT;
				
				if(!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) &&
						!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)){
					y += yMove;
				}else{
					y = ty * Tile.TILE_HEIGHT - bounds.y - bounds.height - 1;
				}
		
			}
		}
	}
    
    
    
    protected boolean collisionWithTile(int x, int y){
		return handler.getMap().getTile(x, y).isSolid();
	}


    
	public float getxMove() {
		return xMove;
	}


	public void setxMove(float xMove) {
		this.xMove = xMove;
	}


	public float getyMove() {
		return yMove;
	}


	public void setyMove(float yMove) {
		this.yMove = yMove;
	}


	public float getSpeed() {
		return speed;
	}


	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
}