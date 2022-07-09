package actors;

import java.awt.event.KeyEvent;

import components.MoveComponent;
import main.Game;
// import math.Vector2;

public class Player extends Entity {

	private int mDirection;
	private MoveComponent mc;
	private Tile dstTile;
	
	public Player(Game game, Grid grid) {
		super(game, grid, grid.getTile(0));
		mDirection = 2;
		grid.setPlayer(this);
		updateTexture();
		mc = new MoveComponent(this);
		mc.setSpeed(300.0);
		dstTile = null;
	}
	
	public Player(Game game, Grid grid, Tile currentTile) {
		super(game, grid, currentTile);
		setCurrentTile(currentTile);
	}

	@Override
	public void processKeyBoard(KeyEvent e) {
		
		Tile srcTile = mCurrentTile;
		int i = srcTile.getTileNum() / mGrid.NUM_COLS;
		int j = srcTile.getTileNum() % mGrid.NUM_COLS;
		int moved = 0;
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			mDirection = 0;
			if(i>0) moved = -mGrid.NUM_COLS;
			break;
		case KeyEvent.VK_A:
			mDirection = 1;
			if(j>0) moved = -1;
			break;
		case KeyEvent.VK_S:
			mDirection = 2;
			if(i+1<mGrid.NUM_ROWS) moved = mGrid.NUM_COLS;
			break;
		case KeyEvent.VK_D:
			mDirection = 3;
			if(j+1<mGrid.NUM_COLS) moved = 1;
			break;
		default:
		}
		
		if(moved != 0 && !mc.isMoving()) {
			Tile dst = mGrid.getTile(srcTile.getTileNum()+moved);
			if(!dst.isBlocked()) {
				updateTexture();
				srcTile.removeEntity(this);
				dst.addEntity(this);
				mc.setSrc(srcTile.getPosition());
				mc.setDst(dst.getPosition());
				mc.move();
				dstTile = dst;
			}
		}
	}
	
	public void setBomb() {
		new Bomb(getGame(), mGrid, this);
	}
	
	@Override
	public void updateActor(double deltaTime) {
		super.updateActor(deltaTime);
		mc.update(deltaTime);
		if(mc.isMoving()) {
			setPosition(mc.getCurPos());
		}else if(dstTile != null){
			setCurrentTile(dstTile);
			dstTile = null;
		}
	}
	
	@Override
	protected void updateTexture() {
		String text = "";
		switch(mDirection) {
		case 0:
			text = "player/khokho_up.png";
			break;
		case 1:
			text = "player/khokho_left.png";
			break;
		case 2:
			text = "player/khokho_down.png";
			break;
		case 3:
			text = "player/khokho_right.png";
			break;
		}
		mSprite.setTexture(getGame().getTexture(text));
	}
	
	@Override
	public void die() {
		super.die();
	}
}
