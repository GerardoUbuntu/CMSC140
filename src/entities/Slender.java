package entities;

import java.awt.Color;
import java.awt.Graphics;
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
		if(handler.getKeyManager().up)
			yMove = -speed;
		else if(handler.getKeyManager().down)
			yMove = speed;
		else if(handler.getKeyManager().right)
			xMove = speed;
		else if(handler.getKeyManager().left)
			xMove = -speed;
	}

	@Override
	public void render(Graphics g) {
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