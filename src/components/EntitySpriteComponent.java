package components;

import java.awt.Graphics;

import actors.Actor;
import actors.Grid;
import math.Vector2;

public class EntitySpriteComponent extends SpriteComponent{

	private Grid grid;
	
	public EntitySpriteComponent(Actor owner, Grid grid) {
		super(owner);
		this.grid = grid;
	}
	
	@Override
	public void draw(Graphics g) {
		if(mTexture != null) {
			Vector2 pos = mOwner.getPosition();
			double scale = mOwner.getScale();
			int width = (int)(mTexWidth*scale);
			int height = (int)(mTexHeight*scale);
			g.drawImage(mTexture,
						(int)(pos.x + ((grid.TILE_SIZE - mTexWidth)/2.0)),
						(int)(pos.y - mTexHeight + grid.TILE_SIZE), 
						width, height, null);
		}
	}
}
