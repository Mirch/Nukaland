package net.mirchs.ld34.screen;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.glfw.GLFW.*;

import net.mirchs.dm8.graphics.Texture;
import net.mirchs.dm8.graphics.buffers.VertexArray;
import net.mirchs.dm8.input.Keyboard;
import net.mirchs.ld34.Game;
import net.mirchs.ld34.GameState;
import net.mirchs.ld34.ShaderContainer;
import net.mirchs.ld34.level.CastleLevel;
import net.mirchs.ld34.level.GardenLevel;
import net.mirchs.ld34.level.scene.Scene;

public class Screen {

	private String name;
	private Texture texture;
	private VertexArray mesh;
	private int width, height;

	private int counter, timer;
	private int timeCounter;

	private int selection = 1;
	private boolean exit = false;

	public static Texture play = new Texture("res/startScreens/startScreen1.png");
	public static Texture about = new Texture("res/startScreens/startScreen2.png");
	public static Texture quit = new Texture("res/startScreens/startScreen3.png");

	public Screen(String name, Texture texture) {
		this.name = name;
		this.texture = texture;

		this.width = 1280;
		this.height = 720;

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

	public void goDown() {
		if (texture == play) {
			texture = about;
			selection = 2;
			return;
		} else if (texture == about) {
			texture = quit;
			selection = 3;
			return;
		} else if (texture == quit) {
			texture = play;
			selection = 1;
			return;
		}
	}

	public void goUp() {
		if (texture == play) {
			texture = quit;
			selection = 3;
			return;
		} else if (texture == about) {
			texture = play;
			selection = 1;
			return;
		} else if (texture == quit) {
			texture = about;
			selection = 2;
			return;
		}
	}

	public boolean shouldExit() {
		return exit;
	}

	public void update() {
		counter++;

		if ((Keyboard.isKeyDown(GLFW_KEY_S ) || Keyboard.isKeyDown(GLFW_KEY_DOWN)) && counter - timer > 10) {
			timer = counter;
			goDown();
		} else if ((Keyboard.isKeyDown(GLFW_KEY_W) || Keyboard.isKeyDown(GLFW_KEY_UP)) && counter - timer > 10) {
			timer = counter;
			goUp();
		}

		if (Keyboard.isKeyDown(GLFW_KEY_ENTER)) {
			if (this.name.equals("menu")) {
				if (selection == 1){
					GameState.changeState(GameState.GAME);
					Game.currentLevel = Game.level1;
				}
				else if (selection == 2)
					GameState.changeState(GameState.HOWTO);
				else
					GameState.changeState(GameState.EXIT);
			} else if (this.name.equals("wonLevel")) {
				GameState.changeState(GameState.GAME);
				if (Game.currentLevel == Game.level1)
					Game.currentLevel = Game.level2;
			}
		}

		if (Keyboard.isKeyDown(GLFW_KEY_W) || Keyboard.isKeyDown(GLFW_KEY_A) || Keyboard.isKeyDown(GLFW_KEY_S)
				|| Keyboard.isKeyDown(GLFW_KEY_D)) {
			if (this.name.equals("wonLevel")) {
				GameState.changeState(GameState.GAME);
				if (Game.currentLevel == Game.level1)
					Game.currentLevel = Game.level2;
			}
		}

	}

	public void render() {
		texture.bind();
		ShaderContainer.SCREEN.enable();
		mesh.render();
		ShaderContainer.SCREEN.disable();
		texture.unbind();
	}

}
