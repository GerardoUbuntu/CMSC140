package network;

public class Packet04START extends Packet {

	public int id;
	public Packet04START(byte[] data) {
		super(04);
		this.id = Integer.parseInt(readData(data));
	}
	
	public Packet04START(int id) {
		super(04);
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
		return ("04"+this.id).getBytes();
	}
	

}
