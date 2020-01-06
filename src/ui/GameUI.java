package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GameUI<T extends JComponent> {
	private T compoent;	
	public GameUI(T obj)
	{
		compoent = obj;
	}
	
	public T getObject() 
	{
		return compoent;
	}
	
	public static GameUI<JButton> createButton(ImageIcon image)
	{
		return new GameUI<JButton>(new JButton(image)); 
	}
	
	public static GameUI<JButton> createButtonString(String string)
	{
		return new GameUI<JButton>(new JButton(string)); 
	}
	
	public static GameUI<JTextField> createTextField(String string)
	{
		return new GameUI<JTextField>(new JTextField(string)); 
	}
	
	public static GameUI<JPanel> createPanel()
	{
		return new GameUI<>(new JPanel());
	}
	
	public static GameUI<JLabel> createLabel(ImageIcon image)
	{
		return new GameUI<JLabel>(new JLabel(image)); 
	}
	
	public static GameUI<JLabel> createLabelString(String string)
	{
		return new GameUI<JLabel>(new JLabel(string)); 
	}

	public GameUI<T> setBorder(EmptyBorder border)
	{
		compoent.setBorder(border);
		return this;
	}
	
	public GameUI<T> setBounds(int x, int y, int width, int height)
	{
		compoent.setBounds(x, y, width, height);
		return this;
	}
	
	public GameUI<T> addActionListener(ActionListener listener)
	{
		try
		{
			if (compoent.getClass() == JButton.class)
				((JButton)compoent).addActionListener(listener);
			else
				throw new Exception("Not support type!");
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return this;
	}
	
	public GameUI<T> setIcon(ImageIcon icon)
	{
		try
		{
			if (compoent.getClass() == JButton.class)
				((JButton)compoent).setIcon(icon);
			else if (compoent.getClass() == JLabel.class)
				((JLabel)compoent).setIcon(icon);
			else
				throw new Exception("Not support type!");
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return this;
	}
	
	public GameUI<T> setLayout(LayoutManager mgr)
	{
		try
		{
			if (compoent.getClass() == JPanel.class)
				compoent.setLayout(mgr);
			else
				throw new Exception("Not support type!");
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return this;
	}
	
	public GameUI<T> add(Component comp)
	{
		try
		{
			if (compoent.getClass() == JPanel.class)
				compoent.add(comp);
			else
				throw new Exception("Not support type!");
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return this;
	}
	
	public GameUI<T> refreshUI()
	{
		compoent.invalidate();
		compoent.repaint();
		return this;
	}
	
	public GameUI<T> add(GameUI<?> obj, int index_Z_Order)
	{
		try
		{
			if (compoent.getClass() == JPanel.class)
				compoent.add(obj.getObject(), index_Z_Order);
			else
				throw new Exception("Not support type!");
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return this;
	}
	
	public GameUI<T> add(GameUI<?> obj)
	{
		try
		{
			if (compoent.getClass() == JPanel.class)
				compoent.add(obj.getObject());
			else
				throw new Exception("Not support type!");
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return this;
	}
	
	public GameUI<T> add(GameUI<?> obj, String str)
	{
		try
		{
			if (compoent.getClass() == JPanel.class)
				compoent.add(obj.getObject(), str);
			else
				throw new Exception("Not support type!");
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return this;
	}

	public GameUI<T> setTag(String Key, Integer ID)
	{
		compoent.putClientProperty(Key, ID);
		return this;
	}
	
	public GameUI<T> setTag(String Key, GameUI<T> obj)
	{
		compoent.putClientProperty(Key, obj);
		return this;
	}
	
	public GameUI<T> setToolTipText(String text)
	{
		compoent.setToolTipText(text);
		return this;
	}

	
	public GameUI<T> setText(String text)
	{
		try
		{
			if (compoent.getClass() == JLabel.class)
				((JLabel)compoent).setText(text);
			else
				throw new Exception("Not support type!");
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return this;
	}
	
	public GameUI<T> setSize(int width, int height)
	{
		compoent.setSize(width, height);
		return this;
	}
	
	public GameUI<T> setFont(String font)
	{
		if(font == "微軟正黑體")
			compoent.setFont(new Font(font, Font.PLAIN, 48));
		else 
			compoent.setFont(new Font(font, Font.PLAIN, 20));
		return this;
	}
	
	public GameUI<T> setBackground(Color bg)
	{
		compoent.setBackground(bg);
		return this;
	}
	
	public GameUI<T> setVisible(boolean isVisible)
	{
		compoent.setVisible(isVisible);
		return this;
	}
	
	public GameUI<T> setTransparent()
	{
		compoent.setOpaque(false);
		if (compoent.getClass() == JButton.class)
		{
			((JButton)compoent).setContentAreaFilled(false);
			((JButton)compoent).setBorderPainted(false);
		}
		return this;
	}
	public void dispose()
	{
		compoent.removeAll();
		Container parent = compoent.getParent();
		parent.remove(compoent);
		parent.invalidate();
		parent.repaint();
		compoent = null;
	}
	

	
	private int targetX, targetY, intervel;
	private boolean disposeAfterMove;
	Thread moveThread;
	
	private void moveToBlocking()
	{
		double startX = compoent.getX();
		double startY = compoent.getY();
		double deltaX = (targetX - startX) / (double)intervel;
		double deltaY = (targetY - startY) / (double)intervel;
		int deltaTime = Math.min(intervel / 100,  16);
		if (deltaTime == 0)
			deltaTime = 1;
		int timeStamp = 0;
		
		while (timeStamp < intervel && moveThread != null)
		{
			try
			{
				int X = (int)(startX + deltaX * timeStamp);
				int Y = (int)(startY + deltaY * timeStamp);
				compoent.setLocation(X, Y);
				timeStamp += deltaTime;
				Thread.sleep(deltaTime);
			}catch (Exception e)
			{
				
			}
		}
		if (disposeAfterMove)
		{
			this.dispose();
			moveThread = null;
			return;
		}
		compoent.setLocation(targetX, targetY);
		moveThread = null;
	}
	public GameUI<T> moveTo(int X, int Y, int millis, boolean disposeAfterMove)
	{
		if (moveThread != null)
		{
			Thread temp = moveThread;
			moveThread = null;
			temp.interrupt();
			try {
				temp.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		targetX = X;
		targetY = Y;
		intervel = millis;
		this.disposeAfterMove = disposeAfterMove;
		
		moveThread = new Thread(() -> { moveToBlocking(); });
		moveThread.run();
		return this;
	}
	
	public GameUI<?> moveTo(GameUI<?> obj, int millis, boolean disposeAfterMove)
	{
		Point location = obj.compoent.getLocationOnScreen();
		Dimension dimension = obj.compoent.getSize();
		return moveTo(location.x + dimension.width / 4, location.y + 10, millis, disposeAfterMove);
	}
	
	
}
