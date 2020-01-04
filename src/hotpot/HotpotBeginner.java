package hotpot;

import static resource.GetResource.hotpotHeight;
import static resource.GetResource.hotpotWidth;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
import network.TellNamePacket;
import resource.GetResource;
import ui.GameUI;
import ui.ResultUI;

public class HotpotBeginner extends Hotpot{
	public HotpotBeginner(String userName,String serverAddress) throws Exception {
		this
			.connectSocket(serverAddress)
			.getUserID()
			.sendName(userName)
			.createUI();
	}

	public HotpotBeginner connectSocket(String IP) throws UnknownHostException, IOException
	{
		socket = new Socket(IP, 12700);
		inputStream = socket.getInputStream();
		outputStream = socket.getOutputStream();
		return this;
	}
	
	private HotpotBeginner getUserID() throws Exception
	{
		byte[] tempByte = new byte[1];
		inputStream.read(tempByte);
		userID = tempByte[0];
		return this;
	}
	
	private HotpotBeginner sendName(String userName) throws Exception
	{
		outputStream.write(TellNamePacket.createPacket(userID, userName).toArray());
		return this;
	}
	
	private HotpotBeginner createUI() throws Exception
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
	
	
	
}
