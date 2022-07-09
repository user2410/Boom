package actors;

import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import loader.ResourceLoader;
import main.Game;
import math.Vector2;

public class Grid extends Actor{

	public final int PADDING_TOP = 15;
	public final int PADDING_LEFT = 15;
	public final int NUM_ROWS = 13;
	public final int NUM_COLS = 15;
	public final double TILE_SIZE = 48.0;
	
	private Tile[][] mTiles;
	
	private Player mPlayer;
	
	public Grid(Game game) {
		super(game);
		
		Vector2 defTileSize = new Vector2(TILE_SIZE, TILE_SIZE);
		mTiles = new Tile[NUM_ROWS][NUM_COLS];
		for(int i=0; i<NUM_ROWS; i++) {
			for(int j=0; j<NUM_COLS; j++) {
				mTiles[i][j] = new Tile(game, i*NUM_COLS + j);
				mTiles[i][j].setPosition(new Vector2(
						PADDING_TOP + j*TILE_SIZE, 
						PADDING_LEFT + i*TILE_SIZE));
				mTiles[i][j].setOriginalSize(defTileSize);
			}
		}
	}

	// Load from .map file
	public void loadMap(String filepath) {
        try{
        	InputStream is = ResourceLoader.load("/" + filepath);
        	if(is == null) throw new IOException();
        	DataInputStream in = new DataInputStream(is);
        	
        	for(int i=0; i<NUM_ROWS; i++) {
    			for(int j=0; j<NUM_COLS; j++) {
    				int type = in.readInt();
    				switch(type%4) {
    				case 0:
    					mTiles[i][j].setTileState(Tile.TileState.EDefault);
    					break;
    				case 1:
    					mTiles[i][j].setTileState(Tile.TileState.EBoxSat);
    					break;
    				case 2:
    					mTiles[i][j].setTileState(Tile.TileState.EBoxGo);
    					break;
    				case 3:
    					mTiles[i][j].setTileState(Tile.TileState.EBoxGo2);
    					break;
    				}
    			}
        	}
        	
        	in.close();
        } catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void processKeyboard(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_SPACE: case KeyEvent.VK_ENTER:
			mPlayer.setBomb();
			break;
		default:
			mPlayer.processKeyBoard(e);
		}
	}
	
	@Override
	public void updateActor(double deltaTime) {
		if(mPlayer.getState() == Actor.State.EDead)
			getGame().mGameOver = true;
	}
	
	public void setPlayer(Player player) {
		mPlayer = player;
	}
	
	public void setPlayerTile(int x, int y) {
		if((x>=0 && x<NUM_COLS) && (y>=0 && y<NUM_ROWS)) {
			mPlayer.setCurrentTile(mTiles[x][y]);
		}
	}
	
	// Getters
	
	public int numberOfTiles() {
		return NUM_ROWS*NUM_COLS;
	}
	
	public Tile getTile(int tileNum) {
		Tile t = null;
		int i = tileNum / NUM_COLS;
		int j = tileNum % NUM_COLS;
		if((i>=0 && i<NUM_ROWS) &&
				(j>=0 && j<NUM_COLS)) {
			t = mTiles[i][j];
		}
		return t;
	}
	
	public void getAdjacentTiles(Tile t, ArrayList<Tile> arr) {
		// up, left, down, right
		arr.clear();
		int tnum = t.getTileNum();
		Tile neighbour = getTile(tnum-NUM_COLS);
		if(neighbour!=null) arr.add(neighbour);
		neighbour = getTile(tnum-1);
		if(neighbour!=null) arr.add(neighbour);
		neighbour = getTile(tnum+NUM_COLS);
		if(neighbour!=null) arr.add(neighbour);
		neighbour = getTile(tnum+1);
		if(neighbour!=null) arr.add(neighbour);
	}
}
