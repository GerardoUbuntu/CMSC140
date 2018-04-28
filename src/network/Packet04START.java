package network;

public class Packet04START extends Packet {

	
	public Packet04START(byte[] data) {
		super(04);
	}
	
	public Packet04START() {
		super(04);
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
		return ("04").getBytes();
	}
	

}
