package actors;

import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import actors.monsters.Monster1;
import actors.monsters.Monster2;
import loader.ResourceLoader;
import math.Vector2;
import scene.MainGameScene;

public class Grid extends Actor{

	public static final int PADDING_TOP = 25;
	public static final int PADDING_BOTTOM = 25;
	public static final int PADDING_LEFT = 15;
	public static final int PADDING_RIGHT = 15;
	public static final double TILE_SIZE = 48.0;
	private int NUM_COLS;
	private int NUM_ROWS;
	
	private Tile[][] mTiles;
	
	// initial grid state
	private int[][] mTileInit;
	private int mPlayerXInit, mPlayerYInit;
	private int[][] mMonster1Init;
	private int[][] mMonster2Init;
	
	Player mPlayer;
	
	ArrayList<Monster> mMonsters;
	
	private Grid(MainGameScene scene) {
		super(scene);
	}
	
	public static Grid makeGrid(MainGameScene scene, String mapfile) {
		Grid g = new Grid(scene);
		
		// Load from .map file
		try{
        	InputStream is = ResourceLoader.load("/" + mapfile);
        	if(is == null) throw new IOException();
        	DataInputStream in = new DataInputStream(is);
        	
        	// load map size
        	g.NUM_ROWS = in.readInt();
        	g.NUM_COLS = in.readInt();
        	g.mTileInit = new int[g.NUM_ROWS][g.NUM_COLS];
        	// System.out.println("m = " + g.NUM_ROWS + "; n = " + g.NUM_COLS);
        	
        	// init tile map
        	g.mTiles = new Tile[g.NUM_ROWS][g.NUM_COLS];
    		Vector2 defTileSize = new Vector2(TILE_SIZE, TILE_SIZE);
    		
        	// load each tile
        	for(int i=0; i<g.NUM_ROWS; i++) {
    			for(int j=0; j<g.NUM_COLS; j++) {
    				g.mTiles[i][j] = new Tile(scene, i*g.NUM_COLS + j);
    				g.mTiles[i][j].setPosition(new Vector2(
    						PADDING_LEFT + j*TILE_SIZE, 
    						PADDING_TOP + i*TILE_SIZE));
    				g.mTiles[i][j].setOriginalSize(defTileSize);
    				
    				int type = in.readInt()%4;
    				g.mTileInit[i][j] = type;
    				
    				// System.out.print(""+type+",");
    				switch(type) {
    				case 0:
    					g.mTiles[i][j].setTileState(Tile.TileState.EDefault);
    					break;
    				case 1:
    					g.mTiles[i][j].setTileState(Tile.TileState.EBoxSat);
    					break;
    				case 2:
    					g.mTiles[i][j].setTileState(Tile.TileState.EBoxGo);
    					break;
    				case 3:
    					g.mTiles[i][j].setTileState(Tile.TileState.EBoxGo2);
    					break;
    				}
    			}
    			// System.out.println("");
        	}
        	
    		g.mMonsters = new ArrayList<Monster>();
    		
        	// load player's initial tile
        	int x = in.readInt();
        	int y = in.readInt();
        	g.mPlayerXInit = x;
        	g.mPlayerYInit = y;
       	
        	g.mPlayer = new Player(scene, g, g.getTile(x, y));
        	
        	// load type-1 monsters position
        	int m1 = in.readInt();
        	g.mMonster1Init = new int[m1][2];
        	for(int i=0; i<m1; i++) {
        		x = in.readInt();
        		y = in.readInt();
        		g.mMonster1Init[i][0] = x;
        		g.mMonster1Init[i][1] = y;
        		g.mMonsters.add(new Monster1(scene, g, g.getTile(x, y)));
        		// System.out.println("x="+x+", y="+y);
        	}

        	// load type-2 monsters position
        	int m2 = in.readInt();
        	g.mMonster2Init = new int[m2][2];
        	for(int i=0; i<m2; i++) {
        		x = in.readInt();
        		y = in.readInt();
        		g.mMonster2Init[i][0] = x;
        		g.mMonster2Init[i][1] = y;
        		g.mMonsters.add(new Monster2(scene, g, g.getTile(x, y)));
        		// System.out.println("x="+x+", y="+y);
        	}
        	in.close();
        } catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return g;
	}
	
	public void resetGrid() {
		// reset tiles
		for(int i=0; i<NUM_ROWS; i++) {
			for(int j=0; j<NUM_COLS; j++) {
				mTiles[i][j].clear();
				switch(mTileInit[i][j]) {
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
		
		// reset player
		mPlayer = new Player((MainGameScene)getScene(), this, getTile(mPlayerXInit, mPlayerYInit));
		
		// reset monsters
		mMonsters.clear();
		for(int i=0; i<mMonster1Init.length; i++) {
			mMonsters.add(new Monster1((MainGameScene)getScene(), this, getTile(mMonster1Init[i][0], mMonster1Init[i][1])));
		}
		for(int i=0; i<mMonster2Init.length; i++) {
			mMonsters.add(new Monster2((MainGameScene)getScene(), this, getTile(mMonster2Init[i][0], mMonster2Init[i][1])));
		}
	}
	
	public synchronized void processKeyboard(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_ENTER:
			mPlayer.setBomb();
			break;
		default:
			mPlayer.processKeyBoard(e);
		}
	}
	
	@Override
	public void updateActor(double deltaTime) {
		if(mPlayer.getState() == Actor.State.EDead) {
			((MainGameScene)getScene()).endGame(false);
		}
		if(mMonsters.isEmpty()) {
			((MainGameScene)getScene()).endGame(true);		
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
	
	public int getncols() {
		return NUM_COLS;
	}

	public int getnrows() {
		return NUM_ROWS;
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
