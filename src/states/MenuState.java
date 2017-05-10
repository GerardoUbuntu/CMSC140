package states;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import Imageloader.Assets;

import main.Handler;
import ui.ClickListener;
import ui.ImageButton;
import ui.UIManager;

public class MenuState extends State {

	
	private UIManager uiManager;
	Image image;

	
	public MenuState(Handler handler) {
		super(handler);
		uiManager  = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
	
		image = Toolkit.getDefaultToolkit().createImage("/images/menubg.gif");
		uiManager.addObject(new ImageButton(220,130 , 32,32, Assets.start, new ClickListener(){

			@Override
			public void onClick() {
				State.setState(new GameState(handler));
			}}));
		
		uiManager.addObject(new ImageButton(220,160 , 32,32, Assets.quit, new ClickListener(){

			@Override
			public void onClick() {
				System.exit(0);
			}}));
		
	}

	@Override
	public void render(Graphics g) {
		g.clearRect(0, 0, 480, 256);
		g.drawImage(Assets.title, 100, 50, null);
		uiManager.render(g);
	}

	@Override
	public void update() {
	   uiManager.update();
	}

}
