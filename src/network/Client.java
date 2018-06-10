package network;

import java.awt.Rectangle;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import entities.ClientPlayer;
import entities.Letter;
import main.Game;
import network.Packet.PacketTypes;
import states.GameState;
import states.MenuState;
import states.State;


public class Client extends Thread{
	
	private InetAddress ipAddress;
	private DatagramSocket socket;
	private Game game;
	
	
	public Client(Game game, String ipAddress) {
		this.game = game;
		try {
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
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
//	        System.out.println("Entity" + game.getHandler().getMap().getEntityManager().getEntities().size());
//	         String message = new String(packet.getData());
//	        System.out.println("ClIENT > " + message);
	        
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
		    	handleLogin((Packet00Login)packet, address, port);		    	
		    	break;
		    case DISCONNECT :
		    	packet = new Packet01Disconnect(data);
		    	System.out.println("[" + address.getHostAddress() + ":" + port + "] " + ((Packet01Disconnect)packet).getUsername() + " has left the world...");
		    	game.getHandler().getMap().getEntityManager().removeClientPlayer(((Packet01Disconnect)packet).id);
		    	break;
		    case MOVE : 
		    	packet = new Packet02Move(data);
		    	if(game.getHandler().getMap().getEntityManager().getEntities().size() > 0)
		    		handleMove((Packet02Move)packet);
		    	break;
		    case GETID :
		    	packet = new Packet03GETID(data);
		    	game.player.id = ((Packet03GETID)packet).id;
		    	game.player.x = ((Packet03GETID)packet).x;
		    	game.player.y = ((Packet03GETID)packet).y;
			
		    	break;
		    case START : 
		    	GameState state = new GameState(game.getHandler());
		    	packet = new Packet04START(data);
		    	modifyPlayer(((Packet04START)packet).id);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    	game.getHandler().getGameCamera().centerOnEntity(game.player);
		    	states.State.setState(state);
		        break;
		    case DEAD :
		    	packet = new Packet05DEAD(data);
		    	setDead((int)((Packet05DEAD)packet).id);
			
		    	break;
		    case WIN :
		    	packet = new Packet06WIN(data);
		        game.winner = ((int)((Packet06WIN)packet).id);
			   	  try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			   	  backToMenu();
		    	break;
		    case Letter:	
		    	Packet07Letter letter = new Packet07Letter(data);
		    	System.out.println("received" + letter.id);
		    	game.getHandler().getMap().getEntityManager().addLetter(new Letter(game.getHandler(), (float)(letter.x), (float)(letter.y), (int)(letter.id), letter.letter));
		        System.out.println("Letter Added");
		    	break;
		    case PICK:	
	    		Packet10PICK pick = new Packet10PICK(data);
	    		System.out.println("add pick" + pick.letterId);
	    		game.getHandler().getMap().getEntityManager().setInvisible(pick.letterId);;
	    		System.out.println("Letter Removed" + pick.letterId);
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		    	break;
		    	
		    	
		}
		
	}
	
	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1131);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void handleMove(Packet02Move packet) {
		System.out.println("Packet Id " + packet.id);
		game.getHandler().getMap().getEntityManager().movePlayer(packet.id, packet.getX(), packet.getY(), packet.isMoving(), packet.getMove());
	}
	
	private void handleLogin(Packet00Login packet, InetAddress address, int port) {
		System.out.println("[" + address.getAddress() + ":" + port + "] " + packet.getUsername() + "has joined the game...");
    	ClientPlayer client = new ClientPlayer(game.getHandler(),packet.getX(), packet.getY(), 50, 50, packet.getUsername(), address, port, packet.getId());
        game.getHandler().getMap().getEntityManager().addEntity(client);
       
	}
	
	private void modifyPlayer(int id) {
		game.getHandler().getMap().getEntityManager().modifyPlayer(id);
	}
	
	private void setDead(int id) {
		System.out.println("Dead " + id);
		game.getHandler().getMap().getEntityManager().setDead(id);
	}
	
	public void backToMenu() {
		states.State.setState(new MenuState(game.getHandler()));
	    if(game.socketServer != null) {
	    	game.socketServer.connectedPlayers.clear();
	    	game.socketServer.id = 0L;
	    	game.socketServer.letterId = 0L;
	    }
		game.socketClient = null;
		game.winner = 0;
		game.getHandler().getMap().getEntityManager().getEntities().clear();
		game.getHandler().getMap().getEntityManager().getLetters().clear();
		
	}
}
