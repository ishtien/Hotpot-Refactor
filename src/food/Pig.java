package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Pig extends FoodConcrete{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage(ConstantData.pigImgState1, 100, 100),
			GetResource.createImage(ConstantData.pigImgState2, 100, 100),
			GetResource.createImage(ConstantData.pigImgState3, 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage(ConstantData.pigImgOri, 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = ConstantData.pigName;
	private static int cal = ConstantData.pigCal;
	private static int gram = ConstantData.pigGram;
	private static int price = ConstantData.pigPrice;
	
	public Pig() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}

}
