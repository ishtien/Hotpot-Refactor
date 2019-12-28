package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Eggdump extends ConcreteFood{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage("raw_eggdump.png", 100, 100),
			GetResource.createImage("cooked_eggdump.png", 100, 100),
			GetResource.createImage("fucked_up_eggdump.png", 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage("raw_eggdump.png", 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = "蛋餃";
	private static int cal = 78;
	private static int gram = 36;
	private static int price = 50;
	
	public Eggdump() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}
}