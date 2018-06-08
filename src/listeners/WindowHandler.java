package listeners;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import main.Game;
import network.Packet01Disconnect;
public class WindowHandler implements WindowListener {
   
	private final Game game;
	
	public WindowHandler(Game game) {
		this.game = game;
		this.game.getWindow().getFrame().addWindowListener(this);
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
			}

	@Override
	public void windowClosing(WindowEvent e) {
		try {
			Packet01Disconnect packet = new Packet01Disconnect(this.game.player.getUsername(), this.game.player.id);
			packet.writeData(this.game.getSocketClient());
		}catch(Exception n) {}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
	
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	
	}

}
