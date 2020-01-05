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

public class Hotpot {
	static ResultUI Rframe;
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
	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private byte userID;
	private ArrayList<GameUI<JButton>> foodUIButtonList = new ArrayList<>();
	//Food food = foodInterfaceList.get(index)
	private ArrayList<Food> foodInterfaceList = new ArrayList<>();
	private JFrame frame;
	@SuppressWarnings("unchecked")
	private GameUI<JLabel> foodPlateButtonList[] = new GameUI[5];
	@SuppressWarnings("unchecked")
	private GameUI<JLabel> otherPlayerPart[] = new GameUI[GameStatus.playerMaxCount - 1];
	@SuppressWarnings("unchecked")
	private GameUI<JButton> hotpotButton[] = new GameUI[GameStatus.roomInHotpot];
	private GameUI<JPanel> allPanel;
	private GameUI<JPanel> contentPane;
	private GameStatus status = new GameStatus(this);
	private GameResult result = new GameResult();
	private boolean isPlateHasFood[] = new boolean[5];
	private int hotpotFoodStatus[] = new int[GameStatus.roomInHotpot];
	private int plateFoodCookTime[] = new int[5];
	private int Width = 1280, Height = 720;	
	private double positionHotpot[][] = 
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

	

	public Hotpot(String userName)
	{
		try
		{
			new GameServerHandler(12700);
			Thread.sleep(100);
			this
				.connectSocket("127.0.0.1")
				.getUserID()
				.sendName(userName)
				.createUI()
				.createReceivePacketThread()
				.updateTimer();
		}catch (Exception e)
		{
			
		}
	}
	
	public Hotpot(String userName, String serverAddress)
	{
		try
		{
			this
				.connectSocket(serverAddress)
				.getUserID()
				.sendName(userName)
				.createUI()
				.createReceivePacketThread()
				.updateTimer();
		}catch (Exception e)
		{
			
		}
	}
	
	private Hotpot connectSocket(String IP) throws Exception
	{
		socket = new Socket(IP, 12700);
		inputStream = socket.getInputStream();
		outputStream = socket.getOutputStream();
		return this;
	}
	
	private Hotpot getUserID() throws Exception
	{
		byte[] tempByte = new byte[1];
		inputStream.read(tempByte);
		userID = tempByte[0];
		return this;
	}
	
	private Hotpot sendName(String userName) throws Exception
	{
		outputStream.write(TellNamePacket.createPacket(userID, userName).toArray());
		return this;
	}

	private Hotpot createUI() throws Exception
	{
		int Width_1_10 = Width/10, Height_1_10 = Height / 10;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setBounds(0, 0, Width, Height);
		frame.addWindowListener(new ExitAdapter(frame, result, Rframe));
		
		//Left Upper Part
		GameUI<JPanel> panel_LeftUp = GameUI.createPanel()
			.setBackground(new Color(255, 228, 181))
			.setBounds(0, 0, Width_1_10 * 8, Height_1_10 * 7)
			.setLayout(null);
		int otherPlayerLabelWidth = Width_1_10 * 8 / (otherPlayerPart.length);
		for (int i = 0; i < otherPlayerPart.length; ++i)
		{
			otherPlayerPart[i] = GameUI
					.createLabel(null)
					.setText("")
					.setVisible(false)
					.setBounds(otherPlayerLabelWidth * i, 0, otherPlayerLabelWidth, 48);
			panel_LeftUp.add(otherPlayerPart[i]);
		}
		int PotImageSize = (int)Math.min(Width_1_10 * 8 * 0.5, Height_1_10 * 7 * 0.9);
		int PotWidthDiff = Width_1_10 * 8 - PotImageSize * 2, PotHeightDiff = Height_1_10 * 7 - PotImageSize;
		ImageIcon hotpotIcon = GetResource.createImage("milk_pot.png", PotImageSize * 2, PotImageSize); 
		for (int i = 0; i < positionHotpot.length; i++)
		{
			positionHotpot[i][0] = positionHotpot[i][0] * PotImageSize * 2 + PotWidthDiff / 2;
			positionHotpot[i][1] = positionHotpot[i][1] * PotImageSize * 1 + PotHeightDiff / 2;
		}
		GameUI<JLabel> hotpotLabel = GameUI.createLabel(hotpotIcon)
				.setBounds(PotWidthDiff / 2, PotHeightDiff / 2, PotImageSize * 2, PotImageSize);
		panel_LeftUp.add(hotpotLabel);
		
		//Left Below Part
		GameUI<JPanel> panel_LeftDown = GameUI.createPanel()
			.setBackground(new Color(255, 160, 122))
			.setBounds(0, Height_1_10 * 7, Width_1_10 * 8, Height - Height_1_10 * 7)
			.setLayout(null);
		
		int plateButtonHeight = (Height - Height_1_10 * 7);
		int plateButtonWidth = Width_1_10 * 8 / 5;
		ImageIcon dishIcon = GetResource.createImage("dish.png", plateButtonWidth, plateButtonHeight);
		for (int i = 0; i < foodPlateButtonList.length; ++i)
		{
			foodPlateButtonList[i] = 
					GameUI.createLabel(dishIcon)
					.setBounds(plateButtonWidth * i, 0, plateButtonWidth, plateButtonHeight)
					.setTransparent();
			panel_LeftDown.add(foodPlateButtonList[i]);
		}
		
		//Right Part
		GameUI<JPanel> panelRight = GameUI.createPanel()
				.setBackground(new Color(255, 69, 0))
				.setBounds(Width_1_10 * 8, 0, Width - Width_1_10 * 8, Height);
		
		//Create Right-Part Button & Listener
		createFoodButtonListener();
		int buttonSize = (int)((Width - Width_1_10 * 8) * 0.65) / 2;
		for (int i = 0; i < foodClass.length; ++i)
		{
			Food food = (Food)foodClass[i].newInstance();
			GameUI<JButton> button = 
					GameUI.createButton(GetResource.resizeImage(food.getIconInUI(), buttonSize, buttonSize))
					.setTag("ID", new Integer(i))
					.setTransparent()
					.setToolTipText(food.getName())
					.addActionListener(addFoodListener);
			foodUIButtonList.add(button);
			panelRight.add(button);
			foodInterfaceList.add(food);
		}
		
		allPanel = GameUI.createPanel()
				.setBounds(0, 0, Width, Height)
				.setLayout(null)
				.add(panel_LeftUp)
				.add(panel_LeftDown)
				.add(panelRight);
		
		contentPane = GameUI.createPanel()
			.setBackground(new Color(255, 255, 255))
			.setBorder(new EmptyBorder(5, 5, 5, 5))
			.setLayout(null)
			.add(allPanel);

		frame.setContentPane(contentPane.getObject());
		frame.setVisible(true);
		frame.setBounds(0, 0, Width + frame.getInsets().left + frame.getInsets().right, Height + frame.getInsets().top + frame.getInsets().bottom);
		return this;
	}
	
	private Hotpot createReceivePacketThread()
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
		
	private Hotpot updateTimer()
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
					
				}
			}
		}).start();
		return this;
	}

	private ActionListener eatFoodListener = null;
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
	
	private ActionListener takeFoodListener = null;
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

	private ActionListener addFoodListener;
	private void createFoodButtonListener()
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
	
	private int getEmptyPlate() 
	{
		for (int i = 0; i < isPlateHasFood.length; ++i)
			if (!isPlateHasFood[i])
				return i;
		return -1;
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
}