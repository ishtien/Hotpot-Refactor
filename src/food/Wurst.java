package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Wurst extends FoodConcrete{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage("raw_wurst.png", 100, 100),
			GetResource.createImage("cooked_wurst.png", 100, 100),
			GetResource.createImage("fucked_up_wurst.png", 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage("raw_wurst.png", 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = "鑫鑫腸";
	private static int cal = 25;
	private static int gram = 20;
	private static int price = 120;
	
	public Wurst() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}
}