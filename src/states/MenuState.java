package states;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.net.BindException;

import javax.swing.JOptionPane;

import Imageloader.Assets;
import entities.ClientPlayer;
import listeners.WindowHandler;
import main.Handler;
import map.Map;
import network.Client;
import network.NetworkUtil;
import network.Packet00Login;
import network.Packet03GETID;
import network.Server;
import ui.ClickListener;
import ui.ImageButton;
import ui.UIManager;

public class MenuState extends State {

	
	private UIManager uiManager;
	Image image;
	public boolean isRunning;

	
	public MenuState(Handler handler) {
		super(handler);
		uiManager  = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
	
		image = Toolkit.getDefaultToolkit().createImage("/images/menubg.gif");
		uiManager.addObject(new ImageButton(220,130 , 32,32, Assets.start, new ClickListener(){

			@Override
			public void onClick() {
				handler.getGame().windowHandler = new WindowHandler(handler.getGame());
				NetworkUtil networkUtil = new NetworkUtil();
				
				System.out.println(networkUtil.getCurrentEnvironmentNetworkIp());
				
				handler.getGame().socketServer = new Server(handler.getGame());
				if(handler.getGame().socketServer.getSocket() != null)
					isRunning = true;
				else 
					isRunning = false;
				System.out.println(isRunning);
				if(isRunning) {
					handler.getGame().socketServer.start();
					handler.getGame().socketClient = new Client(handler.getGame(), networkUtil.getCurrentEnvironmentNetworkIp());
					handler.getGame().socketClient.start();
					handler.getGame().player = new ClientPlayer(handler, handler.getKeyManager(),100, 100, 50, 50, 
							JOptionPane.showInputDialog(handler.getGame().getWindow().getFrame(), "Please Enter a user name: "), null, -1, -1);
					Packet03GETID getId = new Packet03GETID(-1);
					getId.writeData(handler.getGame().getSocketClient());
					
					Map map = new Map(handler,"res/map/map1.txt");
					handler.setMap(map);
					ClientPlayer player = handler.getGame().player;
					handler.getMap().getEntityManager().addEntity(player);
					Packet00Login loginPacket  = new Packet00Login(player.getUsername(), player.x, player.y, player.id);
					
					if(handler.getGame().getServer() != null) {
						handler.getGame().getServer().addConnection(player, loginPacket, player.ipAddress, player.port);
					}
					
					loginPacket.writeData(handler.getGame().getSocketClient());
					System.out.println("Player Id " + handler.getGame().player.id);
					State.setState(new WaitingState(handler));
				}
			}}));
		
		uiManager.addObject(new ImageButton(220,160 , 32,32, Assets.start, new ClickListener(){

			@Override
			public void onClick() {
				handler.getGame().socketClient = new Client(handler.getGame(), JOptionPane.showInputDialog(handler.getGame().getWindow().getFrame(), "Please Enter remote IP address: "));
				handler.getGame().socketClient.start();
				handler.getGame().windowHandler = new WindowHandler(handler.getGame());
				handler.getGame().player = new ClientPlayer(handler, handler.getKeyManager(),100, 100, 50, 50, 
						JOptionPane.showInputDialog(handler.getGame().getWindow().getFrame(), "Please Enter a user name: "), null, -1, -1);
				Packet03GETID getId = new Packet03GETID(-1);
				getId.writeData(handler.getGame().getSocketClient());
				
				Map map = new Map(handler,"res/map/map1.txt");
				handler.setMap(map);
				ClientPlayer player = handler.getGame().player;
				handler.getMap().getEntityManager().addEntity(player);
				Packet00Login loginPacket  = new Packet00Login(player.getUsername(), player.x, player.y, player.id);
				
				if(handler.getGame().getServer() != null) {
					handler.getGame().getServer().addConnection(player, loginPacket, player.ipAddress, player.port);
				}
				
				loginPacket.writeData(handler.getGame().getSocketClient());
				System.out.println("Player Id " + handler.getGame().player.id);
				State.setState(new WaitingState(handler));
			}}));
		
		uiManager.addObject(new ImageButton(220,190 , 32,32, Assets.quit, new ClickListener(){

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
