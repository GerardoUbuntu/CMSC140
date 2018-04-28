package states;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import Imageloader.Assets;
import entities.Slender;
import main.Handler;
import map.Map;
import ui.UIManager;

public class GameState extends State{
	
	private Map map;
	private UIManager uiManager;
	
	public GameState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
	}
	@Override
	public void update() {
		handler.getMap().update();
        if(handler.getKeyManager().esc)
        	State.setState(handler.getGame().menuState);
	}

	@Override
	public void render(Graphics g) {
		g.clearRect(0, 0, 600, 480); 
		handler.getMap().render(g);
	}
}