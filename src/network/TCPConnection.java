package network;

import java.net.Socket;

public class TCPConnection implements Runnable{

	private TCPServer server;
	private Socket socket;
	
	public TCPConnection(TCPServer server, Socket socket) {
		this.server = server;
		this.socket = socket;
	} 
	
	@Override
	public void run() {
		
	}

}
