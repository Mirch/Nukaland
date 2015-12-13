package net.mirchs.ld34.level;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import net.mirchs.dm8.graphics.Window;
import net.mirchs.dm8.graphics.buffers.VertexArray;
import net.mirchs.ld34.GameState;
import net.mirchs.ld34.entity.Entity;
import net.mirchs.ld34.entity.mob.Mob;
import net.mirchs.ld34.entity.mob.Player;
import net.mirchs.ld34.level.tile.CorruptedOriginTile;
import net.mirchs.ld34.level.tile.CorruptedTile;
import net.mirchs.ld34.level.tile.GrassTile;
import net.mirchs.ld34.level.tile.Tile;
import net.mirchs.ld34.level.tile.WaterTile;

public class Level {

	protected int width, height;
	protected String path;

	protected boolean won = false;
	
	protected VertexArray background;
	protected int xScroll, yScroll;
	protected int[] tiles;

	protected Player player;
	protected List<Entity> entities = new ArrayList<Entity>();
	public List<Mob> mobs = new ArrayList<Mob>();

	private int counter, timer = 1;
	protected boolean lost = false;

	public boolean started = false;

	public Level(String path) {
		this.path = path;
		load(path);
	}

	private void load(String path2) {
		BufferedImage image;
		try {
			image = ImageIO.read(new FileInputStream(path));
			this.width = image.getWidth();
			this.height = image.getHeight();
			tiles = new int[width * height];
			image.getRGB(0, 0, width, height, tiles, 0, width);

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int aux = tiles[x + y * width];
					tiles[x + y * width] = tiles[x + (height - y - 1) * width];
					tiles[x + (height - y - 1) * width] = aux;
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start() {
		started = true;
	}

	public void add(Entity e) {
		e.init(this);
		if (e instanceof Player)
			this.player = (Player) e;
		else if (e instanceof Mob)
			mobs.add((Mob) e);
		else
			entities.add(e);
	}

	public void setScroll(int xScroll, int yScroll) {
		this.xScroll += xScroll;
		this.yScroll += yScroll;
	}

	public void render() {

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if ((x << 6) - xScroll < -64 || (x << 6) - xScroll > Window.WIDTH) continue;
				if ((y << 6) - yScroll < -64 || (y << 6) - yScroll > Window.HEIGHT) continue;
				getTile(x, y).render((x << 6) - xScroll, (y << 6) - yScroll);
			}
		}

		for (int i = 0; i < mobs.size(); i++) {
			Mob mob = mobs.get(i);
			mob.render(-xScroll, -yScroll);
		}

		player.render(0, 0);
	}

	public void update() {
		counter++;
		if (counter % 60 == 0) timer++;

		player.update();
		if (player.mobCollision())
			lost = true;

		if (lost)
			GameState.state = GameState.LOST;

		for (int i = 0; i < mobs.size(); i++) {
			mobs.get(i).update();
		}

		for (int i = 0; i < tiles.length; i++) {
			Tile tile = getTile(i % width, i / width);
			if (counter % 100 == 0 && tile instanceof WaterTile) {
				if (tile.equals(Tile.waterTile1))
					tiles[i % width + i / width * height] = 0xff0000fe;
				else
					tiles[i % width + i / width * height] = 0xff0000ff;

			}
		}

		corruptTiles();

	}

	public void corruptTiles() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Tile tile = getTile(x, y);
				if (tile instanceof CorruptedTile && timer % 10 == 0) {
					int res = ((CorruptedTile) tile).corrupt();
					if (res == 0 && getTile(x, y + 1) instanceof GrassTile)
						tiles[x + (y + 1) * width] = 0xff2b2b2b;
					else if (res == 1 && getTile(x + 1, y) instanceof GrassTile)
						tiles[(x + 1) + y * width] = 0xff2b2b2b;
					else if (res == 2 && getTile(x, y - 1) instanceof GrassTile)
						tiles[x + (y - 1) * width] = 0xff2b2b2b;
					else if (res == 3 && getTile(x - 1, y) instanceof GrassTile)
						tiles[(x - 1) + y * width] = 0xff2b2b2b;
				}
			}
		}
	}

	public void decorruptTiles(int x1, int y1, int x2, int y2) {
		for (int y = y1; y < y2; y++) {
			for (int x = x1; x < x2; x++) {
				if (getTile(x, y + 1) instanceof CorruptedTile)
					tiles[x + (y + 1) * width] = 0xff168a47;
				else if (getTile(x + 1, y) instanceof CorruptedTile)
					tiles[(x + 1) + y * width] = 0xff168a47;
				else if (getTile(x, y - 1) instanceof CorruptedTile)
					tiles[x + (y - 1) * width] = 0xff168a47;
				else if (getTile(x - 1, y) instanceof CorruptedTile)
					tiles[(x - 1) + y * width] = 0xff168a47;
				
				
				
				
			}
		}
	}

	public boolean isWon() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (getTile(x, y) instanceof CorruptedTile)
					return false;
			}
		}
		return true;
	}

	public boolean isLost() {
		return lost;
	}

	public void transformTile(int x, int y, int value) {
		tiles[x + y * width] = value;
	}

	public Player getPlayer() {
		return player;
	}

	// fcff06
	// 7A1D4B
	// 2D1027
	public Tile getTile(float x, float y) {
		if (x < 0 || y < 0 || y >= height || x >= width) return Tile.nullTile;

		if (tiles[(int) (x + y * width)] == 0) return Tile.nullTile;
		else if (tiles[(int) (x + y * width)] == 0xffffffff) return Tile.wallTile;
		else if (tiles[(int) (x + y * width)] == 0xff168a47) return Tile.grassTile;
		else if (tiles[(int) (x + y * width)] == 0xffD6E014) return Tile.flowerTile;
		else if (tiles[(int) (x + y * width)] == 0xff2b2b2b) return Tile.corruptedTile;
		else if (tiles[(int) (x + y * width)] == 0xfffcff06) return Tile.swordTile;
		else if (tiles[(int) (x + y * width)] == 0xff0000ff) return Tile.waterTile1;
		else if (tiles[(int) (x + y * width)] == 0xff0000fe) return Tile.waterTile2;
		else if (tiles[(int) (x + y * width)] == 0xff160100) return Tile.floorTile;
		else if (tiles[(int) (x + y * width)] == 0xff7A1D4B) return Tile.armorTile;
		else if (tiles[(int) (x + y * width)] == 0xff380902) return Tile.doorTile;
		else if (tiles[(int) (x + y * width)] == 0xff49201C) return Tile.doorTileLeft;
		else if (tiles[(int) (x + y * width)] == 0xff8F5900) return Tile.doorTileRight;
		else if (tiles[(int) (x + y * width)] == 0xff2D1027) return Tile.corruptedTile;

		return Tile.nullTile;
	}

}
