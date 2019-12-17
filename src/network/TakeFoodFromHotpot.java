package network;

import hotpot.GameStatus;

//00 Type UserID Length_Low Length_High HotpotRoomID HotpotFoodID 00
public class TakeFoodFromHotpot implements Packet {
	public static final byte ID = 2;
	private static final int packetLength = 8;
	private static final int indexUserID = 2;
	private static final int indexRoomID = 5;
	private static final int indexFoodID = 6;
	private static final byte[] packetTemplate = {0, ID, 0, 2, 0, 0, 0, 0};
	private byte[] packet;
	
	public static TakeFoodFromHotpot createPacket(byte[] packet, int offset, int maxLength)
	{
		if (!isPacketValid(packet, offset, maxLength))
			return null;
		TakeFoodFromHotpot obj = new TakeFoodFromHotpot();
		obj.packet = new byte[packetLength];
		for (int i = 0; i < packetLength; ++i)
			obj.packet[i] = packet[offset + i];
		return obj;
	}
	
	public static TakeFoodFromHotpot createPacket(byte sendID, int roomID, int foodID)
	{
		TakeFoodFromHotpot obj = new TakeFoodFromHotpot();
		obj.packet = new byte[packetLength];
		for (int i = 0; i < packetLength; ++i)
			obj.packet[i] = packetTemplate[i];
		obj.packet[indexUserID] = sendID;
		obj.packet[indexRoomID] = (byte)roomID;
		obj.packet[indexFoodID] = (byte)foodID;
		return obj;
	}
	public static boolean isPacketValid(byte[] packet, int offset, int maxLength) {
		int index = offset;
		
		if (index + packetLength > maxLength)
			return false;
		if (packet[index++] != 00)
			return false;
		if (packet[index++] != ID)
			return false;
		int userID = packet[index++];
		if (userID < 0 || userID >= GameStatus.playerMaxCount)
			return false;
		int length = packet[index++];
		length += packet[index++] * 256;
		if (length != 2)
			return false;
		return true;
	}
	
	@Override
	public boolean isValid(GameStatus status) {
		if (packet == null)
			return false;
		if (status == null)
			return true;
		if (!status.checkIsCooking(packet[indexRoomID]))
			return false;
		if (status.getHotpotItemID(packet[indexRoomID]) != packet[indexFoodID])
			return false;
		return true;
	}
	
	@Override
	public byte[] toArray()
	{
		return packet;
	}
	
	@Override
	public void doOperation(GameStatus status) {
		if (status == null)
			return;
		status.takeFoodAway(packet[indexRoomID], packet[indexUserID]);
	}

}
