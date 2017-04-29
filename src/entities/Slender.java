package entities;

import java.awt.Graphics;

import Imageloader.Assets;

public class Slender extends Creature {

	public Slender(float x, float y) {
		super(x, y);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.slender, (int) x, (int) y, null);
	}

}