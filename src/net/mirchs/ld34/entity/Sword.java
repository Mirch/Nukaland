package net.mirchs.ld34.entity;

import static org.lwjgl.glfw.GLFW.*;

import net.mirchs.dm8.graphics.Texture;
import net.mirchs.dm8.graphics.buffers.VertexArray;
import net.mirchs.dm8.input.Mouse;
import net.mirchs.dm8.math.Matrix4f;
import net.mirchs.dm8.math.Vector3f;
import net.mirchs.ld34.ShaderContainer;
import net.mirchs.ld34.entity.mob.Mob;
import net.mirchs.ld34.entity.mob.Phap;
import net.mirchs.ld34.entity.mob.Player;

public class Sword extends Entity {
	
	private double cooldown = 120;
	private double cCounter = 0;
	private boolean onCooldown = false;

	public Sword() {
		texture = new Texture("res/sword.png");

		this.width = texture.getWidth();
		this.height = texture.getHeight();

		float[] vertices = new float[] {
				0.0f, 0.0f, 0.21f,
				0.0f, height, 0.21f,
				width, height, 0.21f,
				width, 0.0f, 0.21f
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
		if(onCooldown)
			cCounter++;
		
		if(cCounter >= cooldown) {
			onCooldown = false;
			cCounter  =0;
		}
		
		ShaderContainer.SWORD.enable();
		ShaderContainer.SWORD.setUniform1i("click", 0);
		ShaderContainer.SWORD.disable();
		Player player = level.getPlayer();
		int px = (int) player.getX() >> 6;
		int py = (int) player.getY() >> 6;
		
		if(Mouse.isButtonDown(GLFW_MOUSE_BUTTON_1) && !onCooldown) {
			onCooldown = true;
			ShaderContainer.SWORD.enable();
			ShaderContainer.SWORD.setUniform1i("click", 1);
			ShaderContainer.SWORD.disable();
			for(int i=0;i<level.mobs.size();i++) {
				Mob mob = level.mobs.get(i);
				if(Math.abs(mob.getX() - player.getX()) <= 64*4 && Math.abs(mob.getY() - player.getY()) <= 64*4) {
					((Phap)mob).decorrupt();
				}
			}
		}

		level.decorruptTiles(px - 1, py - 1, px + 1, py + 1);
	}

	public void render(float x, float y) {
		texture.bind();
		ShaderContainer.SWORD.enable();
		Matrix4f result = Matrix4f.translate(new Vector3f(x + 25, y, 0.0f));
		ShaderContainer.SWORD.setUniformMat4f("ml_matrix", result);
		mesh.render();
		ShaderContainer.SWORD.disable();
		texture.unbind();
	}

}
