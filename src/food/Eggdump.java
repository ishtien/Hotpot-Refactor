package food;

import resource.GetResource;

import javax.swing.ImageIcon;

public class Eggdump extends FoodConcrete{
	private static ImageIcon icons[] = 
		{
			GetResource.createImage(ConstantData.eggdumpImgState1, 100, 100),
			GetResource.createImage(ConstantData.eggdumpImgState2, 100, 100),
			GetResource.createImage(ConstantData.eggdumpImgState3, 100, 100)
		};
	private static ImageIcon iconUI = GetResource.createImage(ConstantData.eggdumpImgOri, 100, 100);
	private static int transferState[] = {6000, 30000, Integer.MAX_VALUE};
	private static Food sauce = null;
	private static String name = ConstantData.eggdumpName;
	private static int cal = ConstantData.eggdumpCal;
	private static int gram = ConstantData.eggdumpGram;
	private static int price = ConstantData.eggdumpPrice;
	
	public Eggdump() {
		super(icons, iconUI, transferState, sauce, name, cal, gram, price);
	}
}