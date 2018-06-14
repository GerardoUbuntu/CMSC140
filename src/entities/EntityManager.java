package entities;

import java.awt.Graphics;
import java.util.ArrayList;

import main.Handler;
import network.Packet05DEAD;
import network.Packet13SLOW;

public class EntityManager {
    
	private Handler handler;
	private Slender slender;
    private ArrayList<Entity> entities;	
    private ArrayList<Letter> letters;
	
    public EntityManager(Handler handler, Slender slender){
		this.slender = slender;
		this.handler = handler;
		entities = new ArrayList<Entity>();
		letters = new ArrayList<Letter>();
	}
    
    public void update(){
    	for(int i =0; i<entities.size(); i++){
    		Entity e = entities.get(i);
    		e.update();
    	}
    	
    	for(int i =0; i<letters.size(); i++){
    		Entity e = letters.get(i);
    		e.update();
    	}
//    	slender.update();
    }
    
    public void render(Graphics g){
	   for(Letter e : getLetters()){
    	 e.render(g);
       }
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
    
    public void addLetter(Letter e){
    	letters.add(e);
    }
    
    public void removeLetter(Letter e){
    	letters.remove(e);
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
	
	public synchronized ArrayList<Letter> getLetters() {
		return letters;
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
	
	public void removeLetter(long id) {
		int index = 0;
		for(Letter e: getLetters()) {
			if( e instanceof Letter && e.id == id) {
				break;
			}
			index++; 
		}
		this.letters.remove(index);
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
	
	public void setInvisible(int id) {
		int index = 0;
		for(Letter e: getLetters()) {
			if( e instanceof Letter && e.id == id) {
				break;
			}
			index++; 
		}
		System.out.println(this.letters.get(index).letter);
		this.letters.get(index).visible = 0;
	}
	
	public void setSlow(int id) {
		 int index = getClientPlayerIndex(id);
		 ClientPlayer player = (ClientPlayer) getEntities().get(index);
		 player.slowdown = true;

	}
	
	public void minusHealth(int slenderId, int playerId) {
		if(getEntities().size() != 1) {
			 System.out.println("PlayerId: "+  playerId +"slenderId: " + slenderId + " size: " + getEntities().size());
			 int index = getClientPlayerIndex(playerId);
			 ClientPlayer player = (ClientPlayer) getEntities().get(index);
			 player.DEFAULT_HEALTH -= 1;
			 if(player.DEFAULT_HEALTH == 0) {
				 if(handler.getGame().player.id == playerId) {
						Packet05DEAD dead = new Packet05DEAD(playerId, -1);
						dead.writeData(handler.getGame().socketClient);
						System.out.println("Yeah yeah yeah "  + playerId );	
				 }
//				 setDead((int)player.id);
			 }
			 else if (player.DEFAULT_HEALTH > 0) {
				 if(handler.getGame().player.id == playerId) {
					handler.getGame().player.removeLight = true;
				 }else if(handler.getGame().player.id == slenderId)  {
					 Packet13SLOW slow = new Packet13SLOW(slenderId);
				     slow.writeData(handler.getGame().socketClient);
				 }
			 }
		}
	}
    
}
