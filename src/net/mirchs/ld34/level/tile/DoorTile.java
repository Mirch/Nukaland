package net.mirchs.ld34.level.tile;

import net.mirchs.dm8.graphics.Texture;

public class DoorTile extends Tile {
	
	
	private int i=0;

	public DoorTile(Texture texture, int i) {
		super(texture);
		this.i = i;
		// TODO Auto-generated constructor stub
	}
	
	public boolean isSolid() {
		if(i==0)
			return true;
		return false;
	}
	
	
	

}
