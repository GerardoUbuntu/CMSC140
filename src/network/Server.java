package network;


import java.awt.Rectangle;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import entities.ClientPlayer;
import main.Game;
import network.Packet.PacketTypes;
import tile.Tile;

public class Server extends Thread{
	
	private DatagramSocket socket;
	private Game game;
	public int humans = 0, letters;
	public int currentPick = -1;
	public List<ClientPlayer> connectedPlayers = new ArrayList<ClientPlayer>();
	public long id = 0L;
	private List<Rectangle> rectangles ;
	public long letterId = 0L;
	public static String slender= "slenderman";
	public Server(Game game) {
		rectangles = new ArrayList<Rectangle>();
		this.game = game;
		game.serverRunning = true;
		try {
			this.socket = new DatagramSocket(1131);
		} catch (SocketException e) {
//			JOptionPane.showMessageDialog(game.getWindow().getFrame(), "Server is already running" , "", JOptionPane.WARNING_MESSAGE);
		    game.serverRunning =false;
		}
		
	}
	
	public void run() {
		while(true) {
			
	        byte[] data = new byte[1024];
	        DatagramPacket packet  = new DatagramPacket(data,data.length);
	        try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
				
			}
	        this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
	        
		}
	}
	
	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
		PacketTypes type  = Packet.lookupPacket(message.substring(0,2));
		Packet packet = null ;
		switch( type ) {
		    default : 
		    case INVALID :
		    	break;
		    case LOGIN :
		        packet = new Packet00Login(data);
		        System.out.println("username Packet:" + ((Packet00Login)packet).getUsername());
		    	System.out.println("[" + address.getHostAddress() + ":" + port + "] " + ((Packet00Login)packet).getUsername() + " has connected ...");
		    	ClientPlayer client = new ClientPlayer(game.getHandler(), 100, 100, 50, 50, ((Packet00Login)packet).getUsername(), address, port, ((Packet00Login)packet).getId());
		        this.addConnection(client, ((Packet00Login)packet), address, port);
		    	break;
		    case DISCONNECT :
		    	packet = new Packet01Disconnect(data);
		    	System.out.println("[" + address.getHostAddress() + ":" + port + "] " + ((Packet01Disconnect)packet).getUsername() + " has left ...");
		        this.removeConnection(((Packet01Disconnect)packet));
		    	break;
		    case MOVE:
		    	packet = new Packet02Move(data);
//		    	System.out.println(((Packet02Move)packet).getUsername() +" has moved to" + ((Packet02Move)packet).getX() + "," +  ((Packet02Move)packet).getY());
		    	this.handleMove(((Packet02Move)packet));
		    	break;
		    case GETID:
		    	Rectangle coord = getCoord();
		    	packet = new Packet03GETID(getId(),coord.x, coord.y);	    	
		    	sendData(packet.getData(), address, port);
		    	break;
		    case DEAD:
		    	humans -= 1;
		    	packet = new Packet05DEAD(data);
		    	((Packet05DEAD)packet).noPlayers = humans;
		    	packet.writeData(this);    	
		    	if(humans== 0) {
		    		Packet06WIN win = new Packet06WIN(2);
		    		win.writeData(this);
		    	}
		    	break;
		    case Letter:
		    	letters = slender.length();
		    	for(int i=0; i<slender.length(); i++) {
		    		Rectangle coord1 = getCoord();
		    		Packet07Letter letter = new Packet07Letter(coord1.x, coord1.y , (int)(getLetterId()), ""+slender.charAt(i));
//		    		System.out.println("add" + letter.id);
		    		letter.writeData(this);
		    	}
		    	break;
		    case PICK:	
		    
	    		Packet10PICK pick = new Packet10PICK(data);
	    		
	    		System.out.println(pick.letterId);
	    		if(pick.letterId != currentPick) {
	    			letters -= 1;
	    			pick.noLetters = letters;
		    		pick.writeData(this);
		    		if(letters == 0) {
			    		Packet06WIN win = new Packet06WIN(1);
			    		win.writeData(this);
			    	}
		    		currentPick = pick.letterId;
	    		}
		    	break;
		    case QUIT:
		    	Packet11QUIT quit = new Packet11QUIT(data);
		    	this.connectedPlayers.remove(getClientPlayerIndex(quit.playerId));
		    	quit.writeData(this);
		    	break;
		    case TOUCH: 
		    	Packet12TOUCH touch = new Packet12TOUCH(data);
		    	touch.writeData(this);
		    	break;
		    case SLOW:
		    	Packet13SLOW slow = new Packet13SLOW(data);
				slow.writeData(this);
				break;
		}
		
	}

	public void removeConnection(Packet01Disconnect packet) {
		this.connectedPlayers.remove(getClientPlayerIndex(packet.id));
		packet.writeData(this);
	}
	
	public ClientPlayer getClientPlayer(long id) {
		for(ClientPlayer c: this.connectedPlayers) {
			if(c.id == id) {
				return c;
			}
		}
		
		return null;
	}
	
	public int getClientPlayerIndex(long id) {
		int index = 0;
		for(ClientPlayer c: this.connectedPlayers) {
			if(c.id == id) {
				break;
			}
			index++;
		}
		
		return index;
	}
	
	public void addConnection(ClientPlayer client, Packet00Login packet, InetAddress address, int port) {
		boolean alreadyConnected = false;
		System.out.println(client.port);
		System.out.println("size" + this.connectedPlayers.size());
		for(ClientPlayer c : this.connectedPlayers) {
					
			if(c.id == client.id ) {
				System.out.println("Username: " +  c.getUsername());
				System.out.println("Client Username: " +  client.getUsername());
				
				if(c.ipAddress == null) {
					c.ipAddress = client.ipAddress;	
				}
				
				if(c.port == -1) {
					c.port = client.port;
				}
				alreadyConnected = true;
			}
			else {
				System.out.println("port" + c.port);
				sendData(packet.getData(), c.ipAddress, c.port);
				
				Packet00Login packet1 = new Packet00Login(c.getUsername(), c.x, c.y, c.id);
				sendData(packet1.getData(), client.ipAddress, client.port);
				System.out.println(c);
			}
		}
		if(!alreadyConnected) {
			this.connectedPlayers.add(client);
		}
		System.out.println("Connected Players "+this.connectedPlayers.size());
		
	}

	public void sendData(byte[] data, InetAddress ipAddress, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}

	public void sendToAllClients(byte[] data) {
		for(ClientPlayer c : connectedPlayers) {
			sendData(data, c.ipAddress, c.port);
		}
	}
	
	public void handleMove(Packet02Move packet) {
		if(getClientPlayer(packet.id) != null) {
			int index = getClientPlayerIndex(packet.id);
			this.connectedPlayers.get(index).x = packet.getX();
			this.connectedPlayers.get(index).y = packet.getY();
			this.connectedPlayers.get(index).setMoving(packet.isMoving());
			this.connectedPlayers.get(index).setMove(packet.getMove()); 
			packet.writeData(this);
		}
	}
	
	public long getId() {
		return id++;
	}
	
	public long getLetterId() {
		return letterId++;
	}
	
	public DatagramSocket getSocket() {
	    return this.socket;  	
	}
	
	public Rectangle getCoord() {
		Random rand = new Random(); 
		int x, y;
		Rectangle rect = null;
		if (rectangles != null && !rectangles.isEmpty()) {
			rect = rectangles.get(rectangles.size()-1);
		}
		
		while(true) {
			x = rand.nextInt(1546); 
		    y = rand.nextInt(506);
		    
		  if(!(game.getHandler().getMap().getTile((x+32)/Tile.TILE_WIDTH, (y+32)/Tile.TILE_HEIGHT).isSolid()) && !(rectangles.contains(new Rectangle(x, y, 32,32))) )
		    	break;
			
		}   
		rectangles.add(new Rectangle(x, y, 32,32));
		return new Rectangle(x, y, 32,32);
	}
	
	public int SlenderId() {	
		Random rand = new Random(); 
		humans = connectedPlayers.size() - 1;
		currentPick = -1;
		return rand.nextInt(connectedPlayers.size()); 
	}
}
