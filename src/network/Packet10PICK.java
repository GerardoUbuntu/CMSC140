package network;

public class Packet10PICK extends Packet {

	public int letterId, playerId;

	public Packet10PICK(byte[] data) {
		super(10);
		String[] dataArray = readData(data).split(",");
		this.letterId = Integer.parseInt(dataArray[0]);
		this.playerId = Integer.parseInt(dataArray[1]);

	}
	
	public Packet10PICK(int letterId, int playerId) {
		super(10);
		this.letterId = letterId;
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
		return ("10"+ this.letterId+ "," + this.playerId).getBytes();
	}
	

}
