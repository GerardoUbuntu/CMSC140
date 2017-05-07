package Imageloader;

import java.awt.image.BufferedImage;



public class Assets {
	
	public static BufferedImage back,rock,grass, bg, title;
	public static BufferedImage[] slender_down;
	public static BufferedImage[] slender_up;
	public static BufferedImage[] slender_right;
	public static BufferedImage[] slender_left;
	public static BufferedImage[] start, quit;
	private static SpriteSheet sheet; 
	public static int width = 32, height =32;
	
	public static void init(){
		sheet = new SpriteSheet(ImageLoader.loadImage("/images/sprite.png"));
		bg  = ImageLoader.loadImage("/images/menubg.gif");
		back = ImageLoader.loadImage("/images/Horizon.png");
		rock = ImageLoader.loadImage("/images/rock.png");
		grass = ImageLoader.loadImage("/images/grass.png");
		title = ImageLoader.loadImage("/images/Photos/hadsd.png");
		
		start = new BufferedImage[2];
		quit= new BufferedImage[2];
		start[0] = ImageLoader.loadImage("/images/Photos/Start.png");
		start[1] = ImageLoader.loadImage("/images/Photos/Start1.png");
		quit[0] = ImageLoader.loadImage("/images/Photos/Quit.png");
		quit[1] = ImageLoader.loadImage("/images/Photos/Quit1.png");
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
