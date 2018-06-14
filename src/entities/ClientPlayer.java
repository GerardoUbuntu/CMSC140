package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.net.InetAddress;

import Imageloader.Animation;
import Imageloader.Assets;
import Imageloader.Text;
import listeners.KeyManager;
import main.Handler;
import network.Packet02Move;
import states.State;

public class ClientPlayer extends Creature implements Comparable<ClientPlayer> {

	//Animation
	private Animation down;
	private Animation up;
	private Animation right;
	private Animation left;
	private int move;
	
	public InetAddress ipAddress;
	private String username;
	public int port;
	public KeyManager keymanager;
	
	private boolean isMoving;
    public long id, tick =0;
    public int type = 0, dead=0, pause =0;
    public boolean removeLight,  slowdown;
	public int countdown=3;
	public float tempSpeed = 1, saveSpeed =0;
    
	public ClientPlayer(Handler handler, KeyManager keymanager,  float x, float y, int width, int height, String username, InetAddress ipAddress, int port, long id) {
		super(handler, x, y, Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT);
		this.handler = handler;
		this.ipAddress = ipAddress;
		this.port = port;
		this.username = username;
		this.keymanager = keymanager;
		this.id = id;
		bounds.x = 10;
		bounds.y = 15;
		bounds.width = 15;
		bounds.height = 15;
		handler.getGameCamera().centerOnEntity(this);
		
		down = new Animation(100, Assets.human_down);
		up = new Animation(100, Assets.human_up);
		left = new Animation(100, Assets.human_left);
		right = new Animation(100, Assets.human_right);
	}
	
	public ClientPlayer(Handler handler ,float x, float y, int width, int height, String username, InetAddress ipAddress, int port, long id) {
		super(handler, x, y, Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT);
		this.username = username;
		this.ipAddress = ipAddress;
		this.port = port;
		this.handler = handler;
		this.id = id;
		bounds.x = 10;
		bounds.y = 15;
		bounds.width = 15;
		bounds.height = 13;
		
		down = new Animation(100, Assets.human_down);
		up = new Animation(100, Assets.human_up);
		left = new Animation(100, Assets.human_left);
		right = new Animation(100, Assets.human_right);
	}

	@Override
	public void update() {
		down.tick();
        up.tick();
        left.tick();
        right.tick();
		getInput();	
		if(slowdown) {
			tick++;
			if(tick == 100) {
				this.speed = this.saveSpeed;
				slowdown = false;
			}
		}
		if(removeLight) {
			tick++;
			if(tick == 100) {
				removeLight = false;
			}
		}
	}	
	
	public void getInput(){
		if(keymanager!=null && pause == 0) {
			xMove = 0;
			yMove = 0;
			if(keymanager.up){
				yMove = -speed;
	            move = 1;
			}
			else if(keymanager.down){
				yMove = speed;
				move = 0;
			}
			else if(keymanager.right){
				xMove = speed;
				move = 2;
			}
			else if(keymanager.left){
				xMove = -speed;
				move = 3;
			}
			
			if(xMove != 0 || yMove != 0) {
				move();
				isMoving = true;
			    System.out.println("Client Player" + this.id);
				Packet02Move packet = new Packet02Move(this.getUsername(), super.x, super.y, this.isMoving, this.move, this.id);
				packet.writeData(this.handler.getGame().getSocketClient());
				handler.getGameCamera().centerOnEntity(this);
			}
			
		}
	}

	@Override
	public void render(Graphics g) {
		 Graphics2D g2d = (Graphics2D) g;	  
//	      int xPoly[] = new int[3];
//	      int yPoly[]= new int[3];
//	      if(move == 0){
//	    	  xPoly = new int[]{(int)(x - handler.getGameCamera().getxOffset()) -84,(int)(x - handler.getGameCamera().getxOffset()) + 16,(int)(x - handler.getGameCamera().getxOffset()) + 116};
//	    	  yPoly = new int[]{(int) (y - handler.getGameCamera().getyOffset()) +116,(int) (y - handler.getGameCamera().getyOffset()) + 16, (int) (y - handler.getGameCamera().getyOffset()) + 116};
//	      }else if(move == 1){
//	    	  xPoly = new int[]{(int)(x - handler.getGameCamera().getxOffset()) -84,(int)(x - handler.getGameCamera().getxOffset()) + 16,(int)(x - handler.getGameCamera().getxOffset()) + 116};
//	    	  yPoly = new int[]{(int) (y - handler.getGameCamera().getyOffset()) -84,(int) (y - handler.getGameCamera().getyOffset()) + 16, (int) (y - handler.getGameCamera().getyOffset()) - 84};
//	      }else if(move == 2){
//	    	  xPoly = new int[]{(int)(x - handler.getGameCamera().getxOffset()) +116,(int)(x - handler.getGameCamera().getxOffset()) + 16,(int)(x - handler.getGameCamera().getxOffset()) + 116};
//	    	  yPoly = new int[]{(int) (y - handler.getGameCamera().getyOffset()) -84,(int) (y - handler.getGameCamera().getyOffset()) + 16, (int) (y - handler.getGameCamera().getyOffset()) + 116};
//	      }else if(move == 3){
//	    	  xPoly = new int[]{(int)(x - handler.getGameCamera().getxOffset()) -84,(int)(x - handler.getGameCamera().getxOffset()) + 16,(int)(x - handler.getGameCamera().getxOffset()) - 84};
//	    	  yPoly = new int[]{(int) (y - handler.getGameCamera().getyOffset()) -84,(int) (y - handler.getGameCamera().getyOffset()) + 16, (int) (y - handler.getGameCamera().getyOffset()) + 116};
//	      }
//		  a.subtract(new Area(new Polygon(xPoly,yPoly, 3)));
		   Area a = new Area(new Rectangle(0, 0, 480, 256));
		  
		  if(type == 1 && slowdown) {
			  this.saveSpeed = this.speed;
			  this.speed = tempSpeed;
		  }
		
		  if(handler.getGame().winner == 2) {
			  g.setColor(Color.RED);
//			  g.setFont(new Font("default", Font.BOLD,32));
//          	  g.drawString("SlenderMan Wins", 130, 32);
			  Text.drawString(g, "SlenderMan Wins", 130, 32, false, Color.RED, Assets.tat30	);
          	  pause = 1;
		  }else if(handler.getGame().winner == 1) {
			  g.setColor(Color.BLUE);
//			  g.setFont(new Font("default", Font.BOLD,32));
//          	  g.drawString("Humans Wins", 130, 32);
			  Text.drawString(g, "Humans Win", 130, 32, false, Color.BLUE, Assets.tm30);
          	  pause = 1;
         
		  }
		  if(dead == 0) {
			  g.setFont(new Font("default", Font.BOLD, 16));
			  Color color = type == 1 ? Color.RED : Color.BLUE;
			  Text.drawString(g, this.username,(int)(x - handler.getGameCamera().getxOffset()) + 16, (int) (y - handler.getGameCamera().getyOffset()) - 5, true, color, Assets.silk12);
//          	  g.drawString(this.username, (int)(x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()) - 5);
    		  g.drawImage(getCurrentAnimationFrame(), (int)(x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height,null);
		  }else if(dead == 1 && keymanager != null) {
			  g.setColor(Color.RED);
			  g.setFont(new Font("default", Font.BOLD,32));
          	  g.drawString("You're Dead", 180, 100);
		  }
		 
																									
		  if(keymanager!=null && type != 1 && dead == 0) {
			  if(removeLight) {
			      g2d.setColor(Color.BLACK);
			      g2d.fill(a);
			  }else {
				 g2d.setColor(Color.BLACK);
				 a.subtract(new Area(new Ellipse2D.Double((int)(x - handler.getGameCamera().getxOffset())-21,  (int) (y - handler.getGameCamera().getyOffset())-20, 70, 70)));
			     g2d.fill(a);
			  }
		  }
			  
//          g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()), (int)(y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
		     if(keymanager != null) {
				 addStats(g);
			 }
	}
	
	 private void addStats(Graphics g) {
		 if(type == 0) {
			 Text.drawString(g, "P:" + handler.getGame().noPlayers, 350, 20, false, Color.BLUE, Assets.silk20);
			 Text.drawString(g, "H:" + this.DEFAULT_HEALTH, 390, 20, false, Color.RED, Assets.silk20);
			 Text.drawString(g, "L:" + handler.getGame().noLetters, 430, 20, false, Color.YELLOW, Assets.silk20);
		 }else {
			 Text.drawString(g, "P:" + handler.getGame().noPlayers, 390, 20, false, Color.BLUE, Assets.silk20);
			 Text.drawString(g, "L:" + handler.getGame().noLetters, 430, 20, false, Color.YELLOW, Assets.silk20);
		 }
	 }

	private BufferedImage getCurrentAnimationFrame(){
			BufferedImage movingDir = null;
			
		    if(move == 3){
				movingDir = left.getCurrentFrame();
			}else if(move == 2){
				movingDir = right.getCurrentFrame();
			}else if(move == 1){
				movingDir = up.getCurrentFrame();
			}else if(move == 0){
			    movingDir = down.getCurrentFrame();
			}
		    return movingDir;
		}

	public void makeSlender() {
		type = 1;
		down = new Animation(100, Assets.slender_down);
		up = new Animation(100, Assets.slender_up);
		left = new Animation(100, Assets.slender_left);
		right = new Animation(100, Assets.slender_right);
		speed = 2;																														
	}
	
	public String getUsername() {
		return this.username;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public int getMove() {
		return move;
	}

	public void setMove(int move) {
		this.move = move;
	}

	@Override
	public int compareTo(ClientPlayer o) {
		int returnVal = 0;
		if(this.id < o.id)
			returnVal = -1;
		else if(this.id > o.id)
			returnVal = 1;
		else if(this.id == o.id)
			returnVal = 0;
		
		return returnVal;
	}
	

}
