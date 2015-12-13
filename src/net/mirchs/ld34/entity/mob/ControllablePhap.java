package net.mirchs.ld34.entity.mob;

import net.mirchs.dm8.graphics.Texture;
import net.mirchs.dm8.math.Matrix4f;
import net.mirchs.dm8.math.Vector2f;
import net.mirchs.ld34.ShaderContainer;
import net.mirchs.ld34.level.tile.CorruptedTile;

public class ControllablePhap extends Phap {

	private int dirx, diry;
	private boolean reached = false;
	private Vector2f endPoint = new Vector2f();
	private boolean isSet = false;

	public ControllablePhap(Vector2f spawnPoint) {
		super(spawnPoint);
	}

	public void setMove(Vector2f endPoint) {
		this.endPoint = endPoint;
		isSet = true;
	}

	public void update() {
		int xa = 0, ya = 0;

		if (position.x < endPoint.x) xa += 2;
		if (position.x > endPoint.x) xa -= 2;

		if (position.y < endPoint.y) ya += 2;
		if (position.y > endPoint.y) ya -= 2;

		if (!corrupted) {
			if (dirx == 1 && diry == 1)
				texture = stay;
			if (dirx == 0)
				texture = left;
			if (dirx == 2)
				texture = right;
			if (diry == 0)
				texture = up;
			if (diry == 2)
				texture = down;
		} else {
			if (dirx == 1 && diry == 1)
				texture = corrupt_stay;
			if (dirx == 0)
				texture = corrupt_left;
			if (dirx == 2)
				texture = corrupt_right;
			if (diry == 0)
				texture = corrupt_up;
			if (diry == 2)
				texture = corrupt_down;
		}

		if ((position.x != endPoint.x || position.y != endPoint.y) && isSet)
			move(xa, ya);
		else
			reached = true;

		for (int c = 0; c < 4; c++) {
			int xt = (((int) position.x + xa) + c % 2 * 48) >> 6;
			int yt = (((int) position.y + ya) + c / 2 * 48 + 3) >> 6;
			if (level.getTile(xt, yt) instanceof CorruptedTile) {
				corrupt();
			}
		}

	}

	public void lookLeft() {
		texture = left;
	}

	public void lookRight() {
		texture = right;
	}


	public void render() {
		texture.bind();
		ShaderContainer.PHAP.enable();
		ShaderContainer.PHAP.setUniformMat4f("ml_matrix", Matrix4f.translate(position));
		mesh.render();
		ShaderContainer.PHAP.disable();
		texture.unbind();
	}

	public boolean reached() {
		return reached;
	}

}
