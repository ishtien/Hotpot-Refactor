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
	private JPanel contentPane;

	public ResultUI() {}
	public ResultUI(int cal,int price, String nickname, String rankImg1,String rankImg2,String rankImg3) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(254,244,173));
		panel_1.setBounds(0, 0, 800, 600);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel Nickname = new JLabel(nickname);
		Nickname.setFont(new Font("微軟正黑體", Font.PLAIN, 48));
		Nickname.setBounds(302, 34, 329, 85);
		panel_1.add(Nickname);
		
		JLabel calTitle = new JLabel("你今天總共吃了"+cal+"Kcal的食物");
		calTitle.setFont(new Font("新細明體", Font.PLAIN, 20));
		calTitle.setBounds(100, 187, 388, 54);
		panel_1.add(calTitle);
		
		JLabel PriceTitle = new JLabel("你花了"+price+"元吃了這一餐");
		PriceTitle.setFont(new Font("新細明體", Font.PLAIN, 20));
		PriceTitle.setBounds(100, 249, 272, 43);
		panel_1.add(PriceTitle);
		
		
		ImageIcon hungerIcon = null;
		if(cal>=1500) {
			hungerIcon=GetResource.createImage("good.png", 312,234);
		}else if(cal<1500 && cal>=800) {
			hungerIcon=GetResource.createImage("ok.png", 312,234);
		}else {			
			hungerIcon=GetResource.createImage("bad.png", 312,234);
		}
		
		JLabel hunger = new JLabel("hunger");
		hunger.setIcon(hungerIcon);
		hunger.setBounds(400, 140, 310, 235);
		panel_1.add(hunger);
		
		JLabel rank1 = new JLabel("吃最多的生食!");
		ImageIcon rankIcon1=GetResource.createImage(rankImg1, 50,50);
		rank1.setIcon(rankIcon1);
		rank1.setBounds(60, 385, 200, 165);
		panel_1.add(rank1);
		
		JLabel rank2 = new JLabel("吃最多將將好的食物！");
		ImageIcon rankIcon2=GetResource.createImage(rankImg2, 50,50);
		rank2.setIcon(rankIcon2);
		rank2.setBounds(310, 385, 200, 165);
		panel_1.add(rank2);
		
		JLabel rank3 = new JLabel("吃這些會生病吧！");
		ImageIcon rankIcon3=GetResource.createImage(rankImg3, 50,50);
		rank3.setIcon(rankIcon3);
		rank3.setBounds(560, 385, 200, 165);
		panel_1.add(rank3);
	}

}
