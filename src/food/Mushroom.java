package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Mushroom extends FoodConcrete{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage(ConstantData.mushroomImgState1, 100, 100),
			GetResource.createImage(ConstantData.mushroomImgState2, 100, 100),
			GetResource.createImage(ConstantData.mushroomImgState3, 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage(ConstantData.mushroomImgOri, 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = ConstantData.mushroomName;
	private static int cal = ConstantData.mushroomCal;
	private static int gram = ConstantData.mushroomGram;
	private static int price = ConstantData.mushroomPrice;
	
	public Mushroom() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}

}