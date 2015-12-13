package net.mirchs.ld34.level;

import java.util.Random;

import net.mirchs.dm8.graphics.Window;
import net.mirchs.dm8.math.Vector2f;
import net.mirchs.ld34.GameState;
import net.mirchs.ld34.entity.mob.Phap;
import net.mirchs.ld34.entity.mob.Player;
import net.mirchs.ld34.level.tile.CorruptedTile;
import net.mirchs.ld34.level.tile.GrassTile;

public class GardenLevel extends Level {

	private boolean hasOrigin = false;
	private final Random random = new Random();

	private Phap phap1 = new Phap(new Vector2f(22 * 64, 34 * 64));
	private Phap phap2 = new Phap(new Vector2f(39 * 64, 25 * 64));
	private Phap phap3 = new Phap(new Vector2f(46 * 64, 16 * 64));
	private Phap phap4 = new Phap(new Vector2f(47 * 64, 16 * 64));
	private Phap phap5 = new Phap(new Vector2f(47 * 64, 17 * 64));
	private Phap phap6 = new Phap(new Vector2f(26 * 64, 18 * 64));
	private Phap phap7 = new Phap(new Vector2f(18 * 64, 19 * 64));
	private Phap phap8 = new Phap(new Vector2f(17 * 64, 19 * 64));
	private Phap phap9 = new Phap(new Vector2f(34 * 64, 18 * 64));
	private Phap phap10 = new Phap(new Vector2f(47 * 64, 23 * 64));
	private Phap phap11 = new Phap(new Vector2f(9 * 64, 4 * 64));
	private Phap phap12 = new Phap(new Vector2f(25 * 64, 4 * 64));
	private Phap phap13 = new Phap(new Vector2f(18 * 64, 45 * 64));
	private Phap phap14 = new Phap(new Vector2f(26 * 64, 26 * 64));
	private Phap phap15 = new Phap(new Vector2f(40 * 64, 42 * 64));
	private Phap phap16 = new Phap(new Vector2f(4 * 64, 39 * 64));
	
	
	

	public GardenLevel(String path) {
		super(path);
		player = new Player(new Vector2f(24 * 64, 47 * 64));
		player.addSword();
		add(player);
		add(player.getSword());

		phap1.corrupt();
		phap2.corrupt();
		phap3.corrupt();
		phap4.corrupt();
		phap5.corrupt();
		phap6.corrupt();
		phap7.corrupt();
		phap8.corrupt();
		phap9.corrupt();
		phap10.corrupt();
		phap11.corrupt();
		phap12.corrupt();
		phap13.corrupt();
		phap14.corrupt();
		phap15.corrupt();
		phap16.corrupt();
		

		add(phap1);
		add(phap2);
		add(phap3);
		add(phap4);
		add(phap5);
		add(phap6);
		add(phap7);
		add(phap8);
		add(phap9);
		add(phap10);
		add(phap11);
		add(phap12);
		add(phap13);
		add(phap14);
		add(phap15);
		add(phap16);
		

		setScroll((int) (player.getX() - Window.WIDTH / 2), (int) (player.getY() - Window.HEIGHT / 2));
		
	}

	public boolean isWon() {
		return won;
	}

	public boolean isLost() {

		for (int i = 0; i < tiles.length; i++) {
			if (getTile(i % width, i / width) instanceof GrassTile)
				return false;
		}
		return true;

	}

	public void update() {
		super.update();
		if (isLost())
			lost = true;
		
		won = true;
		for(int i =0;i<tiles.length;i++) {
			if(getTile(i%width, i/width) instanceof CorruptedTile)
				won = false;
		}

		if (won)
			GameState.changeState(GameState.WON);
	}

}
