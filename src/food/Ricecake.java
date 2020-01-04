package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Ricecake extends FoodConcrete{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage("raw_ricecake.png", 100, 100),
			GetResource.createImage("cooked_ricecake.png", 100, 100),
			GetResource.createImage("fucked_up_ricecake.png", 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage("raw_ricecake.png", 100, 100);
	private static int transferState[] = {30000, 120000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = "年糕";
	private static int cal = 80;
	private static int gram = 60;
	private static int price = 55;
	
	public Ricecake() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}

}