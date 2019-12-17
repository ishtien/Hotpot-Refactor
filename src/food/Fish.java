package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Fish implements Food{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage("raw_fish.png", 100, 100),
			GetResource.createImage("cooked_fish.png", 100, 100),
			GetResource.createImage("fucked_up_fish.png", 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage("fish.png", 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private Food sauce = null;

	public ImageIcon getIconInUI()
	{
		return iconUI;
	}
	
	public ImageIcon getIconInHotpot(int index)
	{
		if (index > 2)
			return getIconInHotpot(2);
		else if (index < 0)
			return getIconInHotpot(0);
		return icons[index];
	}
	public String getName()
	{
		return "魚肉";
	}
	public int getTime(int nowState)
	{
		if (nowState < 0)
			return getTime(nowState);
		else if (nowState > 2)
			return getTime(2);
		return transferState[nowState];
	}
	public int getCal()
	{
		return 25;
	}
	public int getGram()
	{
		return 20;
	}
	public int getPrice()
	{
		return 12;
	}
	public Food getSauce() 
	{
		return sauce;
	}
}
