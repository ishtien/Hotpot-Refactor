package food;

import resource.GetResource;
import javax.swing.ImageIcon;


public class Fishegg extends FoodConcrete{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage(ConstantData.fisheggImgState1, 100, 100),
			GetResource.createImage(ConstantData.fisheggImgState2, 100, 100),
			GetResource.createImage(ConstantData.fisheggImgState3, 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage(ConstantData.fisheggImgOri, 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = ConstantData.fisheggName;
	private static int cal = 36;
	private static int gram = 30;
	private static int price = 45;
	
	public Fishegg() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}
}