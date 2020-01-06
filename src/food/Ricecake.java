package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Ricecake extends FoodConcrete{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage(ConstantData.ricecakeImgState1, 100, 100),
			GetResource.createImage(ConstantData.ricecakeImgState2, 100, 100),
			GetResource.createImage(ConstantData.ricecakeImgState3, 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage(ConstantData.ricecakeImgOri, 100, 100);
	private static int transferState[] = {30000, 120000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = ConstantData.ricecakeName;
	private static int cal = ConstantData.ricecakeCal;
	private static int gram = ConstantData.ricecakeGram;
	private static int price = ConstantData.ricecakePrice;
	
	public Ricecake() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}

}