package network;

public class Packet06WIN extends Packet {

	public long id;
	public Packet06WIN(byte[] data) {
		super(06);
		this.id = Integer.parseInt(readData(data));
	}
	
	public Packet06WIN(long id) {
		super(06);
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
		return ("06"+this.id).getBytes();
	}
	

}
