package hotpot;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class UIObject<T extends JComponent> {
	private T compoent;
	private int targetX, targetY, intervel;
	private boolean disposeAfterMove;
	Thread moveThread;
	
	private void moveTo_blocking()
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
	public UIObject<T> moveTo(int X, int Y, int millis, boolean disposeAfterMove)
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
		
		moveThread = new Thread(() -> { moveTo_blocking(); });
		moveThread.run();
		return this;
	}
	
	public UIObject<?> moveTo(UIObject<?> obj, int millis, boolean disposeAfterMove)
	{
		Point location = obj.compoent.getLocationOnScreen();
		Dimension dimension = obj.compoent.getSize();
		return moveTo(location.x + dimension.width / 4, location.y + 10, millis, disposeAfterMove);
	}
	
	public UIObject(T obj)
	{
		compoent = obj;
	}
	
	public T getObject() 
	{
		return compoent;
	}
	

	public UIObject<T> setBorder(EmptyBorder border)
	{
		compoent.setBorder(border);
		return this;
	}
	
	public UIObject<T> setBounds(int x, int y, int width, int height)
	{
		compoent.setBounds(x, y, width, height);
		return this;
	}
	
	public UIObject<T> addActionListener(ActionListener listener)
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
	
	public UIObject<T> setIcon(ImageIcon icon)
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
	
	public UIObject<T> setLayout(LayoutManager mgr)
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
	
	public UIObject<T> add(Component comp)
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
	
	public UIObject<T> refreshUI()
	{
		compoent.invalidate();
		compoent.repaint();
		return this;
	}
	
	public UIObject<T> add(UIObject<?> obj, int index_Z_Order)
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
	
	public UIObject<T> add(UIObject<?> obj)
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
	
	public UIObject<T> add(UIObject<?> obj, String str)
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

	public UIObject<T> setTag(String Key, Integer ID)
	{
		compoent.putClientProperty(Key, ID);
		return this;
	}
	
	public UIObject<T> setTag(String Key, UIObject<T> obj)
	{
		compoent.putClientProperty(Key, obj);
		return this;
	}
	
	public UIObject<T> setToolTipText(String text)
	{
		compoent.setToolTipText(text);
		return this;
	}

	
	public UIObject<T> setText(String text)
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
	
	public UIObject<T> setSize(int width, int height)
	{
		compoent.setSize(width, height);
		return this;
	}
	
	public UIObject<T> setBackground(Color bg)
	{
		compoent.setBackground(bg);
		return this;
	}
	
	public UIObject<T> setVisible(boolean isVisible)
	{
		compoent.setVisible(isVisible);
		return this;
	}
	
	public UIObject<T> setTransparent()
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
	
	public static UIObject<JButton> createButton(ImageIcon image)
	{
		return new UIObject<JButton>(new JButton(image)); 
	}
	
	public static UIObject<JPanel> createPanel()
	{
		return new UIObject<>(new JPanel());
	}
	
	public static UIObject<JLabel> createLabel(ImageIcon image)
	{
		return new UIObject<JLabel>(new JLabel(image)); 
	}
}
