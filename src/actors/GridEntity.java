package actors;

import java.awt.Graphics;

import components.SpriteComponent;
import math.Vector2;
import scene.MainGameScene;

public abstract class GridEntity extends Actor {

	protected Grid mGrid;
	protected Tile mCurrentTile;
	
	protected int mDirection;
	
	protected SpriteComponent mSprite;
	
	public GridEntity(MainGameScene scene, Grid grid, Tile currentTile) {
		super(scene);
		mGrid = grid;
		setCurrentTile(currentTile);
		currentTile.addEntity(this);
		mSprite = new SpriteComponent(this) {
			@Override
			public void draw(Graphics g) {
				if(mTexture != null) {
					Vector2 pos = mOwner.getPosition();
					double scale = mOwner.getScale();
					int width = (int)(mTexWidth*scale);
					int height = (int)(mTexHeight*scale);
					g.drawImage(mTexture,
								(int)(pos.x + ((Grid.TILE_SIZE - mTexWidth)/2.0)),
								(int)(pos.y - mTexHeight + Grid.TILE_SIZE), 
								width, height, null);
				}
			}
		};
	}
	
	protected void updateTexture() {
	}
	
	public void die() {
		setState(Actor.State.EDead);
		getScene().removeSprite(mSprite);
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
