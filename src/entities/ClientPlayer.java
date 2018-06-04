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
import listeners.KeyManager;
import main.Handler;
import network.Packet02Move;

public class ClientPlayer extends Creature {

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
    public long id;
    public int type = 0, dead=0;
	
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
	}
	
	public void getInput(){
		if(keymanager!=null) {
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
		  Area a = new Area(new Rectangle(0, 0, 480, 256));
		  a.subtract(new Area(new Ellipse2D.Double((int)(x - handler.getGameCamera().getxOffset())-31,  (int) (y - handler.getGameCamera().getyOffset())-30, 100, 100)));
		  
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
		  if(keymanager!=null && type != 1 && dead == 0) {
			  g2d.setColor(Color.BLACK);
			  g2d.fill(a);
		  }
		  if(handler.getGame().winner == 2) {
			  g.setColor(Color.RED);
			  g.setFont(new Font("default", Font.BOLD,32));
          	  g.drawString("SlenderMan Wins", 130, 32);
		  }
		  if(dead == 0) {
			  g.setFont(new Font("default", Font.BOLD, 16));
          	  g.drawString(this.username, (int)(x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()) - 5);
    		  g.drawImage(getCurrentAnimationFrame(), (int)(x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height,null);
		  }else if(dead == 1 && keymanager != null) {
			  g.setColor(Color.RED);
			  g.setFont(new Font("default", Font.BOLD,32));
          	  g.drawString("You're Dead", 180, 100);
		  }
    		  g.setColor(Color.RED);
          g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()), (int)(y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
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
}
