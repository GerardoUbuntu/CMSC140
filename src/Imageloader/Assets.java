package Imageloader;

import java.awt.image.BufferedImage;

public class Assets {
	
	public static BufferedImage back, slender;
	
	public static void init(){
		back = ImageLoader.loadImage("/images/Horizon.png");
		slender = ImageLoader.loadImage("/images/slender.png");
	}

}
