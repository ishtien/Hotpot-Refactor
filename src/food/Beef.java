package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Beef extends FoodConcrete{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage(ConstantData.beefImgState1, 100, 100),
			GetResource.createImage(ConstantData.beefImgState2, 100, 100),
			GetResource.createImage(ConstantData.beefImgState3, 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage(ConstantData.beefImgOri, 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	
	private static String name = ConstantData.beefName;
	private static int cal = ConstantData.beefCal;
	private static int gram = ConstantData.beefGram;
	private static int price = ConstantData.beefPrice;
	
	public Beef() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}

}
