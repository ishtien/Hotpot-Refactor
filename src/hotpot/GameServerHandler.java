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
		for (int i = 0; i < socketList.length; ++i)
			if (checkClientConnection(i))
				counter++;
			else
				socketList[i] = null;
		return counter;
	}
	
	private void insertSocket(Socket sock) throws Exception
	{
		int i = 0;
		if (listCount() >= socketList.length)
			return;
		while (checkClientConnection(i) && i < socketList.length)
			i++;
		if (i >= socketList.length)
			return;
		socketList[i] = sock;
		inputStream[i] = sock.getInputStream();
		outputStream[i] = new BufferedOutputStream(sock.getOutputStream());
		startReceive(new Integer(i));
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
		for (int j = 0; j < socketList.length; ++j)
			if (senderID == j)
				continue;
			else if (checkClientConnection(j))
				sendPacketToTarget(j, packet);
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
			int i = index.intValue();
			byte[] tempArray = new byte[1024];
			
			try
			{
				outputStream[i].write(i);
				
				for (int j = 0; j < socketList.length; ++j)
				{
					//Sync Others Name
					if (i == j)
						continue;
					if (!checkClientConnection(j))
						continue;
					sendPacketToTarget(i, TellNamePacket.createPacket(j, status.getPlayerName(j)));
				}
				
				while (checkClientConnection(i))
				{
					outputStream[i].flush();
					if (inputStream[i].available() > 0)
					{
						int readSize = inputStream[i].read(tempArray);
						if (readSize > 0)
							parsePacket(i, tempArray, readSize);
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
