package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Veg extends FoodConcrete{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage(ConstantData.vegImgState1, 100, 100),
			GetResource.createImage(ConstantData.vegImgState2, 100, 100),
			GetResource.createImage(ConstantData.vegImgState3, 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage(ConstantData.vegImgOri, 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = ConstantData.vegName;
	private static int cal = ConstantData.vegCal;
	private static int gram = ConstantData.vegGram;
	private static int price = ConstantData.vegPrice;
	
	public Veg() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}

}