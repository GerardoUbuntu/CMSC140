package network;

public class Packet02Move extends Packet {

	public String username;
	private float x,y;
	
	private boolean isMoving;
	private int move;
	public long id;
	
	public Packet02Move(byte[] data ) {
		super(02);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.x = Float.parseFloat(dataArray[1]);
		this.y = Float.parseFloat(dataArray[2]);
		this.isMoving = Integer.parseInt(dataArray[3]) == 1;
		this.move = Integer.parseInt(dataArray[4]);
		this.id = Long.parseLong(dataArray[5]);
	
	}
	
	public Packet02Move(String username, float x, float y, boolean isMoving, int move, long id ) {
		super(02);
		this.username = username;
		this.x = x;
		this.y = y;
		this.isMoving = isMoving;
		this.move = move;
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
		return ("02"+ this.username + "," + this.x + "," + this.y + "," + (isMoving?1:0)  + "," + this.move + "," + this.id).getBytes();
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

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public int getMove() {
		return move;
	}

	public void setMove(int move) {
		this.move = move;
	}
	
	public long getId() {
		return this.id;
	}

}
