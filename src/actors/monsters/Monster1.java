package actors.monsters;

import actors.Grid;
import actors.Monster;
import actors.Tile;
import math.Vector2;
import scene.MainGameScene;

public class Monster1 extends Monster{

	public Monster1(MainGameScene scene, Grid grid, Tile currentTile) {
		super(scene, grid, currentTile);
	}
	
	@Override
	protected void updateTexture() {
		String text = "";
		switch(mDirection) {
		case 0:
			text = "monster/quaivat1_up.png";
			break;
		case 1:
			text = "monster/quaivat1_left.png";
			break;
		case 2:
			text = "monster/quaivat1_down.png";
			break;
		case 3:
			text = "monster/quaivat1_right.png";
			break;
		}
		mSprite.setTexture(getScene().getTexture(text));
	}

	@Override
	protected Vector2 getPlayerPos() {
		// TODO Auto-generated method stub
		return mGrid.getPlayer().getPosition();
	}

	@Override
	protected void attack() {
		mGrid.getPlayer().sustain(3.0);
	}
}
