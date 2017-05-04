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
    	slender.update();
    }
    
    public void render(Graphics g){
       for(Entity e : entities){
    	   e.render(g);
       }
       slender.render(g);
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

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}
    
}
