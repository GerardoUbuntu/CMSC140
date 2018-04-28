package network;

public class Packet01Disconnect extends Packet {

	public String username;
	public long id;
	
	public Packet01Disconnect(byte[] data) {
		super(01);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.id = Long.parseLong(dataArray[1]);
	}
	
	public Packet01Disconnect(String username, long id) {
		super(01);
		this.username = username;
		this.id = id;
	}

	@Override
	public void writeData(Client client) {
		client.sendData(getData());
	}

	@Override
	public void writeData(Server server) {
		server.sendToAllClients(getData());
	}

	@Override
	public byte[] getData() {
		return ("01"+ this.username + "," + this.id).getBytes();
	}
	
	public String getUsername() {
	    return username;    
	}
	
	public long getId() {
		return id;
	}
	

}
