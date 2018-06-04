package entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import main.Handler;
import network.Packet05DEAD;

public abstract class Entity {
    
	public float x;
	public float y;
	protected int width, height;
	protected Handler handler;
	protected Rectangle bounds;
	
	public Entity(Handler handler, float x, float y, int width , int height){
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle(0,0, width, height);
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public boolean checkEntityCollisions(float xOffset, float yOffset) {
		for( Entity e : handler.getMap().getEntityManager().getEntities()) {
			if(e.equals(this)) {
				continue;
			}
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)) && ((ClientPlayer)e).dead == 0){
				if(((ClientPlayer)this).type == 1) {
					Packet05DEAD dead = new Packet05DEAD(((ClientPlayer)e).id);
					dead.writeData(handler.getGame().socketClient);
					System.out.println("Yeah yeah yeah "  + ((ClientPlayer)e).id );
				}
				return true;
			}
		}
		return false;
	}
    public Rectangle getCollisionBounds(float xOffset, float yOffset) { 
    	return new Rectangle((int)(x + bounds.x + xOffset),(int)(y + bounds.y + yOffset), bounds.width , bounds.height);
    }
    
	public abstract void update();
	
	public abstract void render(Graphics g);
}
