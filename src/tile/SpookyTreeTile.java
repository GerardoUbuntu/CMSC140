package tile;

import Imageloader.Assets;

public class SpookyTreeTile extends Tile{
	
	public SpookyTreeTile(int id) {
		super(Assets.spookytree, id);
	}
	
	public boolean isSolid(){
		return true;
	}
}

