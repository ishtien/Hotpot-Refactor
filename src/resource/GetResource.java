package resource;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class GetResource {
	
	public static double hotpotWidth = 239, hotpotHeight = 190;
	public static URL getResource(String fileName)
	{
		return GetResource.class.getResource(fileName);
	}
	
	public static ImageIcon createImage(String fileName, int resizeWidth, int resizeHeight)
	{
		URL url = getResource(fileName);
		if (url == null)
			System.out.println(fileName);
		if (url == null)
			return null;
		ImageIcon icon = new ImageIcon(url);
		return new ImageIcon(icon.getImage().getScaledInstance(resizeWidth, resizeHeight,  Image.SCALE_DEFAULT));
	}
	
	public static ImageIcon resizeImage(ImageIcon icon, int resizeWidth, int resizeHeight)
	{
		return new ImageIcon(icon.getImage().getScaledInstance(resizeWidth, resizeHeight, Image.SCALE_DEFAULT));
	}
	
}
