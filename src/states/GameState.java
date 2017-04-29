package states;

import java.awt.Graphics;

import Imageloader.Assets;
import entities.Slender;

public class GameState extends State{
	
	private Slender slender;
	
	public GameState() {
		slender = new Slender(100, 100);
	}
	@Override
	public void update() {
		slender.update();
	}

	@Override
	public void render(Graphics g) {
		  g.drawImage(Assets.back, 0, 0, null);
		  slender.render(g);
	}
}
