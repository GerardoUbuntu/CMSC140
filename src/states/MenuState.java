package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.OptionPaneUI;

import Imageloader.Assets;
import entities.ClientPlayer;
import listeners.WindowHandler;
import main.Game;
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
	public boolean isClicked = false;
	private String ip = "";
	public MenuState(Handler handler) {
		super(handler);
		uiManager  = new UIManager(handler);
		javax.swing.UIManager.put("OptionPane.background", Color.DARK_GRAY);
		javax.swing.UIManager.put("Panel.background", Color.DARK_GRAY);
		javax.swing.UIManager.put("Button.background", Color.LIGHT_GRAY);
		handler.getMouseManager().setUIManager(uiManager);
	    
        image = new ImageIcon(getClass().getResource("/images/menubg.gif")).getImage();
       
		uiManager.addObject(new ImageButton(195,160,240, 63, Assets.create, new ClickListener(){

			@Override
			public void onClick() {

				handler.getGame().windowHandler = new WindowHandler(handler.getGame());
				NetworkUtil networkUtil = new NetworkUtil();
				
				handler.getGame().address = networkUtil.getCurrentEnvironmentNetworkIp();
				System.out.println(networkUtil.getCurrentEnvironmentNetworkIp());
				
				
					if(handler.getGame().socketServer != null)
						isRunning = true;
					else {
						isRunning = false;
						handler.getGame().socketServer = new Server(handler.getGame());
					}
				System.out.println("running" +isRunning);
		    
			System.out.println(handler.getGame().serverRunning);
			if(handler.getGame().serverRunning )	{
				 handler.getGame().isServer = true;
					if(!isRunning)
						handler.getGame().socketServer.start();
					handler.getGame().socketClient = new Client(handler.getGame(), networkUtil.getCurrentEnvironmentNetworkIp());
					handler.getGame().socketClient.start();
					Map map = new Map(handler,"/map/map1.txt");
					handler.setMap(map);
			   
//				String input = JOptionPane.showInputDialog(handler.getGame().getWindow().getFrame(), getPanel(), "Please Enter a user name: ", JOptionPane.PLAIN_MESSAGE);
				handler.getGame().inputDialog.getLabel().setText("Enter User Name");
				handler.getGame().inputDialog.getTextField().setText("");
				handler.getGame().inputDialog.setVisible(true);
				removeAllListeners();
			        handler.getGame().inputDialog.getOkButton().addActionListener(new ActionListener()
					{
						  public void actionPerformed(ActionEvent e)
						  {     
								   
								    System.out.println("Nano in");
									String name = "Player Name";
								    String input  = handler.getGame().inputDialog.getTextField().getText(); 
								    name = input == "" || input == null ? name : input;
									handler.getGame().player = new ClientPlayer(handler, handler.getKeyManager(),100, 100, 50, 50, 
											name, null, -1, -1);
									Packet03GETID getId = new Packet03GETID(-1, -1, -1);
									getId.writeData(handler.getGame().getSocketClient());
									try {
										Thread.sleep(1000);
									} catch (InterruptedException exx) {				
										exx.printStackTrace();
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
								    handler.getGame().inputDialog.setVisible(false);
							  
						  }
					});
			        
			    	handler.getGame().inputDialog.getCancelButton().addActionListener(new ActionListener()
					{
						  public void actionPerformed(ActionEvent e)
						  {
							  handler.getGame().inputDialog.setVisible(false);
						  }
					});
				
			
				
			}else {
				JOptionPane.showMessageDialog(handler.getGame().getWindow().getFrame(), "Server is already running" , "", JOptionPane.WARNING_MESSAGE);
			}
	
				
			}}));
		
		uiManager.addObject(new ImageButton(195,225 , 240,63, Assets.join, new ClickListener(){
            
			@Override
			public void onClick() {
				removeAllListeners();
				handler.getGame().inputDialog.getTextField().setText("");
				handler.getGame().inputDialog.getLabel().setText("Enter IP Address");
				handler.getGame().inputDialog.setVisible(true);	
				handler.getGame().inputDialog.getOkButton().addActionListener(new ActionListener()
				{
					  public void actionPerformed(ActionEvent e)
					  {     
						    ip = handler.getGame().inputDialog.getTextField().getText();
							Pattern ptn = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
//							String ip = JOptionPane.showInputDialog(handler.getGame().getWindow().getFrame(), getPanel(), "Please Enter remote IP address: ", JOptionPane.PLAIN_MESSAGE);
							Matcher mtch = ptn.matcher(ip);
//							while(! mtch.find() ) {
//								JOptionPane.showMessageDialog(handler.getGame().getWindow().getFrame(), "Input Valid Ip Address" , "", JOptionPane.WARNING_MESSAGE);
//								String ip = JOptionPane.showInputDialog(handler.getGame().getWindow().getFrame(), "Please Enter remote IP address: ");
//								
//							}
							if(mtch.find()) {
								removeAllListeners();
								handler.getGame().inputDialog.setVisible(false);
								enter();
								
							}else {
								JOptionPane.showMessageDialog(handler.getGame().getWindow().getFrame(), "Input Valid Ip Address" , "", JOptionPane.WARNING_MESSAGE);
							 
							}
					  }
				});
				
				handler.getGame().inputDialog.getCancelButton().addActionListener(new ActionListener()
				{
					  public void actionPerformed(ActionEvent e)
					  {     
						  handler.getGame().inputDialog.setVisible(false);	
						
					  }
				});
				
				

			}}));
		
		uiManager.addObject(new ImageButton(195,290 , 240,63, Assets.help, new ClickListener(){


			@Override
			public void onClick() {
				State.setState(new HelpState(handler));
			}}));
		
		uiManager.addObject(new ImageButton(195,355 , 240,63, Assets.quit, new ClickListener(){


			@Override
			public void onClick() {
				System.exit(0);
			}}));
		

	}

	@Override
	public void render(Graphics g) {
		g.clearRect(0, 0, 640, 480);
		g.drawImage(image, 0, 0, 640,480, null);
		g.drawImage(Assets.title, 65,30, 485, 110, null);
		uiManager.render(g);
		displayed = true;
	}

	@Override
	public void update() {
	   uiManager.update();
	}
	
	 private JPanel getPanel() {
	        JPanel panel = new JPanel();
	        ImageIcon image = null;
	        panel.setSize(200,200);
	        return panel;
	}
	 
	public void removeAllListeners() {
		ArrayList<JButton> jbuttons = new ArrayList<JButton>();
		jbuttons.add(handler.getGame().backDialog.getYesButton());
		jbuttons.add(handler.getGame().inputDialog.getOkButton());
		jbuttons.add(handler.getGame().inputDialog.getCancelButton());
		
		for(JButton button : jbuttons) {
			for(ActionListener al : button.getActionListeners()) {
				button.removeActionListener(al);
			}
		}
	}
	
	public void enter() {
		handler.getGame().socketClient = new Client(handler.getGame(),ip);
		handler.getGame().socketClient.start();
		handler.getGame().windowHandler = new WindowHandler(handler.getGame());
		Map map = new Map(handler,"/map/map1.txt");
		handler.getGame().inputDialog.setVisible(true);
		handler.getGame().inputDialog.getTextField().setText("");
		handler.getGame().inputDialog.getLabel().setText("Enter Username");
		handler.getGame().inputDialog.getOkButton().addActionListener(new ActionListener()
		{
			  public void actionPerformed(ActionEvent e)
			  {     
				    String name = "No Name";
				    String input  = handler.getGame().inputDialog.getTextField().getText();  
				    name = input == null ? name : input;
				    handler.getGame().player = new ClientPlayer(handler, handler.getKeyManager(),100, 100, 50, 50, 
							name, null, -1, -1);
					handler.setMap(map);
					Packet03GETID getId = new Packet03GETID(-1, -1, -1);
					getId.writeData(handler.getGame().getSocketClient());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
					if(handler.getGame().player.id != -1) {
					    handler.getGame().isServer = false;
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
						JOptionPane.showMessageDialog(handler.getGame().getWindow().getFrame(), "Server does not exist" , "", JOptionPane.WARNING_MESSAGE);
					}
					handler.getGame().inputDialog.setVisible(false);
			  }
			  
		});
		
//		String input = JOptionPane.showInputDialog(handler.getGame().getWindow().getFrame(), getPanel(), "Please Enter a user name: ", JOptionPane.PLAIN_MESSAGE);
		
		
	}


}
