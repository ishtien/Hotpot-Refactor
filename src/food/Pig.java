package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Pig extends ConcreteFood{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage("raw_pig.png", 100, 100),
			GetResource.createImage("cooked_pig.png", 100, 100),
			GetResource.createImage("fucked_up_pig.png", 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage("pig.png", 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = "豬肉";
	private static int cal = 25;
	private static int gram = 20;
	private static int price = 120;
	
	public Pig() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}

}
