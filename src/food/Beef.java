package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Beef extends FoodConcrete{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage("raw_beef.png", 100, 100),
			GetResource.createImage("cooked_beef.png", 100, 100),
			GetResource.createImage("fucked_up_beef.png", 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage("beef.png", 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	
	private static String name = "牛肉";
	private static int cal = 25;
	private static int gram = 20;
	private static int price = 15;
	
	public Beef() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}

}
