package network;

public class Packet03GETID extends Packet {

	public long id;
	public int x, y;
	public Packet03GETID(byte[] data) {
		super(03);
		String[] dataArray = readData(data).split(",");
		this.id = Integer.parseInt(dataArray[0]);
		this.x = Integer.parseInt(dataArray[1]);
		this.y = Integer.parseInt(dataArray[2]);
	}
	
	public Packet03GETID(long id, int x, int y) {
		super(03);
		this.id = id;
		this.x = x;
		this.y = y;
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
		return ("03"+ this.id + "," + this.x +  "," + this.y).getBytes();
		
	}
	

}
