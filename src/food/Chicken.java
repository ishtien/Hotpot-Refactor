package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Chicken extends FoodConcrete{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage(ConstantData.chickenImgState1, 100, 100),
			GetResource.createImage(ConstantData.chickenImgState2, 100, 100),
			GetResource.createImage(ConstantData.chickenImgState3, 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage(ConstantData.chickenImgOri, 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = ConstantData.chickenName;
	private static int cal = ConstantData.chickenCal;
	private static int gram = ConstantData.chickenGram;
	private static int price = ConstantData.chickenPrice;
	
	public Chicken() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}

}
