package actors;

import java.awt.event.KeyEvent;

import components.EntitySpriteComponent;
import main.Game;
// import math.Vector2;

public class Entity extends Actor {

	protected Grid mGrid;
	protected Tile mCurrentTile;
	
	protected int mDirection;
	
	protected EntitySpriteComponent mSprite;
	
	public Entity(Game game, Grid grid, Tile currentTile) {
		super(game);
		mGrid = grid;
		setCurrentTile(currentTile);
		currentTile.addEntity(this);
		mSprite = new EntitySpriteComponent(this, grid);
	}

	public void processKeyBoard(KeyEvent e) {
	}
	
	protected void updateTexture() {
	}
	
	public void die() {
		setState(Actor.State.EDead);
		getGame().removeSprite(mSprite);
	}

	
	public Tile getCurrentTile() {
		return mCurrentTile;
	}

	public void setCurrentTile(Tile t) {
		this.mCurrentTile = t;
		setPosition(t.getPosition());
		t.addEntity(this);
	}
	
	
}
