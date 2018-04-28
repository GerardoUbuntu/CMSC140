package network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

import main.Game;

public class TCPServer extends Thread{
	
	private ServerSocket serverSocket;
	private CopyOnWriteArrayList<Integer> activeClients;
	private Game game;
	private int port;
	
	public TCPServer(Game game, int port) {
		this.game =  game;
		this.port = port;
	
	}
	
	@Override
    public synchronized void start() {
		try {
			serverSocket = new ServerSocket(port);
			Socket clientSocket;
			while((clientSocket = serverSocket.accept()) != null) {
				new Thread(new TCPConnection(this, clientSocket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
    }
	
	public class IpPort{
		
		public int port;
		public InetAddress ipAddress;
		
		public IpPort(InetAddress ipAddress, int port) {
			this.ipAddress = ipAddress;
			this.port = port;
		}
	}
	

}
