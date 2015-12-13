package net.mirchs.ld34.level;

import net.mirchs.dm8.graphics.Window;
import net.mirchs.dm8.math.Vector2f;
import net.mirchs.ld34.GameState;
import net.mirchs.ld34.entity.mob.Mob;
import net.mirchs.ld34.entity.mob.Phap;
import net.mirchs.ld34.entity.mob.Player;

public class CastleLevel extends Level {

	private Phap phap1 = new Phap(new Vector2f(42 * 64, 2 * 64));
	private Phap phap2 = new Phap(new Vector2f(21 * 64, 2 * 64));
	private Phap phap3 = new Phap(new Vector2f(22 * 64, 2 * 64));
	private Phap phap4 = new Phap(new Vector2f(13 * 64, 17 * 64));
	private Phap phap5 = new Phap(new Vector2f(9 * 64, 11 * 64));
	private Phap phap6 = new Phap(new Vector2f(26 * 64, 18 * 64));
	private Phap phap7 = new Phap(new Vector2f(36 * 64, 27 * 64));
	

	public CastleLevel(String path) {
		super(path);
		player = new Player(new Vector2f(36 * 64, 3 * 64));
		phap1.corrupt();
		phap2.corrupt();
		phap3.corrupt();
		phap4.corrupt();
		phap5.corrupt();
		phap6.corrupt();
		phap7.corrupt();

		add(phap1);
		add(phap2);
		add(phap3);
		add(phap4);
		add(phap5);
		add(phap6);
		add(phap7);

		add(player);
		setScroll((int) (player.getX() - Window.WIDTH / 2), (int) (player.getY() - Window.HEIGHT / 2));
	}

	public boolean isWon() {
		return won;
	}

	public boolean allClear() {
		for (int i = 0; i < mobs.size(); i++) {
			Mob mob = mobs.get(i);
			if (((Phap) mob).isCorrupted())
				return false;
		}
		return true;
	}

	public void update() {
		super.update();
		if (player.getY() > 10 * 64) {
			tiles[36 + 8 * width] = 0xff380902;
			tiles[37 + 8 * width] = 0xff380902;
		}
		if (player.getY() >= 27 * 64 && player.getSword() != null && allClear()) {
			GameState.changeState(GameState.WONLEVEL);
		}
	}

}
