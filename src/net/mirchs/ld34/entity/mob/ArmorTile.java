package net.mirchs.ld34.entity.mob;

import net.mirchs.dm8.graphics.Texture;
import net.mirchs.ld34.level.tile.Tile;

public class ArmorTile extends Tile {

	public ArmorTile(Texture texture) {
		super(texture);
	}
	
	public boolean isSolid() {
		return true;
	}

}
