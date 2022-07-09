package actors;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import components.SpriteComponent;
import main.Game;
import math.Vector2;

public class Tile extends Actor {

	public enum TileState {
		EDefault, EBoxCot, EBoxGo, EBoxGo2, EBoxSat, EBoxSatMain
	}

	private TileState mTileState;
	private int mTileNum;
	private boolean mBlocked;
	private SpriteComponent mSprite;
	@SuppressWarnings("unused")
	private SpriteComponent mCoverSprite;
	private double mBlowTime;
	private ArrayList<Entity> mEntities;

	public Tile(Game game, int num) {
		super(game);
		mTileNum = num;
		mTileState = TileState.EDefault;
		mBlocked = false;
		mEntities = new ArrayList<>();
		mSprite = new SpriteComponent(this, 10);
		mCoverSprite = new SpriteComponent(this, 11) {
			@Override
			public void draw(Graphics g) {
				if(mBlowTime == 0) return;
				Color c = new Color(0, 102, 255, (int)(mBlowTime*255));
				g.setColor(c);
				Vector2 pos = getPosition();
				Vector2 size = getOriginalSize();
				g.fillRect((int)pos.x, (int)pos.y, (int)(size.x*getScale()), (int)(size.x*getScale()));
			}
		};
		updateTexture();
	}

	public void setTileState(TileState state) {
		mTileState = state;
		
		if(state != TileState.EDefault)
			mBlocked = true;
		else
			mBlocked = false;
		
		updateTexture();
	}
	
	public void updateActor(double deltaTime) {
		if(mBlowTime > 0.0) {
			mBlowTime -= deltaTime;
			if(mBlowTime<0.0) mBlowTime = 0.0;
		}
	}

	private void updateTexture() {
		String text = "";
		switch (mTileState) {
		case EDefault:
			text = (mTileNum & 1) == 0 ? "tiles/ngang.png" : "tiles/doc.png";
			break;
		case EBoxCot:
			text = "tiles/boxcot.png";
			break;
		case EBoxGo:
			text = "tiles/boxgo.png";
			break;
		case EBoxGo2:
			text = "tiles/boxgo2.png";
			break;
		case EBoxSat:
			text = "tiles/boxsat.png";
			break;
		case EBoxSatMain:
			text = "tiles/boxsatmain.png";
			break;
		}
		mSprite.setTexture(getGame().getTexture(text));
	}
	
	public void addEntity(Entity e) {
		if(!mEntities.contains(e))
			mEntities.add(e);
	}
	
	public void removeEntity(Entity e) {
		mEntities.remove(e);
	}

	public TileState getTileState() {
		return mTileState;
	}

	public int getTileNum() {
		return mTileNum;
	}

	public boolean isBlocked() {
		return mBlocked;
	}

	public void setBlocked(boolean blocked) {
		this.mBlocked = blocked;
	}
	
	public boolean isBlowable() {
		return mTileState != TileState.EBoxSat
				&& mTileState != TileState.EBoxSatMain;
	}
	
	public void blow() {
		for(int i=0; i<mEntities.size(); i++) {
			Entity e = mEntities.get(i);
			e.die();
			try {
				mEntities.remove(i--);
			}catch(Exception ie) {}
		}
		setTileState(TileState.EDefault);
		mBlowTime = 0.5;
	}
	
}
