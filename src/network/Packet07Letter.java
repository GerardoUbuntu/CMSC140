package network;

public class Packet07Letter extends Packet {

	public long id;
	public int x, y;
	public String letter;
	public Packet07Letter(byte[] data) {
		super(07);
		String[] dataArray = readData(data).split(",");
		this.x = Integer.parseInt(dataArray[0]);
		this.y = Integer.parseInt(dataArray[1]);
		this.id = Integer.parseInt(dataArray[2]);
		this.letter = dataArray[3];
	}
	
	public Packet07Letter(int x, int y, long id, String letter) {
		super(07);
		this.x = x;
		this.y = y;
		this.id  = id;
		this.letter = letter;
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
		return ("07"+ this.x + "," + this.y + "," + this.id + "," + this.letter).getBytes();
	}
	

}
