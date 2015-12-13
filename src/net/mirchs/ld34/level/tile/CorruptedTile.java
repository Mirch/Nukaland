package net.mirchs.ld34.level.tile;

import java.util.Random;

import net.mirchs.dm8.graphics.Texture;

public class CorruptedTile extends Tile {

	private final Random random = new Random();

	public CorruptedTile(Texture texture) {
		super(texture);
		// TODO Auto-generated constructor stub
	}

	public int corrupt() {
		int happening = random.nextInt(20);
		if (happening == 0)
			return random.nextInt(4);
		else
			return -1;
	}
	
	public int corruptForSure() {
		return random.nextInt(4);
	}

}
