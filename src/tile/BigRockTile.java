package tile;

import Imageloader.Assets;

public class BigRockTile extends Tile{
	
	public BigRockTile(int id) {
		super(Assets.bigrock, id);
	}
	
	public boolean isSolid(){
		return true;
	}
}

