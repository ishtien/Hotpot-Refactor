package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Veg extends ConcreteFood{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage("raw_veg.png", 100, 100),
			GetResource.createImage("cooked_veg.png", 100, 100),
			GetResource.createImage("fucked_up_veg.png", 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage("raw_veg.png", 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = "青菜";
	private static int cal = 15;
	private static int gram = 50;
	private static int price = 10;
	
	public Veg() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}

}