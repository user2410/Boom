package actors;

import components.EntitySpriteComponent;
import main.Game;

public class Bomb extends Entity{

	private Player mOwner;
	private final int BLOW_RANGE = 2;
	private double mTimeToLive;
	
	public Bomb(Game game, Grid grid, Player player) {
		super(game, grid, player.getCurrentTile());
		mTimeToLive = 3.0;
		mSprite = new EntitySpriteComponent(this, grid);
		mSprite.setDrawOrder(98);
		mSprite.setTexture(game.getTexture("objects/bomb.gif"));
	}

	@Override
	public void updateActor(double deltaTime) {
		mTimeToLive -= deltaTime;
		if(mTimeToLive < 0.0) {
			die();
		}
	}

	private void blow() {
		// get all adjacent tiles
		int tnum = mCurrentTile.getTileNum();
		int i = tnum / mGrid.NUM_COLS;
		int j = tnum % mGrid.NUM_COLS;
		
		mCurrentTile.removeEntity(this);
		mCurrentTile.blow();
		
		for(int r=1; r<=BLOW_RANGE; r++) {
			if(i >= r) {
				Tile t = mGrid.getTile(tnum-r);
				if(t==null) break;
				if(!t.isBlowable()) break;
				if(t.isBlocked()) r = BLOW_RANGE;
				t.blow();
			}
		}
		
		for(int r=1; r<=BLOW_RANGE; r++) {
			if(i+r < mGrid.NUM_ROWS) {
				Tile t = mGrid.getTile(tnum+r*mGrid.NUM_COLS);
				if(t==null) break;
				if(!t.isBlowable()) break;
				if(t.isBlocked()) r = BLOW_RANGE;				
				t.blow();
			}
		}
		
		for(int r=1; r<=BLOW_RANGE; r++) {
			if(j >= r) {
				Tile t = mGrid.getTile(tnum-r*mGrid.NUM_COLS);
				if(t==null) break;
				if(!t.isBlowable()) break;
				if(t.isBlocked()) r = BLOW_RANGE;
				t.blow();
			}
		}
		
		for(int r=1; r<=BLOW_RANGE; r++) {
			if(j+r < mGrid.NUM_COLS) {
				Tile t = mGrid.getTile(tnum+r);
				if(t==null) break;
				if(!t.isBlowable()) break;
				if(t.isBlocked()) r = BLOW_RANGE;
				t.blow();
			}
		}
	}
	
	@Override
	public void die() {
		super.die();
		blow();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Bomb) {
			Bomb b = (Bomb)o;
			return this.mOwner == b.mOwner && this.mCurrentTile == b.mCurrentTile;
		}
		return false;
	}
	
}