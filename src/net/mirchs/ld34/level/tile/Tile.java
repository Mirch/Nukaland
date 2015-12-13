package net.mirchs.ld34.level.tile;

import net.mirchs.dm8.graphics.Texture;
import net.mirchs.dm8.graphics.buffers.VertexArray;
import net.mirchs.dm8.math.Matrix4f;
import net.mirchs.dm8.math.Vector3f;
import net.mirchs.ld34.ShaderContainer;
import net.mirchs.ld34.entity.mob.ArmorTile;

public class Tile {
	
	public static Tile nullTile = new NullTile(new Texture("res/tiles/nullTile.png"));
	public static Tile wallTile = new WallTile(new Texture("res/tiles/wallTile.png"));
	public static Tile grassTile = new GrassTile(new Texture("res/tiles/grassTile.png"));
	public static Tile flowerTile = new GrassTile(new Texture("res/tiles/flowerTile.png"));
	public static Tile corruptedTile = new CorruptedTile(new Texture("res/tiles/corruptedTile.png"));
	public static Tile corruptedOriginTile = new CorruptedOriginTile(new Texture("res/tiles/corruptedOrigin.png"));
	
	public static Tile armorTile = new ArmorTile(new Texture("res/tiles/armorTile.png"));
	public static Tile swordTile = new SwordTile(new Texture("res/tiles/sword.png"));
	
	public static Tile waterTile1 = new WaterTile(new Texture("res/tiles/water1.png"));
	public static Tile waterTile2 = new WaterTile(new Texture("res/tiles/water2.png"));
	public static Tile floorTile = new FloorTile(new Texture("res/tiles/floor.png"));
	
	public static Tile doorTile = new DoorTile(new Texture("res/tiles/door1.png"), 0);
	public static Tile doorTileLeft = new DoorTile(new Texture("res/tiles/door2.png"), 1);
	public static Tile doorTileRight = new DoorTile(new Texture("res/tiles/door3.png"), 1);
	
	
	
	

	protected Texture texture;
	protected int width, height;
	protected VertexArray mesh;
	
	public Tile(Texture texture) {
		this.texture = texture;
		width = texture.getWidth();
		height = texture.getHeight();
		
		float[] vertices = new float[] {
				0.0f, 0.0f, 0.1f,
				0.0f, height, 0.1f,
				width, height, 0.1f,
				width, 0.0f, 0.1f
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
	
	public void render(float x, float y) {
		texture.bind();
		ShaderContainer.TILE.enable();
		ShaderContainer.TILE.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(x, y, 0.0f)));
		mesh.render();
		ShaderContainer.TILE.disable();
		texture.unbind();
	}
	
	public void update() {
		
	}
	
	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
	
	public boolean isSolid() {
		return false;
	}

}
