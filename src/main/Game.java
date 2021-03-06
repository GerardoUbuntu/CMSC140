package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import states.HelpState;
import states.InputDialog;
import states.MenuState;
import states.MyDialog;
import states.State;

public class Game implements Runnable {
	
	private GameWindow window;
	public MyDialog backDialog;
	public InputDialog inputDialog;
	private String title;
	private int height,width;
	private Thread thread;
	private boolean running = false;
	private BufferStrategy bs;
	private Graphics g;
	public boolean serverRunning = false;
	public boolean isServer;
	public int noLetters =10; 
	public int noPlayers;
	//states
	public State gameState;
	public State menuState;
	//input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	public WindowHandler windowHandler;
	private Image image;
	//
	private GameCamera gameCamera;
	private Handler handler;
	private InputDialog inputdialog;
	
	public Client socketClient;
	public Server socketServer = null;
    public int winner = 0; 
    public String address = "";
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
//				System.out.println("Ticks and Frames: " + ticks);
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
		backDialog = new MyDialog(window.getFrame());
		inputDialog = new InputDialog();
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
		if(!(State.getState() instanceof MenuState) && !(State.getState() instanceof HelpState))
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
		if(this.socketServer != null)
			return this.socketServer;
		else 
			return null;
	}

	public void setWindow(GameWindow window) {
		this.window = window;
	}
	
	public void setBackground(Image image) {
		System.out.println("Image");
		if(g!= null) {
			g.drawImage(image, 20, 20, window.getCanvas());
		}
			
	}

}