package hotpot;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import food.*;

import network.AddFoodToHotpot;
import network.Packet;
import network.TakeFoodFromHotpot;
import network.TellNamePacket;
import resource.GetResource;
import ui.GameUI;
import ui.ResultUI;

import static resource.GetResource.hotpotWidth;
import static resource.GetResource.hotpotHeight;

public class HotpotUpdator extends Hotpot{
	GameStatus STATUS = new GameStatus(this);
	public HotpotUpdator(GameStatus status) {
		STATUS = status;
		this
		.createReceivePacketThread()
		.updateTimer();
	}
		
	private HotpotUpdator createReceivePacketThread()
	{
		new Thread(()-> {
			byte tempArray[] = new byte[1024];
			while (true)
			{
				try {
					int readSize = inputStream.read(tempArray);
					if (readSize <= 0)
						continue;
					
					int index = 0;

					while (index < readSize - 5)
					{
						int offset = index;
						if (tempArray[index++] != 0)	//Read 00
							continue;
						int type = tempArray[index++];	//Read Type
						int senderUserID = tempArray[index++];
						if (senderUserID < 0 || senderUserID >= GameStatus.playerMaxCount)
							continue;
						Packet p;
						switch (type)
						{
							case AddFoodToHotpot.ID:
								p = AddFoodToHotpot.createPacket(tempArray, offset, readSize);
								break;
							case TakeFoodFromHotpot.ID:
								p = TakeFoodFromHotpot.createPacket(tempArray, offset, readSize);
								break;
							case TellNamePacket.ID:
								p = TellNamePacket.createPacket(tempArray, offset, readSize);
								break;
							default:
								p = null;
								break;
						}
						if (p == null)
							continue;
						p.doOperation(status);
						index = offset + p.toArray().length;
					}
				} catch (IOException e) {
					e.printStackTrace();
			    	Rframe = new ResultUI(result.getSumCal(),result.getSumPrice(),result.getNickname(),result.getFoodMost(0),result.getFoodMost(1),result.getFoodMost(2));
			    	Rframe.setLocation(50, 50);
			        Rframe.setVisible(true);
			        frame.setVisible(false);
				}
			}
		}).start();
		return this;
	}

	private HotpotUpdator updateTimer()
	{
		new Thread(() -> {
			while (true)
			{
				for (int i = 0; i < GameStatus.roomInHotpot; ++i)
				{
					long cookedTime = status.getHotpotItemCookTime(i);					
					int foodID = status.getHotpotItemID(i);
					if (cookedTime < 0 || foodID < 0)
						continue;
					int foodStatus = 0;
					for (foodStatus = 0; foodStatus < 3; foodStatus++)
						if (foodInterfaceList.get(foodID).getTime(foodStatus) > cookedTime)
							break;
					if (foodStatus == hotpotFoodStatus[i])
						continue;
					hotpotFoodStatus[i] = foodStatus;
					hotpotButton[i].setIcon(foodInterfaceList.get(foodID).getIconInHotpot(foodStatus));
				}
				try
				{
					Thread.sleep(100);
				}catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}).start();
		return this;
	}
	
}
