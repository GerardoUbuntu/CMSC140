package network;

public class Packet10PICK extends Packet {

	public int letterId, playerId, noLetters;
	public Packet10PICK(byte[] data) {
		super(10);
		String[] dataArray = readData(data).split(",");
		this.letterId = Integer.parseInt(dataArray[0]);
		this.playerId = Integer.parseInt(dataArray[1]);
		this.noLetters= Integer.parseInt(dataArray[2]);


	}
	
	public Packet10PICK(int letterId, int playerId,int noLetters) {
		super(10);
		this.letterId = letterId;
		this.playerId = playerId;
		this.noLetters = noLetters;
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
		return ("10"+ this.letterId+ "," + this.playerId +"," + this.noLetters).getBytes();
	}
	

}
