package network;

public class Packet05DEAD extends Packet {

	public long id;
	public Packet05DEAD(byte[] data) {
		super(05);
		this.id = Integer.parseInt(readData(data));
	}
	
	public Packet05DEAD(long id) {
		super(05);
		this.id  = id;
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
		return ("05"+this.id).getBytes();
	}
	

}
