package states;

import java.awt.Graphics;

import Imageloader.Assets;
import entities.Slender;
import main.Handler;
import map.Map;

public class GameState extends State{
	
	private Map map;
	
	public GameState(Handler handler) {
		super(handler);
		map = new Map(handler,"res/map/map1.txt");
		handler.setMap(map);
	}
	@Override
	public void update() {
		map.update();
        if(handler.getKeyManager().esc)
        	State.setState(handler.getGame().menuState);
	}

	@Override
	public void render(Graphics g) {
		g.clearRect(0, 0, 600, 480); 
		map.render(g);
	}
}