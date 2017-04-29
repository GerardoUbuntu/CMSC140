package main;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import Imageloader.Assets;
import states.GameState;
import states.State;

public class Game implements Runnable {
	
	private GameWindow window;
	private String title;
	private int height,width;
	private Thread thread;
	private boolean running = false;
	private BufferStrategy bs;
	private Graphics g;
	
	private State gameState;
	
    
	public Game(String title, int width, int height) {
		this.height = height;
		this.width = width;
		this.title = title;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		init();
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0; 
		
		while(running){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			if(delta >= 1){
				update();
				render();
				ticks++;
				delta--;
			}
			
			if(timer >= 1000000000){
				System.out.println("Ticks and Frames: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}
		stop();
	
	}
	
	private void init() {
		window = new GameWindow(title, width, height);	
		Assets.init();
		
		gameState = new GameState();
		State.setState(gameState);
	}
	
	private void update(){
		if(State.getState() != null)
			State.getState().update();
	}
    
    private void render(){
		 
    	  bs = window.getCanvas().getBufferStrategy();
		  if( bs == null ){
			  window.getCanvas().createBufferStrategy(3);
			  return;
		  }
		  g = bs.getDrawGraphics();
		  
		  if(State.getState() != null)
			State.getState().render(g);

		  bs.show();
		  g.dispose();
	}


	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
    public synchronized void stop(){
    	if(!running)
    		return;
    	running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
