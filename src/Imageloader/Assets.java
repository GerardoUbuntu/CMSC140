package Imageloader;

import java.awt.image.BufferedImage;

public class Assets {
	
	public static BufferedImage back,rock,grass;
	public static BufferedImage[] slender_down;
	public static BufferedImage[] slender_up;
	public static BufferedImage[] slender_right;
	public static BufferedImage[] slender_left;
	private static SpriteSheet sheet; 
	public static int width = 32, height =32;
	
	public static void init(){
		sheet = new SpriteSheet(ImageLoader.loadImage("/images/sprite.png"));
		
		back = ImageLoader.loadImage("/images/Horizon.png");
		rock = ImageLoader.loadImage("/images/rock.png");
		grass = ImageLoader.loadImage("/images/grass.png");
		
		slender_down = new BufferedImage[9]; 
		slender_up = new BufferedImage[9];
		slender_right = new BufferedImage[9];
		slender_left = new BufferedImage[9];
		
		store();
	}

	private static void store() {
	    for(int i = 0 ; i  < 9; i++){
	    	slender_up[i] = sheet.crop(i * width, 0, width, height);
	    }
	    for(int i = 0 ; i  < 9; i++){
	    	slender_left[i] = sheet.crop(i * width, 32, width, height);
	    }
	    for(int i = 0 ; i  < 9; i++){
	    	slender_down[i] = sheet.crop(i * width, 64, width, height);
	    }
	    for(int i = 0 ; i  < 9; i++){
	    	slender_right[i] = sheet.crop(i * width, 96, width, height);
	    }
	}

}
