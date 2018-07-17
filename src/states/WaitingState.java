package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.ImageIcon;

import Imageloader.Assets;
import Imageloader.Text;
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
import network.Packet11QUIT;
import network.Server;
import ui.ClickListener;
import ui.ImageButton;
import ui.UIManager;

public class WaitingState extends State {

	private UIManager uiManager;
	private int size;
	private Image image;
	public WaitingState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		image = new ImageIcon(getClass().getResource("/images/menubg.gif")).getImage();
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
						e.printStackTrace();
					}
					handler.getGame().gameState = new GameState(handler);
					State.setState(handler.getGame().gameState );
				}}));
		
		}
		
		uiManager.addObject(new ImageButton(50,180 , 32,32, Assets.back2, new ClickListener(){
			
			@Override
			public void onClick() {
				int isServer = handler.getGame().isServer ? 1 : 0;
				if(isServer == 1) {
					handler.getGame().socketServer.id = 0L;
				}
				Packet11QUIT quit = new Packet11QUIT((int)(handler.getGame().player.id), isServer);
				quit.writeData(handler.getGame().socketClient);
				State.setState(new MenuState(handler));
			}}));
	
		size = handler.getMap().getEntityManager().getEntities().size();
		
	}

	@Override
	public void update() {
		 uiManager.update();	
	}

	@Override
	public void render(Graphics g) {
	    g.clearRect(0, 0, 480, 256);
		g.drawImage(image, 0, 0, 480, 256, null);
		g.setFont(new Font("default", Font.BOLD, 16));
		g.setColor(Color.BLACK);
		if(handler.getGame().isServer)
			Text.drawString(g, "LOBBY: " + handler.getGame().address,  150, 20,false, Color.BLACK, Assets.tat30);
		else
			Text.drawString(g, "LOBBY",  200, 20,false, Color.BLACK, Assets.tat30);
		Text.drawString(g, "Players",  20, 50,false, Color.BLACK, Assets.tm30);
		int y = 70;
	    ArrayList<ClientPlayer> players = new ArrayList<ClientPlayer>();
	    g.setColor(Color.RED);
	    for(int i =0; i < handler.getMap().getEntityManager().getEntities().size(); i++) {
		     for(Entity e : handler.getMap().getEntityManager().getEntities()) {
		    	 if(((ClientPlayer)e).id == i ) {
		    		 players.add((ClientPlayer) e);
		    	 }
		     }
	    }
	    int i =1;
	    Collections.sort(players);
		for(ClientPlayer e : players){
			   Text.drawString(g, i++ + ". " +((ClientPlayer)e).getUsername(),  0, y, false, Color.RED, Assets.silk20);
	    	   y += 20;
	    }
		uiManager.render(g);	
	}

}
