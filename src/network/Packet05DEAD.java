package network;

public class Packet05DEAD extends Packet {

	public long id;
	public int noPlayers;
	public Packet05DEAD(byte[] data) {
		super(05);
		String[] dataArray = readData(data).split(",");
		this.id = Integer.parseInt(dataArray[0]);
		this.noPlayers = Integer.parseInt(dataArray[1]);
	}
	
	public Packet05DEAD(long id, int noPlayers) {
		super(05);
		this.id  = id;
		this.noPlayers = noPlayers;
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
		return ("05"+this.id+","+this.noPlayers).getBytes();
	}
	

}
