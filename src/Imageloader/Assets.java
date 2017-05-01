package Imageloader;

import java.awt.image.BufferedImage;

public class Assets {
	
	public static BufferedImage back, slender, rock,grass;
	
	public static void init(){
		back = ImageLoader.loadImage("/images/Horizon.png");
		slender = ImageLoader.loadImage("/images/slender.png");
		rock = ImageLoader.loadImage("/images/rock.png");
		grass = ImageLoader.loadImage("/images/grass.png");
	}

}
