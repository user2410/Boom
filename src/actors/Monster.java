package actors;

import components.MoveComponent;
import components.PathFindingComponent;
import main.Game;

public class Monster extends Entity{

	protected int mDirection;
	protected PathFindingComponent mPF;
	protected MoveComponent mc;
	protected Tile dstTile;
	
	public Monster(Game game, Grid grid, Tile currentTile) {
		super(game, grid, currentTile);
		mPF = new PathFindingComponent(this, grid);
		mDirection = 2;
		updateTexture();
		mc = new MoveComponent(this);
		mc.setSpeed(150.0);
		dstTile = null;
		react(grid.mPlayer.getCurrentTile());
	}
	
	@Override
	public void updateActor(double deltaTime) {
		super.updateActor(deltaTime);
		mc.update(deltaTime);
		if(mc.isMoving()) {
			setPosition(mc.getCurPos());
		}else if(dstTile != null){
			setCurrentTile(dstTile);
			// set next dstTile
			Tile t = mPF.moveNext();
			if(t==dstTile) dstTile = null;
			else if(t!=null){
				int d = t.getTileNum() - dstTile.getTileNum();
				if(d == -mGrid.NUM_COLS) mDirection = 0;
				else if(d == -1) mDirection = 1;
				else if(d == 1) mDirection = 2;
				else if(d == mGrid.NUM_COLS) mDirection = 3;
				updateTexture();
				dstTile = t;
				mc.setSrc(getPosition());
				mc.setDst(dstTile.getPosition());
				mc.move();
			}
			
		}
	}
	
	public void react(Tile t) {
		mPF.setFromTile(mCurrentTile);
	}

	@Override
	public void die() {
		super.die();
	}
}
