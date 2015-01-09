package ted.gamename.level;

import ted.gamename.graphics.Screen;
import ted.gamename.level.tile.Tile;

public class Level {
	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];

		generateLevel();
	}

	public Level(String path) {
		loadLevel(path);
	}

	protected void generateLevel() {

	}

	protected void loadLevel(String path) {

	}

	public void update() {

	}

	public void time() {

	}

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;

		//Cycles through all tiles between top to bottom and right to left
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);

			}
		}
	}

	
	// Grass = 0xFF00FF00
	// Flower = 0xFFFFFF00
	// Rock = 0xFF658700
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height){
			return Tile.voidTile;
		}
		if (tiles[x + (y * width)] == 0xFF00FF00) {
			return Tile.grass;
		}

		if (tiles[x + (y * width)] == 0xFFFFFF00) {
			return Tile.flower;
		}
		
		if (tiles[x + (y * width)] == 0xFF658700) {
			return Tile.rock;
		}
		return Tile.voidTile;

	}
}
