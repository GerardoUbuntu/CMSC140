package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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

public class InputState  extends State {
	private UIManager uiManager;
	Image image;
    public boolean  displayed=false;
	public boolean isRunning;
	
	public InputState(Handler handler) {
		super(handler);
		uiManager  = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		 JTextField input = new JTextField(10);
		 input.setBounds(120, 200, 60, 20);
		 input.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
		 input.setEditable(true);
		 input.setBackground( handler.getGame().getWindow().getFrame().getBackground());
		 input.setForeground( handler.getGame().getWindow().getFrame().getForeground());
		 handler.getGame().getWindow().getFrame().add(input);
		 handler.getGame().getWindow().getFrame().revalidate();
		 handler.getGame().getWindow().getFrame().repaint();
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g) {
	    
	}
}
