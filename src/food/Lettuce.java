package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Lettuce extends ConcreteFood{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage("raw_lettuce.png", 100, 100),
			GetResource.createImage("cooked_lettuce.png", 100, 100),
			GetResource.createImage("fucked_up_lettuce.png", 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage("raw_lettuce.png", 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = "高麗菜";
	private static int cal = 9;
	private static int gram = 33;
	private static int price = 69;
	
	public Lettuce() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}

}