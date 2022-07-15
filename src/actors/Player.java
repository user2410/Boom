package actors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import components.MoveComponent;
import components.SpriteComponent;
import scene.MainGameScene;

public class Player extends GridEntity {
	
	public final int HEALTHBAR_Y = 5;
	public final int HEALTHBAR_HEIGHT = 15;

	private int mDirection;
	private MoveComponent mc;
	private Tile dstTile;
	private double bombCooldown;
	private double health;
	@SuppressWarnings("unused")
	private SpriteComponent healthBar;
	
	public Player(MainGameScene scene, Grid grid, Tile currentTile) {
		super(scene, grid, currentTile);
		mDirection = 2;
		updateTexture();
		mc = new MoveComponent(this);
		mc.setSpeed(240.0);
		dstTile = null;
		health = 100.0;
		healthBar = new SpriteComponent(this, 9) {			
			@Override
			public void draw(Graphics g) {
				g.setColor(Color.RED);
				g.fillRect(Grid.PADDING_LEFT, HEALTHBAR_Y, (int)(health/100.0*grid.getncols()*Grid.TILE_SIZE), HEALTHBAR_HEIGHT);
			}
		};
		bombCooldown = 0.0;
	}

	public void processKeyBoard(KeyEvent e) {
		
		Tile srcTile = mCurrentTile;
		int i = srcTile.getTileNum() / mGrid.getncols();
		int j = srcTile.getTileNum() % mGrid.getncols();
		int moved = 0;
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			mDirection = 0;
			if(i>0) moved = -mGrid.getncols();
			break;
		case KeyEvent.VK_A:
			mDirection = 1;
			if(j>0) moved = -1;
			break;
		case KeyEvent.VK_S:
			mDirection = 2;
			if(i+1<mGrid.getnrows()) moved = mGrid.getncols();
			break;
		case KeyEvent.VK_D:
			mDirection = 3;
			if(j+1<mGrid.getncols()) moved = 1;
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
		if(bombCooldown <= 0.0) {
			new Bomb((MainGameScene)getScene(), mGrid, this);
			bombCooldown = 3.0;
		}
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
		mSprite.setTexture(getScene().getTexture(text));
	}
	
	
	public int getDirection() {
		return mDirection;
	}
	
	public void sustain(double damage) {
		health -= damage;
	}
}
