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
	private JFrame frame = new JFrame();
	private GameUI<JPanel> startPane;
	private GameUI<JLabel> nameTitle;
	private GameUI<JLabel> background;
	private GameUI<JTextField> textTitle;
	private GameUI<JButton> searchButton;

	public StartGameUI(LanDeviceSearch lanDeviceSearch) {
		// TODO Auto-generated constructor stub
		frame.setSize(410, 430); 
		frame.setTitle("Config");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		startPane = GameUI.createPanel()
			.setBounds(0, 0, 400, 300)
			.setLayout(null)
			.setBorder(null);

		nameTitle = GameUI.createLabelString("Your Name")
				.setBounds(0, 300, 400, 20);
		
		textTitle = GameUI.createTextField("")
				.setBounds(0, 320, 400, 20)
				.setToolTipText("Enter Your Name Here");
		
		searchButton = GameUI.createButtonString("Search other outsider")
				.setBounds(0, 350, 400, 25);
		
		background = GameUI.createLabel(GetResource.createImage("start.png", 400,300))
				.setBounds(0, 0, 400, 300)
				.setBorder(null);
		
		startPane.add(background);
		
		searchButton.addActionListener(e -> 
		{
			searchButton.getObject().setEnabled(false);
			if (isStartScanning)
				return;
			if (textTitle.getObject().getText().length() <= 0)
			{
				JOptionPane.showConfirmDialog(
						null,
						"You have to enter your name",
						"Name is empty",
						JOptionPane.PLAIN_MESSAGE,
						JOptionPane.QUESTION_MESSAGE,
						null
						);
				searchButton.getObject().setEnabled(true);
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
				hotpot = new Hotpot(textTitle.getObject().getText().toString(), lanDeviceSearch.popServerIP());
			else
			{
				hotpot = new Hotpot(textTitle.getObject().getText().toString());
				lanDeviceSearch.autoBroadcast(new byte[]{0}, new Integer(100));
			}
			
		});
		
		frame.add(startPane.getObject());
		startPane.add(nameTitle);
		startPane.add(textTitle);
		startPane.add(searchButton);
		frame.setVisible(true);
	}

}
