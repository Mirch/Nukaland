package net.mirchs.ld34.level.tile;

import net.mirchs.dm8.graphics.Texture;

public class WallTile extends Tile {

	public WallTile(Texture texture) {
		super(texture);
		// TODO Auto-generated constructor stub
	}
	
	public boolean isSolid() {
		return true;
	}

}
