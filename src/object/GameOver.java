package object;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public class GameOver {
	
	public BufferedImage mGameOverImage;
	public BufferedImage mVictoryImage;
	public Button mReplayBtn;
	
	public GameOver(Game game) {
		mGameOverImage = game.getTexture("gameover.png");
		mVictoryImage = game.getTexture("victory.png");
		mReplayBtn = new Button(game, "playbutton.png");
	}
	
	public void draw(Graphics g, boolean win) {
		if(win) {
			g.drawImage(mVictoryImage,
					(Game.WIDTH-mGameOverImage.getWidth())>>1,
					(Game.HEIGHT-3*mGameOverImage.getHeight())>>1,
					mGameOverImage.getWidth(),
					mGameOverImage.getHeight(),					
					null);			
		}else {
			g.drawImage(mGameOverImage,
				(Game.WIDTH-mGameOverImage.getWidth())>>1,
				(Game.HEIGHT-3*mGameOverImage.getHeight())>>1,
				null);			
		}
		mReplayBtn.draw(g);
	}
}
