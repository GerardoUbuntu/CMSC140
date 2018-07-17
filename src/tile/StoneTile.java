package tile;

import Imageloader.Assets;

public class StoneTile extends Tile{
	
	public StoneTile(int id) {
		super(Assets.stone, id);
	}
	
	public boolean isSolid(){
		return true;
	}
}
