package network;

public class Packet11QUIT extends Packet {

	public int playerId, isServer;

	public Packet11QUIT(byte[] data) {
		super(11);
		String[] dataArray = readData(data).split(",");
		this.playerId = Integer.parseInt(dataArray[0]);
		this.isServer = Integer.parseInt(dataArray[1]);
	}
	
	public Packet11QUIT(int playerId, int isServer) {
		super(11);
		this.playerId = playerId;
		this.isServer = isServer;
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
		return ("11" + this.playerId + "," + this.isServer).getBytes();
	}
	

}
