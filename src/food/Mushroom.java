package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Mushroom extends ConcreteFood{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage("raw_mushroom.png", 100, 100),
			GetResource.createImage("cooked_mushroom.png", 100, 100),
			GetResource.createImage("fucked_up_mushroom.png", 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage("raw_mushroom.png", 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = "香菇";
	private static int cal = 40;
	private static int gram = 100;
	private static int price = 99;
	
	public Mushroom() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}

}