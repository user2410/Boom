package actors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import components.MoveComponent;
import components.SpriteComponent;
import main.Game;
// import math.Vector2;
import math.Vector2;

public class Player extends Entity {

	private int mDirection;
	private MoveComponent mc;
	private Tile dstTile;
	private double bombCooldown;
	private double health;
	@SuppressWarnings("unused")
	private SpriteComponent healthBar;
	
	public Player(Game game, Grid grid, Tile currentTile) {
		super(game, grid, currentTile);
		mDirection = 2;
		updateTexture();
		mc = new MoveComponent(this);
		mc.setSpeed(300.0);
		dstTile = null;
		health = 100.0;
		healthBar = new SpriteComponent(this, 9) {
			private Vector2 pos = new Vector2(15.0, 5.0);
			private Vector2 size = new Vector2(720.0, 15.0);
			
			@Override
			public void draw(Graphics g) {
				g.setColor(Color.RED);
				g.fillRect((int)pos.x, (int)pos.y, (int)(health/100.0*size.x), (int)(size.y));
			}
		};
		bombCooldown = 0.0;
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
		if(bombCooldown <= 0.0)	new Bomb(getGame(), mGrid, this);
	}
	
	@Override
	public void updateActor(double deltaTime) {
		super.updateActor(deltaTime);
		bombCooldown -= deltaTime;
		if(bombCooldown < 0.0) bombCooldown = 0.0;
		if(health<=0.0) die();
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
	
	
	public int getDirection() {
		return mDirection;
	}
	
	public void sustain(double damage) {
		health -= damage;
	}
}
