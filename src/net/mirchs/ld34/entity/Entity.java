package net.mirchs.ld34.entity;

import net.mirchs.dm8.graphics.Texture;
import net.mirchs.dm8.graphics.buffers.VertexArray;
import net.mirchs.dm8.math.Vector3f;
import net.mirchs.ld34.level.Level;

public abstract class Entity {
	
	protected float width, height;
	protected Vector3f position = new Vector3f();
	
	protected Texture texture;
	protected VertexArray mesh;
	
	protected Level level;
	
	protected boolean removed = false;
	
	public abstract void update();
	
	public abstract void render(float x, float y);
	
	public void init(Level level) {
		this.level = level;
	}
	
	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	protected void remove() {
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}

}
