package Imageloader;

import java.awt.Font;
import java.awt.image.BufferedImage;



public class Assets {
	
	public static Font silk12, silk20, tat30, tm30;
	public static BufferedImage back,rock,grass,ground,tree,cuttree,spookytree,bigrock, bg,stone,bush, title, s, l,e, n, d, r, m, a;
	public static BufferedImage[] slender_down;
	public static BufferedImage[] slender_up;
	public static BufferedImage[] slender_right;
	public static BufferedImage[] slender_left;
	public static BufferedImage[] human_down;
	public static BufferedImage[] human_up;
	public static BufferedImage[] human_right;
	public static BufferedImage[] human_left;
	public static BufferedImage[] start, quit, create, join, back2, help;
	private static SpriteSheet sheet; 
	private static SpriteSheet sheet1; 
	public static int width = 32, height =32;
	
	public static void init(){
		
		silk12 = FontLoader.loadFont("/fonts/silkscreen/slkscr.ttf", 12);
		tm30 =  FontLoader.loadFont("/fonts/tat/tat.ttf", 30);
        tat30 = FontLoader.loadFont("/fonts/tm/tm.ttf", 30);
		silk20 = FontLoader.loadFont("/fonts/silkscreen/slkscr.ttf", 20);
		sheet = new SpriteSheet(ImageLoader.loadImage("/images/sprite.png"));
		sheet1 = new SpriteSheet(ImageLoader.loadImage("/images/slender.png"));
		bg  = ImageLoader.loadImage("/images/menubg.gif");
		back = ImageLoader.loadImage("/images/Horizon.png");
		rock = ImageLoader.loadImage("/images/rock.png");
		grass = ImageLoader.loadImage("/images/grass.png");
		ground = ImageLoader.loadImage("/images/ground.png");
		tree = ImageLoader.loadImage("/images/tree.png");
		spookytree = ImageLoader.loadImage("/images/spookytree.png");
		stone = ImageLoader.loadImage("/images/stone.png");
		bush = ImageLoader.loadImage("/images/bush.png");
		bigrock = ImageLoader.loadImage("/images/bigrock.png");
		title = ImageLoader.loadImage("/images/Photos/hadsd.png");
		cuttree = ImageLoader.loadImage("/images/cuttree.png");
		
		start = new BufferedImage[2];
		quit= new BufferedImage[2];
		create = new BufferedImage[2];
		join = new BufferedImage[2];
		back2 = new BufferedImage[2];
		help = new BufferedImage[2];
		start[0] = ImageLoader.loadImage("/images/Photos/Start.png");
		start[1] = ImageLoader.loadImage("/images/Photos/Start1.png");
		back2[0] = ImageLoader.loadImage("/images/Photos/back.png");
		back2[1] = ImageLoader.loadImage("/images/Photos/back1.png");
		help[0] = ImageLoader.loadImage("/images/Photos/help.png");
		help[1] = ImageLoader.loadImage("/images/Photos/help1.png");
		create[0] = ImageLoader.loadImage("/images/Photos/create.png");
		create[1] = ImageLoader.loadImage("/images/Photos/create1.png");
		join[0] = ImageLoader.loadImage("/images/Photos/join.png");
		join[1] = ImageLoader.loadImage("/images/Photos/join1.png");
		quit[0] = ImageLoader.loadImage("/images/Photos/Quit.png");
		quit[1] = ImageLoader.loadImage("/images/Photos/Quit1.png");
		
		s = ImageLoader.loadImage("/images/Photos/s.png");
		l = ImageLoader.loadImage("/images/Photos/l.png");
		e = ImageLoader.loadImage("/images/Photos/e.png");
		n = ImageLoader.loadImage("/images/Photos/n.png");
		d = ImageLoader.loadImage("/images/Photos/d.png");
		r = ImageLoader.loadImage("/images/Photos/r.png");
		m = ImageLoader.loadImage("/images/Photos/m.png");
		a = ImageLoader.loadImage("/images/Photos/a.png");
		
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
