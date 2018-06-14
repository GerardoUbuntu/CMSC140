package network;

public class Packet13SLOW extends Packet {

	public long id;
	public Packet13SLOW(byte[] data) {
		super(13);
		this.id = Integer.parseInt(readData(data));
	}
	
	public Packet13SLOW(long id) {
		super(13);
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
		return ("13"+this.id).getBytes();
	}
	

}
