package food;

import javax.swing.ImageIcon;

public interface Food {
	ImageIcon getIconInHotpot(int index);
	ImageIcon getIconInUI();
	String getName();
	int getTime(int nowState);
	int getCal();
	int getGram();
	int getPrice();
	Food getSauce();
}
