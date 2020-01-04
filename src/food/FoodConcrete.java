package food;

import javax.swing.ImageIcon;

import resource.GetResource;

public class FoodConcrete implements Food{
	private ImageIcon icons[];
	private ImageIcon iconUI;
	private int transferState[];
	private Food sauce = null;
	
	private String name;
	private int cal;
	private int gram;
	private int price;
	
	public FoodConcrete(ImageIcon ICONS[], ImageIcon ICON_UI, int TRANSFER_STATE[],Food SAUCE, String NAME, int CAL, int GRAM, int PRICE) {
		icons = ICONS;
		iconUI = ICON_UI;
		transferState = TRANSFER_STATE;
		sauce = SAUCE;
		name = NAME;
		cal = CAL;
		gram = GRAM;
		price = PRICE;
	}

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
		return name;
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
		return cal;
	}
	public int getGram()
	{
		return gram;
	}
	public int getPrice()
	{
		return price;
	}
	public Food getSauce() 
	{
		return sauce;
	}

}
