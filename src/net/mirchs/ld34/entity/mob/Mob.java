package net.mirchs.ld34.entity.mob;

import net.mirchs.dm8.math.Vector2f;
import net.mirchs.ld34.entity.Entity;

public abstract class Mob extends Entity {

	protected enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	
	protected boolean corrupted = false;
	

	Direction dir;

	public Mob(Vector2f spawnPoint) {
		position.x = spawnPoint.x;
		position.y = spawnPoint.y;
	}

	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
		}

		if (!collision(xa, ya)) {
			position.x += xa;
			position.y += ya;
		}

	}

	public boolean collision(int xa, int ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (((int) position.x + xa) + c % 2 * 48) >> 6;
			int yt = (((int) position.y + ya) + c / 2 * 48 + 3) >> 6;
			if (level.getTile(xt, yt).isSolid()) solid = true;
		}
		return solid;
	}

}
