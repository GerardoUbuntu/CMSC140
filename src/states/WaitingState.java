package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;

import Imageloader.Assets;
import entities.ClientPlayer;
import entities.Entity;
import listeners.WindowHandler;
import main.Handler;
import map.Map;
import network.Client;
import network.NetworkUtil;
import network.Packet00Login;
import network.Packet03GETID;
import network.Packet04START;
import network.Packet07Letter;
import network.Server;
import ui.ClickListener;
import ui.ImageButton;
import ui.UIManager;

public class WaitingState extends State {

	private UIManager uiManager;
	private int size;
	
	public WaitingState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		if(handler.getGame().getServer() != null) {
			uiManager.addObject(new ImageButton(400,180 , 32,32, Assets.start, new ClickListener(){
	
				@Override
				public void onClick() {
					Packet07Letter letter = new Packet07Letter(-1, -1, -1, "");
					letter.writeData(handler.getGame().socketClient);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Packet04START start = new Packet04START(handler.getGame().getServer().SlenderId());
					start.writeData(handler.getGame().getServer());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					State.setState(new GameState(handler));
				}}));
		
		}
	
		size = handler.getMap().getEntityManager().getEntities().size();
		
	}

	@Override
	public void update() {
		 uiManager.update();	
	}

	@Override
	public void render(Graphics g) {
		g.clearRect(0, 0, 600, 480); 
		g.drawImage(Assets.bg, 0, 0, 480, 256, null);
		g.setFont(new Font("default", Font.BOLD, 16));
		g.setColor(Color.BLACK);
	    g.drawString("LOBBY", 210, 20);
	    g.drawString("Players", 200, 40);
		int y = 60;
	    ArrayList<Entity> players = new ArrayList<Entity>();
	    g.setColor(Color.RED);
	    for(int i =0; i < handler.getMap().getEntityManager().getEntities().size(); i++) {
		     for(Entity e : handler.getMap().getEntityManager().getEntities()) {
		    	 if(((ClientPlayer)e).id == i ) {
		    		 players.add(e);
		    	 }
		     }
	    }
		for(Entity e : players){
	    	   g.drawString(players.indexOf(e)+1 + ". " +((ClientPlayer)e).getUsername(), 0, y);
	    	   y += 20;
	    }
		uiManager.render(g);	
	}

}
