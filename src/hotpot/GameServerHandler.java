package hotpot;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import network.AddFoodToHotpot;
import network.Packet;
import network.TakeFoodFromHotpot;
import network.TellNamePacket;

public class GameServerHandler {
	private Socket[] socketList = new Socket[GameStatus.playerMaxCount];
	private InputStream[] inputStream = new InputStream[GameStatus.playerMaxCount];
	private BufferedOutputStream[] outputStream = new BufferedOutputStream[GameStatus.playerMaxCount];
	private ServerSocket socketServer;
	private GameStatus status = new GameStatus(null);
	
	public GameServerHandler(int port) throws Exception
	{
		socketServer = new ServerSocket(port);
		socketServer.setSoTimeout(1500);
	    new Thread(() -> {
	    	while (true)
	    	{
	    		try
	    		{
			    	if (listCount() >= socketList.length)
			    	{
			    		Thread.sleep(1000);
			    		continue;
			    	}
			    	Socket sock = socketServer.accept();
			    	insertSocket(sock);
	    		}catch (Exception e)
	    		{
	    			e.printStackTrace();
	    		}
	    	}
	    }).start();
	}

	private boolean checkClientConnection(int index)
	{
		if (index >= socketList.length || index < 0)
			return false;
		return socketList[index] != null && socketList[index].isConnected();
	}
	
	private int listCount()
	{
		int counter = 0;
		for (int idx = 0; idx < socketList.length; ++idx)
			if (checkClientConnection(idx))
				counter++;
			else
				socketList[idx] = null;
		return counter;
	}
	
	private void insertSocket(Socket sock) throws Exception
	{
		int idx = 0;
		if (listCount() >= socketList.length)
			return;
		while (checkClientConnection(idx) && idx < socketList.length)
			idx++;
		if (idx >= socketList.length)
			return;
		socketList[idx] = sock;
		inputStream[idx] = sock.getInputStream();
		outputStream[idx] = new BufferedOutputStream(sock.getOutputStream());
		startReceive(new Integer(idx));
	}
	
	private void sendPacketToTarget(int targetID, Packet packet)
	{
		if (!checkClientConnection(targetID))
			return;
		try {
			outputStream[targetID].write(packet.toArray(), 0, packet.toArray().length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sendPacketToOther(int senderID, Packet packet)
	{
		if (packet == null)
			return;
		for (int idx = 0; idx < socketList.length; ++idx)
			if (senderID == idx)
				continue;
			else if (checkClientConnection(idx))
				sendPacketToTarget(idx, packet);
	}
	
	private void parsePacket(int userID, byte[] packet, int size)
	{
		int index = 0;

		while (index < size - 5)
		{
			int offset = index;
			if (packet[index++] != 0)	//Read 00
				continue;
			int type = packet[index++];	//Read Type
			if (packet[index++] != (byte)userID)	//Read UserID
				continue;
			Packet p;
			switch (type)
			{
				case AddFoodToHotpot.ID:
					p = AddFoodToHotpot.createPacket(packet, offset, size);
					break;
				case TakeFoodFromHotpot.ID:
					p = TakeFoodFromHotpot.createPacket(packet, offset, size);
					break;
				case TellNamePacket.ID:
					p = TellNamePacket.createPacket(packet, offset, size);
					break;
				default:
					p = null;
					break;
			}
			if (p == null)
				continue;
			if (!p.isValid(status))
				continue;
			p.doOperation(status);
			sendPacketToOther(-1, p);
			index = offset + p.toArray().length;
		}
	}
	
	private void startReceive(Integer index)
	{
		new Thread(() -> {
			int idx = index.intValue();
			byte[] tempArray = new byte[1024];
			
			try
			{
				outputStream[idx].write(idx);
				
				for (int j = 0; j < socketList.length; ++j)
				{
					//Sync Others Name
					if (idx == j)
						continue;
					if (!checkClientConnection(j))
						continue;
					sendPacketToTarget(idx, TellNamePacket.createPacket(j, status.getPlayerName(j)));
				}
				
				while (checkClientConnection(idx))
				{
					outputStream[idx].flush();
					if (inputStream[idx].available() > 0)
					{
						int readSize = inputStream[idx].read(tempArray);
						if (readSize > 0)
							parsePacket(idx, tempArray, readSize);
					}
					Thread.sleep(1);
				}
				
			}catch (Exception e)
			{
				e.printStackTrace();
			}
		}).start();
	}
	
}
