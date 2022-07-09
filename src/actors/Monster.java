package actors;

import java.util.ArrayList;

import components.MoveComponent;
import main.Game;
import math.Vector2;

public abstract class Monster extends Entity{

	protected int mDirection;
	protected MoveComponent mc;
	protected Tile nextTile;
	protected ArrayList<Tile> adjTiles;
	private double attackCooldown;
	
	public Monster(Game game, Grid grid, Tile currentTile) {
		super(game, grid, currentTile);
		mDirection = 2;
		updateTexture();
		mc = new MoveComponent(this);
		mc.setSpeed(40.0);
		adjTiles = new ArrayList<Tile>();
		attackCooldown = 0.0;
		pollNextTile();
	}
	
	@Override
	public void updateActor(double deltaTime) {
		super.updateActor(deltaTime);
		attackCooldown -= deltaTime;
		if(attackCooldown <= 0.0) {
			if(Vector2.sub(mGrid.mPlayer.getPosition(), getPosition()).length()<=mGrid.TILE_SIZE) {
				attack();
				attackCooldown = 0.5;
			}else {
				attackCooldown = 0.0;
			}
		}
		mc.update(deltaTime);
		if(mc.isMoving()) {
			setPosition(mc.getCurPos());
		}else if(nextTile != null){
			mCurrentTile.removeEntity(this);
			setCurrentTile(nextTile);
			pollNextTile();
		}
	}
	
	protected abstract Vector2 getPlayerPos();
	
	protected abstract void attack();
	
	protected void pollNextTile() {
		mGrid.getAdjacentTiles(mCurrentTile, adjTiles);
		// nextTile = adjTiles.get((int)(Math.random()*adjTiles.size()));
		Tile t = adjTiles.get(0);
		double dis = Double.MAX_VALUE;
		for(Tile _t : adjTiles) {
			double d = Vector2.sub(_t.getPosition(), getPlayerPos()).length();
			if(d<dis) {
				dis = d;
				t = _t;
			}
		}
		nextTile = t;
		nextTile.addEntity(this);
		mc.setSrc(mCurrentTile.getPosition());
		mc.setDst(nextTile.getPosition());
		mc.move();
		changeDirection(mCurrentTile.getTileNum(), nextTile.getTileNum());
	}
	
	protected void changeDirection(int oldt, int newt) {
		int d = newt - oldt;
		if(d == -mGrid.NUM_COLS) mDirection = 0;
		else if(d == -1) mDirection = 1;
		else if(d == 1) mDirection = 2;
		else if(d == mGrid.NUM_COLS) mDirection = 3;
		updateTexture();
	}

	@Override
	public void die() {
		super.die();
	}
}
