package hotpot;

import static resource.GetResource.hotpotHeight;
import static resource.GetResource.hotpotWidth;

import java.awt.Point;
import java.awt.event.ActionListener;
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

import food.Beef;
import food.Chicken;
import food.Eggdump;
import food.Fish;
import food.Fishegg;
import food.Food;
import food.Lettuce;
import food.Mushroom;
import food.Pig;
import food.Ricecake;
import food.Veg;
import food.Wurst;
import network.AddFoodToHotpot;
import network.Packet;
import network.TakeFoodFromHotpot;
import ui.GameUI;
import ui.ResultUI;

import static resource.GetResource.hotpotHeight;

public class Hotpot {
	static ResultUI Rframe;
	Socket socket;
	InputStream inputStream;
	OutputStream outputStream;
	byte userID;
	JFrame frame;
	ArrayList<GameUI<JButton>> foodUIButtonList = new ArrayList<>();
	//Food food = foodInterfaceList.get(index)
	ArrayList<Food> foodInterfaceList = new ArrayList<>();
	@SuppressWarnings("unchecked")
	GameUI<JLabel> foodPlateButtonList[] = new GameUI[5];
	boolean isPlateHasFood[] = new boolean[5];
	int plateFoodCookTime[] = new int[5];
	@SuppressWarnings("unchecked")
	GameUI<JLabel> otherPlayerPart[] = new GameUI[GameStatus.playerMaxCount - 1];
	GameStatus status;
	int Width = 1280, Height = 720;

	@SuppressWarnings("unchecked")
	GameUI<JButton> hotpotButton[] = new GameUI[GameStatus.roomInHotpot];
	int hotpotFoodStatus[] = new int[GameStatus.roomInHotpot];
	GameResult result = new GameResult();
	double positionHotpot[][] = 
		{
			{  46 / hotpotWidth,  60 / hotpotHeight } ,
			{  73 / hotpotWidth,  34 / hotpotHeight } ,
			{ 108 / hotpotWidth,  29 / hotpotHeight } ,
			{ 139 / hotpotWidth,  35 / hotpotHeight } ,
			{ 169 / hotpotWidth,  60 / hotpotHeight } ,
			{  67 / hotpotWidth,  84 / hotpotHeight } ,
			{  98 / hotpotWidth,  89 / hotpotHeight } ,
			{ 139 / hotpotWidth,  77 / hotpotHeight } ,
			{  82 / hotpotWidth,  57 / hotpotHeight } ,
			{ 118 / hotpotWidth,  56 / hotpotHeight }
		};
	
	public static final Class<?> foodClass[] = 
	{
			Beef.class,
			Chicken.class,
			Fish.class,
			Pig.class,
			Lettuce.class,
			Veg.class,
			Mushroom.class,
			Fishegg.class,
			Eggdump.class,
			Ricecake.class,
			Wurst.class
	};
	GameUI<JPanel> allPanel;
	GameUI<JPanel> contentPane;
	
	ActionListener addFoodListener;
	public Hotpot() {
	}
	
	public Hotpot(String userName)
	{
		try
		{
			new GameServerHandler(12700);
			Thread.sleep(100);
			new HotpotBeginner(userName, "127.0.0.1");
			new HotpotUpdator(status);
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Hotpot(String userName, String serverAddress)
	{
		try
		{
			new GameServerHandler(12700);
			Thread.sleep(100);
			new HotpotBeginner(userName, serverAddress);
			new HotpotUpdator(status);
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	private ActionListener eatFoodListener = null;
	private ActionListener takeFoodListener = null;
	
	private int getEmptyPlate() 
	{
		for (int i = 0; i < isPlateHasFood.length; ++i)
			if (!isPlateHasFood[i])
				return i;
		return -1;
	}

	private void createTakeFoodButtonListener()
	{
		takeFoodListener = (e -> 
		{
			if (getEmptyPlate() < 0)
				return;	//No plate is empty
			JButton btn = (JButton)e.getSource();
			Integer roomID = (Integer)btn.getClientProperty("RoomID");
			Integer foodID = (Integer)btn.getClientProperty("FoodID");
			Packet packet = TakeFoodFromHotpot.createPacket(userID, roomID, foodID);
			try {
				outputStream.write(packet.toArray());
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		});
	}
	protected void createFoodButtonListener()
	{
		addFoodListener = (e -> 
		{
			int roomInHotpot = status.getFreeRoomInHotpot();
			if (roomInHotpot < 0)
				return;	//No place
			JButton btn = (JButton)e.getSource();
			Integer id = (Integer) btn.getClientProperty("ID");
			int foodID = id.intValue();
			try {
				outputStream.write(AddFoodToHotpot.createPacket(userID, roomInHotpot, foodID).toArray());
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	private void createEatFoodButtonListener()
	{
		eatFoodListener = (e -> 
		{
			JButton btn = (JButton)e.getSource();
			Integer roomID = (Integer)btn.getClientProperty("RoomID");
			Integer foodID = (Integer)btn.getClientProperty("FoodID");
			Integer cookedTime = (Integer)btn.getClientProperty("CookedTime");
			GameUI<JButton> btnObj = (GameUI<JButton>)btn.getClientProperty("UIObject");
			isPlateHasFood[roomID.intValue()] = false;
			btnObj.dispose();
			Food food = foodInterfaceList.get(foodID);
			int status = 0;
			while (food.getTime(status) < cookedTime)
				status++;
			result.setInfo(food.getCal(),food.getPrice(),food.getName(),status);
		});
	}
	
	public void addNewPlayer(int userID, String name)
	{
		int index = userID;
		if (userID == this.userID)
			return;
		else if (userID > this.userID)
			index = userID - 1;
		
		otherPlayerPart[index]
			.setText(name)
			.setVisible(true);
	}
	
	public void addFoodIntoHotpot(int roomID, int foodID)
	{
		if (takeFoodListener == null)
			createTakeFoodButtonListener();
		Food foodObj = foodInterfaceList.get(foodID);
		GameUI<JButton> foodBtn = foodUIButtonList.get(foodID);
		Point btnLocation = foodBtn.getObject().getLocationOnScreen();
		GameUI<JButton> btn = GameUI
			.createButton(foodObj.getIconInHotpot(0))
			.setBounds(btnLocation.x, btnLocation.y, 100, 100)
			.setTransparent()
			.setTag("RoomID", roomID)
			.setTag("FoodID", foodID)
			.addActionListener(takeFoodListener);
		hotpotButton[roomID] = btn;
		hotpotFoodStatus[roomID] = 0;
		allPanel.add(btn, 0)
			.refreshUI();
		btn.moveTo((int)positionHotpot[roomID][0], (int)positionHotpot[roomID][1], 300, false);
	}

	public void foodTakeAwayFromHotpot(int roomID, int foodID, int targetUserID, int cookedTime)
	{
		int index = targetUserID;
		if (targetUserID != userID)
		{
			if (targetUserID > userID)
				index = targetUserID - 1;
			hotpotButton[roomID].moveTo(otherPlayerPart[index], 300, true);
			hotpotButton[roomID] = null;
			return;
		}
		int emptyPlateID = getEmptyPlate();
		if (emptyPlateID < 0)
		{
			hotpotButton[roomID].moveTo(otherPlayerPart[0], 300, true);
			hotpotButton[roomID] = null;
			return;
		}
		
		if (eatFoodListener == null)
			createEatFoodButtonListener();
		GameUI<JButton> btnEatFood = GameUI
			.createButton((ImageIcon)(hotpotButton[roomID].getObject().getIcon()))
			.addActionListener(eatFoodListener)
			.setBounds(0, 0, 100, 100)
			.setTransparent()
			.setTag("RoomID", emptyPlateID)
			.setTag("FoodID", foodID)
			.setTag("CookedTime", cookedTime);
		
		allPanel.add(btnEatFood, 0);
		
		isPlateHasFood[emptyPlateID] = true;
		plateFoodCookTime[emptyPlateID] = cookedTime;
		hotpotButton[roomID].moveTo(foodPlateButtonList[emptyPlateID], 300, true);
		hotpotButton[roomID] = null;

		btnEatFood
			.setTag("UIObject", btnEatFood)
			.moveTo(foodPlateButtonList[emptyPlateID], 1, false);
	}
}
