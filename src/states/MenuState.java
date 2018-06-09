package states;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferStrategy;
import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
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

import Imageloader.Assets;
import main.Handler;
import ui.ClickListener;
import ui.ImageButton;
import ui.UIManager;

public class MenuState extends State {

	
	private UIManager uiManager;
	Image image;
    public boolean  displayed=false;
	public boolean isRunning;
	
	public MenuState(Handler handler) {
		super(handler);
		uiManager  = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
	    
	    URL url = null;
		try {
			url = new URL("http://i.stack.imgur.com/iQFxo.gif");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        image = new ImageIcon(getClass().getResource("/images/menubg.gif")).getImage();
		uiManager.addObject(new ImageButton(220,130 , 32,32, Assets.create, new ClickListener(){

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
					Map map = new Map(handler,"res/map/map1.txt");
					handler.setMap(map);
					String name = "No Name";
					String input = JOptionPane.showInputDialog(handler.getGame().getWindow().getFrame(), "Please Enter a user name: ");
					name = input == null ? name : input;
					handler.getGame().player = new ClientPlayer(handler, handler.getKeyManager(),100, 100, 50, 50, 
							name, null, -1, -1);
					Packet03GETID getId = new Packet03GETID(-1, -1, -1);
					getId.writeData(handler.getGame().getSocketClient());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ClientPlayer player = handler.getGame().player;
					handler.getMap().getEntityManager().addEntity(player);
					Packet00Login loginPacket  = new Packet00Login(player.getUsername(), player.x, player.y, player.id);
					System.out.println("player" + player.id);
					
					if(handler.getGame().getServer() != null) {
						handler.getGame().getServer().addConnection(player, loginPacket, player.ipAddress, player.port);
					}
					loginPacket.writeData(handler.getGame().getSocketClient());
					System.out.println("Player Id " + handler.getGame().player.id);
					State.setState(new WaitingState(handler));
				}
			}}));
		
		uiManager.addObject(new ImageButton(220,160 , 32,32, Assets.join, new ClickListener(){

			@Override
			public void onClick() {
				Pattern ptn = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
				String ip = JOptionPane.showInputDialog(handler.getGame().getWindow().getFrame(), "Please Enter remote IP address: ");
				Matcher mtch = ptn.matcher(ip);
//				while(! mtch.find() ) {
//					JOptionPane.showMessageDialog(handler.getGame().getWindow().getFrame(), "Input Valid Ip Address" , "", JOptionPane.WARNING_MESSAGE);
//					String ip = JOptionPane.showInputDialog(handler.getGame().getWindow().getFrame(), "Please Enter remote IP address: ");
//					
//				}
				if(mtch.find()) {
					handler.getGame().socketClient = new Client(handler.getGame(),ip);
					handler.getGame().socketClient.start();
					handler.getGame().windowHandler = new WindowHandler(handler.getGame());
					Map map = new Map(handler,"res/map/map1.txt");
					String name = "No Name";
					String input = JOptionPane.showInputDialog(handler.getGame().getWindow().getFrame(), "Please Enter a user name: ");
					name = input == null ? name : input;
					handler.getGame().player = new ClientPlayer(handler, handler.getKeyManager(),100, 100, 50, 50, 
							name, null, -1, -1);
					handler.setMap(map);
					Packet03GETID getId = new Packet03GETID(-1, -1, -1);
					getId.writeData(handler.getGame().getSocketClient());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ClientPlayer player = handler.getGame().player;
					Packet00Login loginPacket  = new Packet00Login(player.getUsername(), player.x, player.y, player.id);
					System.out.println("Player Id " + handler.getGame().player.id);
					if(handler.getGame().getServer() != null) {
						handler.getGame().getServer().addConnection(player, loginPacket, player.ipAddress, player.port);
					}
					loginPacket.writeData(handler.getGame().getSocketClient());
				
					handler.getMap().getEntityManager().addEntity(player);
					State.setState(new WaitingState(handler));
				}else {
					JOptionPane.showMessageDialog(handler.getGame().getWindow().getFrame(), "Input Valid Ip Address" , "", JOptionPane.WARNING_MESSAGE);
				}
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
//		g.drawImage(image, 0, 0, 480, 256, null);
		g.drawImage(Assets.title, 100, 50, null);
		uiManager.render(g);
		displayed = true;
	}

	@Override
	public void update() {
	   uiManager.update();
	}

}
