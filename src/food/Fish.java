package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Fish extends FoodConcrete{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage(ConstantData.fishImgState1, 100, 100),
			GetResource.createImage(ConstantData.fishImgState2, 100, 100),
			GetResource.createImage(ConstantData.fishImgState3, 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage(ConstantData.fishImgOri, 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = ConstantData.fishName;
	private static int cal = ConstantData.fishCal;
	private static int gram = ConstantData.fishGram;
	private static int price = ConstantData.fishPrice;
	
	public Fish() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}

}
