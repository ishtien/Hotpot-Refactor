package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Lettuce extends FoodConcrete{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage(ConstantData.lettuceImgState1, 100, 100),
			GetResource.createImage(ConstantData.lettuceImgState2, 100, 100),
			GetResource.createImage(ConstantData.lettuceImgState3, 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage(ConstantData.lettuceImgOri, 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = ConstantData.lettuceName;
	private static int cal = 9;
	private static int gram = 33;
	private static int price = 69;
	
	public Lettuce() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}

}