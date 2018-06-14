package network;

public class Packet12TOUCH extends Packet {

	public long slenderId, playerId;
	public Packet12TOUCH(byte[] data) {
		super(12);
		String[] dataArray = readData(data).split(",");
		this.slenderId =  Integer.parseInt(dataArray[0]);
		this.playerId =  Integer.parseInt(dataArray[1]);
	}
	
	public Packet12TOUCH(long slenderId, long playerId) {
		super(12);
		this.slenderId  = slenderId;
		this.playerId = playerId;
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
		return ("12"+this.slenderId+","+this.playerId).getBytes();
	}
	

}
