package actors;

import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import actors.monsters.Monster1;
import actors.monsters.Monster2;
import loader.ResourceLoader;
import main.Game;
import math.Vector2;

public class Grid extends Actor{

	public final int PADDING_TOP = 25;
	public final int PADDING_LEFT = 15;
	public final int NUM_ROWS = 13;
	public final int NUM_COLS = 15;
	public final double TILE_SIZE = 48.0;
	
	private Tile[][] mTiles;
	
	Player mPlayer;
	
	ArrayList<Monster> mMonsters;
	
	public Grid(Game game) {
		super(game);
		
		Vector2 defTileSize = new Vector2(TILE_SIZE, TILE_SIZE);
		mTiles = new Tile[NUM_ROWS][NUM_COLS];
		for(int i=0; i<NUM_ROWS; i++) {
			for(int j=0; j<NUM_COLS; j++) {
				mTiles[i][j] = new Tile(game, i*NUM_COLS + j);
				mTiles[i][j].setPosition(new Vector2(
						PADDING_LEFT + j*TILE_SIZE, 
						PADDING_TOP + i*TILE_SIZE));
				mTiles[i][j].setOriginalSize(defTileSize);
			}
		}
		
		mMonsters = new ArrayList<Monster>();
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
        	
        	int x = in.readInt();
        	int y = in.readInt();
       	
        	mPlayer = new Player(getGame(), this, getTile(x, y));
        	
        	int m1 = in.readInt();
        	for(int i=0; i<m1; i++) {
        		x = in.readInt();
        		y = in.readInt();
        		mMonsters.add(new Monster1(getGame(), this, getTile(x, y)));
        		// System.out.println("x="+x+", y="+y);
        	}

        	int m2 = in.readInt();
        	for(int i=0; i<m2; i++) {
        		x = in.readInt();
        		y = in.readInt();
        		mMonsters.add(new Monster2(getGame(), this, getTile(x, y)));
        		// System.out.println("x="+x+", y="+y);
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
		if(mPlayer.getState() == Actor.State.EDead) {
			getGame().mVictory = false;
			getGame().mGameOver = true;
		}
		if(mMonsters.isEmpty()) {
			getGame().mVictory = true;
			getGame().mGameOver = true;			
		}
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
	
	public Tile getTile(int i, int j) {
		Tile t = null;
		if((i>=0 && i<NUM_ROWS) &&
				(j>=0 && j<NUM_COLS)) {
			t = mTiles[i][j];
		}
		return t;
	}
	
	public Tile getTile(int tileNum) {
		int i = tileNum / NUM_COLS;
		int j = tileNum % NUM_COLS;
		return getTile(i,j);
	}
	
	public void getAdjacentTiles(Tile t, ArrayList<Tile> arr) {
		// up, left, down, right
		arr.clear();
		int tnum = t.getTileNum();
		int i = tnum / NUM_COLS;
		int j = tnum % NUM_COLS;
		Tile neighbour = getTile(i-1, j);
		if(neighbour!=null) if(!neighbour.isBlocked()) arr.add(neighbour);
		neighbour = getTile(i, j-1);
		if(neighbour!=null) if(!neighbour.isBlocked()) arr.add(neighbour);
		neighbour = getTile(i+1, j);
		if(neighbour!=null) if(!neighbour.isBlocked()) arr.add(neighbour);
		neighbour = getTile(i, j+1);
		if(neighbour!=null) if(!neighbour.isBlocked()) arr.add(neighbour);
	}

	public Player getPlayer() {
		return mPlayer;
	}	
	
}
