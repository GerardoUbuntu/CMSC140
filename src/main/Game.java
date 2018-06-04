package main;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JOptionPane;

import Imageloader.Assets;
import entities.ClientPlayer;
import listeners.KeyManager;
import listeners.MouseManager;
import listeners.WindowHandler;
import network.Client;
import network.NetworkUtil;
import network.Packet00Login;
import network.Server;
import states.GameState;
import states.MenuState;
import states.State;

public class Game implements Runnable {
	
	private GameWindow window;
	private String title;
	private int height,width;
	private Thread thread;
	private boolean running = false;
	private BufferStrategy bs;
	private Graphics g;
	//states
	public State gameState;
	public State menuState;
	//input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	public WindowHandler windowHandler;
	
	//
	private GameCamera gameCamera;
	private Handler handler;
	
	public Client socketClient;
	public Server socketServer;
    public int winner = 0; 
	public ClientPlayer player;
	public Game(String title, int width, int height) {
		this.height = height;
		this.width = width;
		this.title = title;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
		init();
		
	}


	public MouseManager getMouseManager() {
		return mouseManager;
	}


	@Override
	public void run() {
		
		
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
				///System.out.println("Ticks and Frames: " + ticks);
				ticks = 0;
				timer = 0;
			}
			
		}
		
		stop();
	
	}
	
	private void init() {
		window = new GameWindow(title, width, height);
		window.getFrame().addKeyListener(keyManager);
		window.getFrame().addMouseListener(mouseManager);
		window.getFrame().addMouseMotionListener(mouseManager);
		window.getCanvas().addMouseListener(mouseManager);
		window.getCanvas().addMouseMotionListener(mouseManager);
		handler = new Handler(this);
		gameCamera = new GameCamera(handler,0,0);
		Assets.init();
		
		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		State.setState(menuState);
		
	}
	
	
	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public GameCamera getGameCamera() {
		return gameCamera;
	}

	private void update(){
		keyManager.tick();
		if(State.getState() != null)
			State.getState().update();
		if(State.getState() != menuState)
			socketClient.sendData("ping".getBytes());
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
    
    public KeyManager getKeyManager(){
    	return keyManager;
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
			e.printStackTrace();
		}
	}


	public GameWindow getWindow() {
		return window;
	}
	
	public Handler getHandler() {
		return this.handler;
	}
	
	public Client getSocketClient() {
		return this.socketClient;
	}

	public Server getServer() {
		return this.socketServer;
	}

	public void setWindow(GameWindow window) {
		this.window = window;
	}

}