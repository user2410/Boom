package components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import actors.Actor;
import math.Vector2;

public class SpriteComponent extends Component {

	protected BufferedImage mTexture;
	protected int mDrawOrder;
	protected int mTexWidth;
	protected int mTexHeight;
	
	public SpriteComponent(Actor owner) {
		super(owner);
		mTexture = null;
		mDrawOrder = 100;
		mTexWidth = 0;
		mTexHeight = 0;
		owner.getGame().addSprite(this);
	}

	public SpriteComponent(Actor owner, int drawOrder) {
		this(owner);
		mDrawOrder = drawOrder;
	}
	
	public void draw(Graphics g) {
		if(mTexture != null) {
			Vector2 pos = mOwner.getPosition();
			Vector2 orgSize = mOwner.getOriginalSize();
			double scale = mOwner.getScale();
			int width = (int)(mTexWidth*scale);
			int height = (int)(mTexHeight*scale);
			if(orgSize != null) {
    			width = (int)(orgSize.x*scale);
    			height = (int)(orgSize.y*scale);
			}
			g.drawImage(mTexture,
						(int)(pos.x),
						(int)(pos.y), 
						width, height, null);
		}
	}
	
	public void setTexture(BufferedImage texture) {
		mTexture = texture;
		mTexWidth = texture.getWidth();
		mTexHeight = texture.getHeight();
	}

	public int getDrawOrder() {
		return mDrawOrder;
	}
	
	public void setDrawOrder(int drawOrder) {
		this.mDrawOrder = drawOrder;
	}

	public int getTexWidth() {
		return mTexWidth;
	}

	public int getTexHeight() {
		return mTexHeight;
	}
	
	@Override
	public void finalize() {
		mOwner.getGame().removeSprite(this);
	}
}
