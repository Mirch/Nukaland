package net.mirchs.ld34;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.io.File;

import net.mirchs.dm8.graphics.Texture;
import net.mirchs.dm8.graphics.Window;
import net.mirchs.dm8.input.Keyboard;
import net.mirchs.dm8.math.Matrix4f;
import net.mirchs.dm8.math.Vector3f;
import net.mirchs.ld34.entity.mob.Player;
import net.mirchs.ld34.level.CastleLevel;
import net.mirchs.ld34.level.GardenLevel;
import net.mirchs.ld34.level.Level;
import net.mirchs.ld34.level.scene.Scene;
import net.mirchs.ld34.screen.Screen;
import net.mirchs.ld34.sound.Sound;

public class Game implements Runnable {

	private Window window;
	private Thread thread;
	private boolean running;

	private Screen startScreen;
	private Screen howToPlayScreen;

	public static Level currentLevel;
	public static Level level1;
	public static Level level2;
	public static Scene openingScene;

	private boolean reset = false;

	private Player player;

	private Screen winLevel;
	private Screen winGame;
	private Screen lostGame;

	private Sound sound;

	public void start() {
		running = true;
		// sound = new Sound("res/sound/menu.wav");
		thread = new Thread(this, "Game");
		thread.start();
	}

	public void init() {
		window = new Window();

		glClearColor(0.3f, 0.5f, 0.7f, 1.0f);
		glActiveTexture(GL_TEXTURE1);

		Matrix4f pr_matrix = Matrix4f.ortho(0, window.WIDTH, 0, window.HEIGHT, -1.0f, 1.0f);

		ShaderContainer.PLAYER.setUniformMat4f("pr_matrix", pr_matrix);
		ShaderContainer.PLAYER.setUniform1i("tex", 1);

		ShaderContainer.MOOMU.setUniformMat4f("pr_matrix", pr_matrix);
		ShaderContainer.MOOMU.setUniform1i("tex", 1);

		ShaderContainer.TILE.setUniformMat4f("pr_matrix", pr_matrix);
		ShaderContainer.TILE.setUniform1i("tex", 1);

		ShaderContainer.SWORDTILE.setUniformMat4f("pr_matrix", pr_matrix);
		ShaderContainer.SWORDTILE.setUniform1i("tex", 1);

		ShaderContainer.PHAP.setUniformMat4f("pr_matrix", pr_matrix);
		ShaderContainer.PHAP.setUniform1i("tex", 1);

		ShaderContainer.SWORD.setUniformMat4f("pr_matrix", pr_matrix);
		ShaderContainer.SWORD.setUniform1i("tex", 1);

		ShaderContainer.SCREEN.setUniformMat4f("pr_matrix", pr_matrix);
		ShaderContainer.SCREEN.setUniform1i("tex", 1);

		startScreen = new Screen("menu", Screen.play);
		howToPlayScreen = new Screen("howto", new Texture("res/screens/htpScreen.png"));

		level1 = new CastleLevel("res/level1.png");
		level2 = new GardenLevel("res/level.png");

		winLevel = new Screen("wonLevel", new Texture("res/screens/winLevel.png"));
		winGame = new Screen("wonGame", new Texture("res/screens/winningScreen.png"));

		lostGame = new Screen("lost", new Texture("res/screens/losingScreen.png"));

		ShaderContainer.PLAYER.enable();
		ShaderContainer.PLAYER.setUniformMat4f("vw_matrix",
				Matrix4f.translate(new Vector3f(Window.WIDTH / 2, Window.HEIGHT / 2, 0.0f)));
		ShaderContainer.PLAYER.disable();

		ShaderContainer.SWORD.enable();
		ShaderContainer.SWORD.setUniformMat4f("vw_matrix",
				Matrix4f.translate(new Vector3f(Window.WIDTH / 2, Window.HEIGHT / 2, 0.0f)));
		ShaderContainer.SWORD.disable();

		ShaderContainer.MOOMU.enable();
		ShaderContainer.MOOMU.setUniformMat4f("vw_matrix",
				Matrix4f.translate(new Vector3f(Window.WIDTH / 2, Window.HEIGHT / 2, 0.0f)));
		ShaderContainer.MOOMU.disable();

		ShaderContainer.FADE.enable();
		ShaderContainer.FADE.setUniformMat4f("vw_matrix",
				Matrix4f.translate(new Vector3f(Window.WIDTH / 2, Window.HEIGHT / 2, 0.0f)));
		ShaderContainer.FADE.disable();
	}

	public void run() {
		init();
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				delta--;
			}
			render();

			if (Keyboard.isKeyDown(GLFW_KEY_ESCAPE)) {

				glfwSetWindowShouldClose(window.getID(), GL_TRUE);
			}
			if (glfwWindowShouldClose(window.getID()) == GL_TRUE) {
				running = false;

			}
		}
	}

	public void update() {

		if (reset) {
			startScreen = new Screen("menu", Screen.play);
			level1 = new CastleLevel("res/level1.png");
			level2 = new GardenLevel("res/level.png");
			reset = false;
		}

		if (GameState.state == GameState.MENU)
			startScreen.update();
		else if (GameState.state == GameState.WONLEVEL) {
			winLevel.update();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (GameState.state == GameState.WON) {
			winGame.update();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GameState.changeState(GameState.MENU);
			reset = true;

		} else if (GameState.state == GameState.LOST) {
			level1 = null;
			level2 = null;
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GameState.changeState(GameState.MENU);
			reset = true;

		} else if (GameState.state == GameState.HOWTO) {
			howToPlayScreen.update();
			if (Keyboard.isKeyDown(GLFW_KEY_BACKSPACE))
				GameState.changeState(GameState.MENU);
		} else if (GameState.state == GameState.EXIT) {
			glfwSetWindowShouldClose(window.getID(), GL_TRUE);
		} else if (GameState.state == GameState.GAME) {
			if (this.currentLevel == this.level1) {
				if (Keyboard.isKeyDown(GLFW_KEY_TAB))
					this.currentLevel = level2;
			}
			currentLevel.update();
		}
		glfwPollEvents();
	}

	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		if (GameState.state == GameState.MENU)
			startScreen.render();
		else if (GameState.state == GameState.WONLEVEL)
			winLevel.render();
		else if (GameState.state == GameState.WON)
			winGame.render();
		else if (GameState.state == GameState.LOST)
			lostGame.render();
		else if (GameState.state == GameState.HOWTO) {
			howToPlayScreen.render();
		} else if (GameState.state == GameState.GAME) {
			currentLevel.render();
		}

		glfwSwapBuffers(window.getID());
	}

	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath", new File("lib/natives").getAbsolutePath());
		new Game().start();
	}

}
