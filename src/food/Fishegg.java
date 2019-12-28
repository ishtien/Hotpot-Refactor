package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Fishegg extends ConcreteFood{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage("raw_fishegg.png", 100, 100),
			GetResource.createImage("cooked_fishegg.png", 100, 100),
			GetResource.createImage("fucked_up_fishegg.png", 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage("raw_fishegg.png", 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = "魚蛋";
	private static int cal = 36;
	private static int gram = 30;
	private static int price = 45;
	
	public Fishegg() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}
}