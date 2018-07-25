package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import network.Packet11QUIT;
import ui.ClickListener;
import ui.ImageButton;
import ui.UIManager;
import Imageloader.Assets;
import main.Handler;

public class HelpState extends State{

	private UIManager uiManager;
	private Image image, help;
	
	public HelpState(Handler handler) {
	   super(handler);
	   image = new ImageIcon(getClass().getResource("/images/menubg.gif")).getImage();
	   help = new ImageIcon(getClass().getResource("/images/help.png")).getImage();
	   uiManager = new UIManager(handler);
	   handler.getMouseManager().setUIManager(uiManager);
	   
	   uiManager.addObject(new ImageButton(50,360 , 135,65, Assets.back2, new ClickListener(){
			
			@Override
			public void onClick() {
				State.setState(new MenuState(handler));
			}}));
	}
	
	@Override
	public void update() {
		uiManager.update();
	}

	@Override
	public void render(Graphics g) {
		g.clearRect(0, 0, 640, 480);
		g.drawImage(image, 0, 0, 640, 480, null);
		g.drawImage(help, 0, 0, 640, 480, null);
		g.setFont(new Font("default", Font.BOLD, 16));
		g.setColor(Color.BLACK);
		uiManager.render(g);
	}

}
