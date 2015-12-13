package net.mirchs.ld34.entity.mob;

import static org.lwjgl.glfw.GLFW.*;

import net.mirchs.dm8.graphics.Texture;
import net.mirchs.dm8.graphics.buffers.VertexArray;
import net.mirchs.dm8.input.Keyboard;
import net.mirchs.dm8.math.Vector2f;
import net.mirchs.ld34.ShaderContainer;
import net.mirchs.ld34.entity.Sword;
import net.mirchs.ld34.level.tile.SwordTile;

public class Player extends Mob {

	private int counter, timer;

	private Sword sword;

	private Texture stay = new Texture("res/playerSprites/player_stay.png");
	private Texture right = new Texture("res/playerSprites/player_right.png");

	private Texture left = new Texture("res/playerSprites/player_left.png");
	private Texture left2 = new Texture("res/playerSprites/player_left_2.png");

	private Texture up = new Texture("res/playerSprites/player_back.png");
	private Texture down = new Texture("res/playerSprites/player_front.png");

	public Player(Vector2f spawnPoint) {
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

	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
		}

		if (!collision(xa, ya)) {
			position.x += xa;
			position.y += ya;
			level.setScroll(xa, ya);
		}
	}

	public void update() {
		counter++;
		if (counter % 30 == 0) timer++;
		int xa = 0, ya = 0;

		if (Keyboard.isKeyDown(GLFW_KEY_W)) {
			ya += 3;
			texture = up;
		}
		if (Keyboard.isKeyDown(GLFW_KEY_S)) {
			ya -= 3;
			texture = down;
		}
		if (Keyboard.isKeyDown(GLFW_KEY_A)) {
			xa -= 3;
			texture = left;
		}
		if (Keyboard.isKeyDown(GLFW_KEY_D)) {
			xa += 3;
			texture = right;
		}

		if (!Keyboard.isKeyDown(GLFW_KEY_W) && !Keyboard.isKeyDown(GLFW_KEY_S)
				&& !Keyboard.isKeyDown(GLFW_KEY_A) && !Keyboard.isKeyDown(GLFW_KEY_D)) {
			texture = new Texture("res/playerSprites/player_stay.png");
		}

		move(xa, ya);

		if (sword != null) sword.update();
	}

	public boolean collision(int xa, int ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (((int) position.x + xa) + c % 2 * 48) >> 6;
			int yt = (((int) position.y + ya) + c / 2 * 48 + 3) >> 6;
			if (level.getTile(xt, yt).isSolid()) solid = true;
			if (level.getTile(xt, yt) instanceof SwordTile) {
				this.sword = new Sword();
				level.add(sword);
				level.transformTile(xt, yt, 0xff160100);
			}

		}
		return solid;
	}

	public boolean mobCollision() {
		for (int i = 0; i < level.mobs.size(); i++) {
			if (Math.abs(level.mobs.get(i).getX() - position.x) <= 32 && Math.abs(level.mobs.get(i).getY() - position.y) <= 32
					)
				if (level.mobs.get(i).corrupted)
					return true;
		}
		return false;
	}

	@Override
	public void render(float x, float y) {
		texture.bind();
		ShaderContainer.PLAYER.enable();
		mesh.render();
		ShaderContainer.PLAYER.disable();
		texture.unbind();

		if (sword != null) sword.render(0, 0);
	}
	
	public void addSword() {
		this.sword = new Sword();
	}

	public Sword getSword() {
		return sword;
	}

}
