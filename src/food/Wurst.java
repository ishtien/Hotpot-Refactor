package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Wurst extends FoodConcrete{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage(ConstantData.wurstImgState1, 100, 100),
			GetResource.createImage(ConstantData.wurstImgState2, 100, 100),
			GetResource.createImage(ConstantData.wurstImgState3, 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage(ConstantData.wurstImgOri, 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = ConstantData.wurstName;
	private static int cal = ConstantData.wurstCal;
	private static int gram = ConstantData.wurstGram;
	private static int price = ConstantData.wurstPrice;
	
	public Wurst() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}
}