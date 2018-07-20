package Imageloader;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
    
	public static BufferedImage loadImage(String path){
		try {
			return ImageIO.read(ImageLoader.class.getResourceAsStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.exit(1);
			e.printStackTrace();
		}
		return null;
	}
}
