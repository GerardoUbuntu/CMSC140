package Imageloader;

import java.awt.image.BufferedImage;



public class Assets {
	
	public static BufferedImage back,rock,grass, bg, title;
	public static BufferedImage[] slender_down;
	public static BufferedImage[] slender_up;
	public static BufferedImage[] slender_right;
	public static BufferedImage[] slender_left;
	public static BufferedImage[] human_down;
	public static BufferedImage[] human_up;
	public static BufferedImage[] human_right;
	public static BufferedImage[] human_left;
	public static BufferedImage[] start, quit, create, join;
	private static SpriteSheet sheet; 
	private static SpriteSheet sheet1; 
	public static int width = 32, height =32;
	
	public static void init(){
		sheet = new SpriteSheet(ImageLoader.loadImage("/images/sprite.png"));
		sheet1 = new SpriteSheet(ImageLoader.loadImage("/images/slender.png"));
		bg  = ImageLoader.loadImage("/images/menubg.gif");
		back = ImageLoader.loadImage("/images/Horizon.png");
		rock = ImageLoader.loadImage("/images/rock.png");
		grass = ImageLoader.loadImage("/images/grass.png");
		title = ImageLoader.loadImage("/images/Photos/hadsd.png");
		
		start = new BufferedImage[2];
		quit= new BufferedImage[2];
		create = new BufferedImage[2];
		join = new BufferedImage[2];
		start[0] = ImageLoader.loadImage("/images/Photos/Start.png");
		start[1] = ImageLoader.loadImage("/images/Photos/Start1.png");
		create[0] = ImageLoader.loadImage("/images/Photos/create.png");
		create[1] = ImageLoader.loadImage("/images/Photos/create1.png");
		join[0] = ImageLoader.loadImage("/images/Photos/join.png");
		join[1] = ImageLoader.loadImage("/images/Photos/join1.png");
		quit[0] = ImageLoader.loadImage("/images/Photos/Quit.png");
		quit[1] = ImageLoader.loadImage("/images/Photos/Quit1.png");
		slender_down = new BufferedImage[9]; 
		slender_up = new BufferedImage[9];
		slender_right = new BufferedImage[9];
		slender_left = new BufferedImage[9];
		human_down = new BufferedImage[9]; 
		human_up = new BufferedImage[9];
		human_right = new BufferedImage[9];
		human_left = new BufferedImage[9];
		store();
	}

	private static void store() {
	    for(int i = 0 ; i  < 9; i++){
	    	slender_up[i] = sheet1.crop(i * width, 0, width, height);
	    	human_up[i] = sheet.crop(i * width, 0, width, height);
	    }
	    for(int i = 0 ; i  < 9; i++){
	    	slender_left[i] = sheet1.crop(i * width, 32, width, height);
	    	human_left[i] = sheet.crop(i * width, 32, width, height);
	    }
	    for(int i = 0 ; i  < 9; i++){
	    	slender_down[i] = sheet1.crop(i * width, 64, width, height);
	    	human_down[i] = sheet.crop(i * width, 64, width, height);
	    }
	    for(int i = 0 ; i  < 9; i++){
	    	slender_right[i] = sheet1.crop(i * width, 96, width, height);
	    	human_right[i] = sheet.crop(i * width, 96, width, height);
	    }
	}

}
