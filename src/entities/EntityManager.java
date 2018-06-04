package entities;

import java.awt.Graphics;
import java.util.ArrayList;

import main.Handler;

public class EntityManager {
    
	private Handler handler;
	private Slender slender;
    private ArrayList<Entity> entities;	
	
    public EntityManager(Handler handler, Slender slender){
		this.slender = slender;
		this.handler = handler;
		entities = new ArrayList<Entity>();
	}
    
    public void update(){
    	for(int i =0; i<entities.size(); i++){
    		Entity e = entities.get(i);
    		e.update();
    	}
//    	slender.update();
    }
    
    public void render(Graphics g){
       for(Entity e : getEntities()){
    	   if(! e.equals(handler.getGame().player))
    		   e.render(g);
       }
       handler.getGame().player.render(g);
    }
    
    public void addEntity(Entity e){
    	entities.add(e);
    }
    
    public void removeEntity(Entity e){
    	entities.remove(e);
    }

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Slender getSlender() {
		return slender;
	}

	public void setSlender(Slender slender) {
		this.slender = slender;
	}


	public synchronized ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

	public void removeClientPlayer(long id) {
		int index = 0;
		for(Entity e: getEntities()) {
			if( e instanceof ClientPlayer && ((ClientPlayer)e).id == id) {
				break;
			}
			index++; 
		}
		this.entities.remove(index);
	}
	
	public int getClientPlayerIndex(long id) {
		int index = 0;
		for(Entity e: getEntities()) {
			if( e instanceof ClientPlayer && ((ClientPlayer)e).id == id) {
				break;
			}
			index++; 
		}
		return index;
	}
	
	public void movePlayer(long id, float x, float y, boolean isMoving, int move) {
		 int index = getClientPlayerIndex(id);
		 ClientPlayer player = (ClientPlayer) getEntities().get(index);
		 player.x = x;
		 player.y = y;
		 player.setMoving(isMoving);
		 player.setMove(move);
	}
	
	public void modifyPlayer(int id) {
		 int index = getClientPlayerIndex(id);
		 ClientPlayer player = (ClientPlayer) getEntities().get(index);
		 player.makeSlender();

	}
	
	public void setDead(int id) {
		 int index = getClientPlayerIndex(id);
		 ClientPlayer player = (ClientPlayer) getEntities().get(index);
		 player.dead = 1;
		 try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
    
}
