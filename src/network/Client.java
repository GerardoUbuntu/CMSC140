package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import entities.ClientPlayer;
import main.Game;
import network.Packet.PacketTypes;
import states.GameState;
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
		    	handleMove((Packet02Move)packet);
		    	break;
		    case GETID :
		    	packet = new Packet03GETID(data);
		    	game.player.id = ((Packet03GETID)packet).id;
		    	break;
		    case START : 
		    	GameState state = new GameState(game.getHandler());
		    	states.State.setState(state);
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
}
