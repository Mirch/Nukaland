package net.mirchs.ld34.entity.mob;

import java.util.Random;

import net.mirchs.dm8.graphics.Texture;
import net.mirchs.dm8.graphics.buffers.VertexArray;
import net.mirchs.dm8.math.Matrix4f;
import net.mirchs.dm8.math.Vector2f;
import net.mirchs.dm8.math.Vector3f;
import net.mirchs.ld34.ShaderContainer;
import net.mirchs.ld34.level.tile.CorruptedTile;

public class Phap extends Mob {

	private final Random random = new Random();
	private int counter, timer, dirx, diry;

	protected Texture stay = new Texture("res/playerSprites/player_stay.png");
	protected Texture right = new Texture("res/playerSprites/player_right.png");
	protected Texture left = new Texture("res/playerSprites/player_left.png");
	protected Texture up = new Texture("res/playerSprites/player_back.png");
	protected Texture down = new Texture("res/playerSprites/player_front.png");

	protected Texture corrupt_stay = new Texture("res/playerSprites/corrupted_player_stay.png");
	protected Texture corrupt_right = new Texture("res/playerSprites/corrupted_player_right.png");
	protected Texture corrupt_left = new Texture("res/playerSprites/corrupted_player_left.png");
	protected Texture corrupt_up = new Texture("res/playerSprites/corrupted_player_back.png");
	protected Texture corrupt_down = new Texture("res/playerSprites/corrupted_player_front.png");

	public Phap(Vector2f spawnPoint) {
		super(spawnPoint);

		texture = stay;

		this.width = texture.getWidth();
		this.height = texture.getHeight();

		float[] vertices = new float[] {
				0.0f, 0.0f, 0.2f,
				0.0f, height, 0.2f,
				width, height, 0.2f,
				width, 0.0f, 0.2f
		};

		byte[] indices = new byte[] {
				0, 1, 2,
				2, 3, 0
		};

		float[] tcs = new float[] {
				0, 1,
				0, 0,
				1, 0,
				1, 1
		};

		mesh = new VertexArray(vertices, indices, tcs);
	}

	public void update() {
		int xa = 0, ya = 0;
		if ((counter % 240 == 0)) {
			dirx = random.nextInt(3);
			diry = random.nextInt(3);
		} 
		if (isCorrupted())
			seePlayer();

		counter++;

		if (!corrupted)

		{
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
		} else

		{
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

		if (dirx == 0) xa = -2;
		if (dirx == 1) xa = 0;
		if (dirx == 2) xa = 2;

		if (diry == 0) ya = -2;
		if (diry == 1) ya = 0;
		if (diry == 2) ya = 2;

		move(xa, ya);

		for (int c = 0; c < 4; c++) {
			int xt = (((int) position.x + xa) + c % 2 * 48) >> 6;
			int yt = (((int) position.y + ya) + c / 2 * 48 + 3) >> 6;
			if (level.getTile(xt, yt) instanceof CorruptedTile) {
				corrupt();
			}
		}

	}

	private void seePlayer() {
		Player p = level.getPlayer();
		float px = p.getX();
		float py = p.getY();
		
		int xa = 0, ya = 0;

		if (position.x < px && Math.abs(position.x - px) < 64 * 5) {
			xa = 2;
		} else if (position.x > px && Math.abs(position.x - px) < 64 * 5) {
			xa = 0;
		} else
			xa = 1;

		if (position.y < py && Math.abs(position.y - py) < 64 * 5) {
			ya = 2;
		} else if (position.y > py && Math.abs(position.y - py) < 64 * 5) {
			ya = 0;
		} else
			ya = 1;
		
		if(xa != 1 && ya != 1) {
			dirx = xa;
			diry = ya;
		}

	}

	public void corrupt() {
		corrupted = true;
	}

	public void decorrupt() {
		corrupted = false;
	}

	public boolean isCorrupted() {
		return corrupted;
	}

	@Override
	public void render(float x, float y) {
		x += position.x;
		y += position.y;
		texture.bind();
		ShaderContainer.PHAP.enable();
		ShaderContainer.PHAP.setUniformMat4f("ml_matrix", Matrix4f.translate(new Vector3f(x, y, 0.0f)));
		mesh.render();
		ShaderContainer.PHAP.disable();
		texture.unbind();

	}

}
