package actors.monsters;

import actors.Grid;
import actors.Monster;
import actors.Tile;
import main.Game;
import math.Vector2;

public class Monster2 extends Monster {
	
	public Monster2(Game game, Grid grid, Tile currentTile) {
		super(game, grid, currentTile);
	}
	
	@Override
	protected void updateTexture() {
		String text = "";
		switch(mDirection) {
		case 0:
			text = "monster/quaivat2_up.png";
			break;
		case 1:
			text = "monster/quaivat2_left.png";
			break;
		case 2:
			text = "monster/quaivat2_down.png";
			break;
		case 3:
			text = "monster/quaivat2_right.png";
			break;
		}
		mSprite.setTexture(getGame().getTexture(text));
	}

	@Override
	protected Vector2 getPlayerPos() {
		Tile playerTile = mGrid.getPlayer().getCurrentTile();
		int playerTileNum = playerTile.getTileNum();
		int playerDirection = mGrid.getPlayer().getDirection();
		Tile dstTile = playerTile;
		Tile t;
		
		switch(playerDirection) {
		case 0:
			t = mGrid.getTile(playerTileNum-mGrid.getncols()); 
			if(t!=null) {
				dstTile = t;
				t = mGrid.getTile(t.getTileNum()-mGrid.getncols());
				if(t!=null)  dstTile = t;
			}
			break;
		case 1:
			t = mGrid.getTile(playerTileNum-1); 
			if(t!=null) {
				dstTile = t;
				t = mGrid.getTile(t.getTileNum()-1);
				if(t!=null)  dstTile = t;
			}
			break;
		case 2:
			t = mGrid.getTile(playerTileNum+mGrid.getncols()); 
			if(t!=null) {
				dstTile = t;
				t = mGrid.getTile(t.getTileNum()+mGrid.getncols());
				if(t!=null)  dstTile = t;
			}
			break;
		case 3:
			t = mGrid.getTile(playerTileNum+1); 
			if(t!=null) {
				dstTile = t;
				t = mGrid.getTile(t.getTileNum()+1);
				if(t!=null)  dstTile = t;
			}
			break;
		}
		
		return dstTile.getPosition();
	}

	@Override
	protected void attack() {
		mGrid.getPlayer().sustain(5.0);
	}
}
