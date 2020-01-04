package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Fish extends FoodConcrete{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage("raw_fish.png", 100, 100),
			GetResource.createImage("cooked_fish.png", 100, 100),
			GetResource.createImage("fucked_up_fish.png", 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage("fish.png", 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = "魚肉";
	private static int cal = 25;
	private static int gram = 20;
	private static int price = 120;
	
	public Fish() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}

}
