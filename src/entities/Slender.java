package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import Imageloader.Animation;
import Imageloader.Assets;
import main.Handler;

public class Slender extends Creature {

	
	//Animation
	private Animation down;
	private Animation up;
	private Animation right;
	private Animation left;
	private int move;
	
	public Slender(Handler handler,float x, float y) {
		super(handler, x, y, Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT);
		bounds.x = 4;
		bounds.y = 5;
		bounds.width = 25;
		bounds.height = 27;
		
		down = new Animation(100, Assets.slender_down);
		up = new Animation(100, Assets.slender_up);
		left = new Animation(100, Assets.slender_left);
		right = new Animation(100, Assets.slender_right);
	}

	@Override
	public void update() {
		down.tick();
        up.tick();
        left.tick();
        right.tick();
		getInput();
		move();
		handler.getGameCamera().centerOnEntity(this);
	}
	
	public void getInput(){
		xMove = 0;
		yMove = 0;
		if(handler.getKeyManager().up){
			yMove = -speed;
            move = 1;
		}
		else if(handler.getKeyManager().down){
			yMove = speed;
			move = 0;
		}
		else if(handler.getKeyManager().right){
			xMove = speed;
			move = 2;
		}
		else if(handler.getKeyManager().left){
			xMove = -speed;
			move = 3;
		}
	}

	@Override
	public void render(Graphics g) {
		  Graphics2D g2d = (Graphics2D) g;
		  Area a = new Area(new Rectangle(0, 0, 480, 256));
		 // a.subtract(new Area(new Ellipse2D.Double((int)(x - handler.getGameCamera().getxOffset())-16,  (int) (y - handler.getGameCamera().getyOffset())-16, 100, 100)));
		  
	      int xPoly[] = new int[3];
	      int yPoly[]= new int[3];
	      if(move == 0){
	    	  xPoly = new int[]{(int)(x - handler.getGameCamera().getxOffset()) -84,(int)(x - handler.getGameCamera().getxOffset()) + 16,(int)(x - handler.getGameCamera().getxOffset()) + 116};
	    	  yPoly = new int[]{(int) (y - handler.getGameCamera().getyOffset()) +116,(int) (y - handler.getGameCamera().getyOffset()) + 16, (int) (y - handler.getGameCamera().getyOffset()) + 116};
	      }else if(move == 1){
	    	  xPoly = new int[]{(int)(x - handler.getGameCamera().getxOffset()) -84,(int)(x - handler.getGameCamera().getxOffset()) + 16,(int)(x - handler.getGameCamera().getxOffset()) + 116};
	    	  yPoly = new int[]{(int) (y - handler.getGameCamera().getyOffset()) -84,(int) (y - handler.getGameCamera().getyOffset()) + 16, (int) (y - handler.getGameCamera().getyOffset()) - 84};
	      }else if(move == 2){
	    	  xPoly = new int[]{(int)(x - handler.getGameCamera().getxOffset()) +116,(int)(x - handler.getGameCamera().getxOffset()) + 16,(int)(x - handler.getGameCamera().getxOffset()) + 116};
	    	  yPoly = new int[]{(int) (y - handler.getGameCamera().getyOffset()) -84,(int) (y - handler.getGameCamera().getyOffset()) + 16, (int) (y - handler.getGameCamera().getyOffset()) + 116};
	      }else if(move == 3){
	    	  xPoly = new int[]{(int)(x - handler.getGameCamera().getxOffset()) -84,(int)(x - handler.getGameCamera().getxOffset()) + 16,(int)(x - handler.getGameCamera().getxOffset()) - 84};
	    	  yPoly = new int[]{(int) (y - handler.getGameCamera().getyOffset()) -84,(int) (y - handler.getGameCamera().getyOffset()) + 16, (int) (y - handler.getGameCamera().getyOffset()) + 116};
	      }
		  a.subtract(new Area(new Polygon(xPoly,yPoly, 3)));
		  g2d.setColor(Color.BLACK);
		  g2d.fill(a);
		  g.drawImage(getCurrentAnimationFrame(), (int)(x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height,null);
           //g.setColor(Color.RED);
           //g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()), (int)(y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
	}
	
    private BufferedImage getCurrentAnimationFrame(){
		if(xMove < 0){
			return left.getCurrentFrame();
		}else if(xMove > 0){
			return right.getCurrentFrame();
		}else if(yMove < 0){
			return up.getCurrentFrame();
		}else if(yMove > 0){
			return down.getCurrentFrame();
		}else
	       return down.getCurrentFrame();
	}

}