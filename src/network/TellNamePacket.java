package network;

import hotpot.GameStatus;

public class TellNamePacket implements Packet {
	public static final byte ID = 0;
	private static final int indexUserID = 2;
	private static final int indexLengthLow = 3;
	private static final int indexLengthHigh = 4;
	//00 Type UserID Length_Low Length_High StrBytes 00

	private static final byte[] packetTemplate = {0, ID, 0, 0, 0};
	private byte[] packet;
	private String playerName;
	
	public static TellNamePacket createPacket(int userID, String name)
	{
		TellNamePacket obj = new TellNamePacket();
		byte[] strArray = name.getBytes();
		obj.playerName = new String(name);
		obj.packet = new byte[strArray.length + packetTemplate.length + 1];
		for (int i = 0; i < packetTemplate.length; ++i)
			obj.packet[i] = packetTemplate[i];
		obj.packet[indexUserID] = (byte)userID;
		obj.packet[indexLengthLow] = (byte)(strArray.length % 256);
		obj.packet[indexLengthHigh] = (byte)(strArray.length / 256);
		for (int i = 0; i < strArray.length; ++i)
			obj.packet[packetTemplate.length + i] = strArray[i];
		obj.packet[strArray.length + packetTemplate.length] = 0;
		return obj;
	}
	
	public static TellNamePacket createPacket(byte[] packet, int offset, int maxLength)
	{
		int index = offset;
		
		if (index + packetTemplate.length + 1 > maxLength)
			return null;

		if (packet[index] != 0 || packet[index + 1] != ID)
			return null;
		int userID = packet[index + 2];
		int stringLength = packet[index + 3] + packet[index + 4] * 256;
		try
		{
			String s = new String(packet, index + 5, stringLength);
			return createPacket(userID, s);
		}catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean isValid(GameStatus status) {
		return (playerName != null && packet != null);
	}

	@Override
	public void doOperation(GameStatus status) {
		if (status == null || packet == null)
			return;
		status.addPlayerName(packet[indexUserID], playerName);
	}

	@Override
	public byte[] toArray() {
		return packet;
	}

}
