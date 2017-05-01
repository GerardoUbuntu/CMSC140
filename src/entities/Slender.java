package entities;

import java.awt.Color;
import java.awt.Graphics;

import Imageloader.Assets;
import main.Handler;

public class Slender extends Creature {

	
	public Slender(Handler handler,float x, float y) {
		super(handler, x, y, Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT);
		bounds.x = 4;
		bounds.width = 25;
		bounds.height = 30;
	}

	@Override
	public void update() {
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
		   g.drawImage(Assets.slender, (int)(x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height,null);
           g.setColor(Color.RED);
           g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()), (int)(y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
	}

}