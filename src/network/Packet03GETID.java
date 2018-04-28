package network;

public class Packet03GETID extends Packet {

	public long id;
	public Packet03GETID(byte[] data) {
		super(03);
		this.id = Integer.parseInt(readData(data));
	}
	
	public Packet03GETID(long id) {
		super(03);
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
		return ("03"+ this.id).getBytes();
	}
	

}
