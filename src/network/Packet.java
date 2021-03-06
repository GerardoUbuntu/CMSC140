package network;

public abstract class Packet {
 
	public static enum PacketTypes  {
		INVALID(-1), LOGIN(00), DISCONNECT(01), MOVE(02), GETID(03), START(04), DEAD(05), WIN(06),
		Letter(07), PICK(10), QUIT(11), TOUCH(12), SLOW(13);
		
		private int packetId;
		private PacketTypes(int packetId) {
			this.packetId = packetId;
		}
		
		public int getId() {
			return packetId;
		}
	}
	
	public byte packetId;
	
	public Packet(int packetId) {
		this.packetId = (byte) packetId;
	}
	
	public abstract void writeData(Client client);
	public abstract void writeData(Server server);
	
	
	public String readData(byte[] data) {
		String message = new String(data).trim();
		return message.substring(2);
	}
	
	public abstract byte[] getData();
	
	public static PacketTypes lookupPacket(String packetId) {
		try {
			return lookupPacket(Integer.parseInt(packetId));
		}catch(NumberFormatException e) {
			return PacketTypes.INVALID;
		}
	}
	public static PacketTypes lookupPacket(int id) {
		for(PacketTypes p :  PacketTypes.values()) {
			if(p.getId() == id)
				return p;
		}
		return PacketTypes.INVALID;
	}
	
	
  
}
