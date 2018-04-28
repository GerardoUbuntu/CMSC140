package network;

public class Packet00Login extends Packet {

	public String username;
	private float x, y;
	public long id;
	
	
	public Packet00Login(byte[] data) {
		super(00);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.x = Float.parseFloat(dataArray[1]);
		this.y = Float.parseFloat(dataArray[2]);
		this.id = Long.parseLong(dataArray[3]);
	}
	
	public Packet00Login(String username, float x, float y, long id) {
		super(00);
		this.username = username;
		this.x = x;
		this.y = y;
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
		return ("00"+ this.username + "," + this.x + "," + this.y + "," + this.id).getBytes();
	}
	
	public String getUsername() {
	    return username;    
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public long getId() {
		return this.id;
	}
	

}
