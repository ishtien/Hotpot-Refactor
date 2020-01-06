package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import resource.GetResource;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

@SuppressWarnings("serial")
public class ResultUI extends JFrame {
//	private JPanel contentPane;
	@SuppressWarnings("unchecked")
	private GameUI<JLabel> rankListImg[] = new GameUI[3];
	private GameUI<JLabel> rankListString[] = new GameUI[3];
	private GameUI<JPanel> contentPane;
	private GameUI<JPanel> backgroundPane;
	private GameUI<JLabel> nickName;
	private GameUI<JLabel> calInfo;
	private GameUI<JLabel> priceInfo;
	private GameUI<JLabel> hungerIcon;

	public ResultUI() {}
	public ResultUI(int cal,int price, String nickname, String rankImg1,String rankImg2,String rankImg3) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = GameUI.createPanel()
				.setBorder(new EmptyBorder(5, 5, 5, 5))
				.setBounds(100, 100, 800, 600)
				.setLayout(new BorderLayout(0, 0))
				.setLayout(null);
		setContentPane(contentPane.getObject());

		this.showBackground();
		this.showPlayerNickname(nickname);
		this.showCalAndPrice(cal, price);		
		this.showHungerState(cal);
		this.showHungerFoodImg(rankImg1,rankImg2,rankImg3);
		this.showHungerFoodTitle();
	}
	private void showBackground() {
		backgroundPane = GameUI.createPanel()
				.setBackground(new Color(254,244,173))
				.setBounds(0, 0, 800, 600)
				.setLayout(null);
		contentPane.add(backgroundPane, BorderLayout.CENTER);
	}
	private void showPlayerNickname(String nickname) {
		nickName = GameUI.createLabelString(nickname)
				.setFont("微軟正黑體")
				.setBounds(302, 34, 329, 85);
		
		backgroundPane.add(nickName);
		
	}
	private void showCalAndPrice(int cal,int price) {
		calInfo = GameUI.createLabelString("你今天總共吃了"+cal+"Kcal的食物")
				.setFont("新細明體")
				.setBounds(100, 187, 388, 54);

		backgroundPane.add(calInfo);
		
		priceInfo = GameUI.createLabelString("你花了"+price+"元吃了這一餐")
				.setFont("新細明體")
				.setBounds(100, 249, 272, 43);

		backgroundPane.add(priceInfo);
	}
	
	private void showHungerState(int cal) {
		if(cal >=1500) {
			hungerIcon = GameUI.createLabel(GetResource.createImage("good.png", 312,234));
		} else if(cal<1500 && cal>=800) {
			hungerIcon = GameUI.createLabel(GetResource.createImage("ok.png", 312,234));
		}else {
			hungerIcon = GameUI.createLabel(GetResource.createImage("bad.png", 312,234));
		}
		
		hungerIcon.setBounds(400, 140, 312,234);
		backgroundPane.add(hungerIcon);
	}
	
	private void showHungerFoodImg(String rankImg1,String rankImg2,String rankImg3) {
		rankListImg[0] = GameUI.createLabel(GetResource.createImage(rankImg1, 50,50))
				.setBounds(70, 430, 50, 50);
		rankListImg[1] = GameUI.createLabel(GetResource.createImage(rankImg2, 50,50))
				.setBounds(320, 430, 50, 50);
		rankListImg[2] = GameUI.createLabel(GetResource.createImage(rankImg3, 50,50))
				.setBounds(570, 430, 50, 50);
		backgroundPane.add(rankListImg[0]);
		backgroundPane.add(rankListImg[1]);
		backgroundPane.add(rankListImg[2]);
	}
	
	private void showHungerFoodTitle() {
		rankListString[0] = GameUI.createLabelString("吃最多的生食!")
				.setBounds(60, 385, 100, 50);
		rankListString[1] = GameUI.createLabelString("吃最多將將好的食物！")
				.setBounds(310, 385, 100, 50);
		rankListString[2] = GameUI.createLabelString("吃這些會生病吧！")
				.setBounds(560, 385, 100, 50);
		backgroundPane.add(rankListString[0]);
		backgroundPane.add(rankListString[1]);
		backgroundPane.add(rankListString[2]);
	}

}
