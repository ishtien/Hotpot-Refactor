package ui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hotpot.Hotpot;
import hotpot.LanDeviceSearch;
import resource.GetResource;

public class StartGameUI {
	Hotpot hotpot;
	boolean isStartScanning = false;

	public StartGameUI(LanDeviceSearch lanDeviceSearch) {
		// TODO Auto-generated constructor stub
		JFrame frame = new JFrame();
		frame.setSize(400, 400); 
		frame.setTitle("Config");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 400, 300);
		panel.setLayout(null);
		panel.setBorder(null);
		
		JLabel labelTitle = new JLabel("Your Name");
		labelTitle.setBounds(0, 300, 400, 20);
		JTextField textTitle = new JTextField("");
		textTitle.setBounds(0, 320, 400, 20);
		JButton button = new JButton("Search other outsider");
		button.setBounds(0, 350, 400, 25);
		textTitle.setToolTipText("Enter Your Name Here");
		
		JLabel background = new JLabel("");
		ImageIcon backIcon=GetResource.createImage("start.png", 400,300);
		background.setIcon(backIcon);
		background.setBounds(0, 0, 400, 300);
		background.setBorder(null);
		panel.add(background);
		
		button.addActionListener(e -> 
		{
			button.setEnabled(false);
			if (isStartScanning)
				return;
			if (textTitle.getText().length() <= 0)
			{
				JOptionPane.showConfirmDialog(
						null,
						"You have to enter your name",
						"Name is empty",
						JOptionPane.PLAIN_MESSAGE,
						JOptionPane.QUESTION_MESSAGE,
						null
						);
				button.setEnabled(true);
				return;
			}
			isStartScanning = true;
			lanDeviceSearch.startReceiveUdp();
			try { Thread.sleep(3000); } catch (Exception ee) {}
			lanDeviceSearch.stopReceiveUdp();
			if (hotpot != null)
				return;
			frame.setVisible(false);
			if (lanDeviceSearch.isReceiveUdp())
				hotpot = new Hotpot(textTitle.getText().toString(), lanDeviceSearch.popServerIP());
			else
			{
				hotpot = new Hotpot(textTitle.getText().toString());
				lanDeviceSearch.autoBroadcast(new byte[]{0}, new Integer(100));
			}
			
		});
		
		frame.add(panel);
		panel.add(labelTitle);
		panel.add(textTitle);
		panel.add(button);
		frame.setVisible(true);
	}

}
