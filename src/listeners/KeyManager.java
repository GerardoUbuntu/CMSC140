package listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

	private boolean[] keys; 
	public boolean up, down, left, right, esc;

	public KeyManager() {
		keys = new boolean[256];
	}
	
	public void tick(){
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W] ;
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A] ;
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D] ;
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S] ;
		esc = keys[KeyEvent.VK_ESCAPE];
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
	     keys[arg0.getKeyCode()] = true;	
	     System.out.println("Pressed!");
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		 keys[arg0.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}
