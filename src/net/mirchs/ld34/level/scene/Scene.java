package net.mirchs.ld34.level.scene;

import net.mirchs.dm8.graphics.Window;
import net.mirchs.dm8.math.Vector2f;
import net.mirchs.ld34.Game;
import net.mirchs.ld34.GameState;
import net.mirchs.ld34.ShaderContainer;
import net.mirchs.ld34.entity.mob.ControllablePhap;
import net.mirchs.ld34.level.Level;
import net.mirchs.ld34.level.tile.CorruptedTile;

public class Scene extends Level {

	private ControllablePhap phap1, phap2, phap3, phap4, phap5;
	private int counter;
	

	public Scene(String path) {
		super(path);
		phap1 = new ControllablePhap(new Vector2f(9 * 64, 9 * 64));
		phap2 = new ControllablePhap(new Vector2f(9 * 64, 1 * 64));
		phap3 = new ControllablePhap(new Vector2f(6 * 64, 5 * 64));
		phap4 = new ControllablePhap(new Vector2f(12 * 64, 5 * 64));
		
		phap1.corrupt();
		phap2.corrupt();
		phap3.corrupt();
		phap4.corrupt();
		
		phap5 = new ControllablePhap(new Vector2f(9 * 64, 5 * 64));
		
		
		add(phap1);
		add(phap2);
		add(phap3);
		add(phap4);
		add(phap5);
		

		happen();
	}

	public void happen() {
		phap1.setMove(new Vector2f(9 * 64, 6 * 64));
		phap2.setMove(new Vector2f(9 * 64, 4 * 64));
		phap3.setMove(new Vector2f(8 * 64, 5*64));
		phap4.setMove(new Vector2f(10 * 64, 5*64));
		
	}

	public boolean isWon() {
		if (phap1.reached() && phap2.reached() && phap3.reached() && phap4.reached() && counter%220 == 0) {
			System.out.println("done");
			GameState.changeState(GameState.GAME);
			Game.currentLevel = Game.level1;
			return true;
		}
		return false;
	}
	
	
	public void update() {
		counter++;
		
		for(int i=0;i<tiles.length;i++) {
			getTile(i%width, i/width).update();
			if(getTile(i%width, i/width) instanceof CorruptedTile)
				((CorruptedTile)getTile(i%width, i/width)).corruptForSure();
		}
		
		phap1.update();
		phap2.update();
		phap3.update();
		phap4.update();
		phap5.update();
		
		if(counter%40 ==0 && !phap5.isCorrupted()) phap5.lookLeft();
		if(counter%80 ==0 && !phap5.isCorrupted()) phap5.lookRight();
		
		if (phap1.reached() && phap2.reached() && phap3.reached() && phap4.reached() && counter%150==0) {
			phap5.corrupt();
		}
		
		isWon();
		
		
	}

	public void render() {

		Vector2f cent = new Vector2f(Window.WIDTH / 2, Window.HEIGHT / 2);
		ShaderContainer.TILE.enable();
		ShaderContainer.TILE.setUniform2f("cent", cent.x, cent.y);
		ShaderContainer.TILE.disable();
		
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if ((x << 6) - xScroll < -64 || (x << 6) - xScroll > Window.WIDTH) continue;
				if ((y << 6) - yScroll < -64 || (y << 6) - yScroll > Window.HEIGHT) continue;
				getTile(x, y).render((x << 6) - xScroll, (y << 6) - yScroll);
			}
		}

		phap1.render();
		phap2.render();
		phap3.render();
		phap4.render();
		phap5.render();
		
		
	}
}
