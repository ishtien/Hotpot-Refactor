package hotpot;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import resource.GetResource;
import ui.StartGameUI;

public class Main {
	
	public static void main(String[] args) {
		LanDeviceSearch lanDeviceSearch;
		
		try
		{
			lanDeviceSearch = new LanDeviceSearch(9999);
		}catch (Exception e)
		{
			System.out.println("Fail to register udp listener!");
			e.printStackTrace();
			return;	//Close application
		}
		
		//Create UI
		StartGameUI startGameUI = new StartGameUI(lanDeviceSearch);
	}

}
