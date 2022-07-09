package actors.monsters;

import actors.Grid;
import actors.Monster;
import actors.Tile;
import main.Game;

public class Monster1 extends Monster{

	public Monster1(Game game, Grid grid, Tile currentTile) {
		super(game, grid, currentTile);
		updateTexture();
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
		mSprite.setTexture(getGame().getTexture(text));
	}
	
	@Override
	public void react(Tile t) {
		super.react(t);
		mPF.setToTile(t);
		if(mPF.findPath()) {
			dstTile = mPF.moveNext();
			mc.setSrc(getPosition());
			mc.setDst(dstTile.getPosition());
			mc.move();
		}
	}
}
