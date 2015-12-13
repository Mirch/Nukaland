package net.mirchs.ld34.level.tile;

import net.mirchs.dm8.graphics.Texture;

public class WaterTile extends Tile {

	
	public WaterTile(Texture texture) {
		super(texture);
	}
	
	
	public boolean isSolid() {
		return true;
	}

}
