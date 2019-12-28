package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Chicken extends ConcreteFood{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage("raw_chicken.png", 100, 100),
			GetResource.createImage("cooked_chicken.png", 100, 100),
			GetResource.createImage("fucked_up_chicken.png", 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage("chicken.png", 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = "雞肉";
	private static int cal = 50;
	private static int gram = 20;
	private static int price = 8;
	
	public Chicken() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}

}
