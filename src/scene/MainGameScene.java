package scene;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import actors.Grid;
import actors.GridEntity;
import main.Game;
import math.Vector2;

public class MainGameScene extends Scene{
	
	private Grid mGrid;
	private boolean mGameOver;
	private GameOver mGameOverItem;
	
	public MainGameScene(Game game) {
		super(game);
		mGameOverItem = new GameOver(game);
	}
	
	@Override
	public boolean init() {
		mGameOver = false;
		return loadData();
	}

	@Override
	public boolean loadData() {
		mGrid = Grid.makeGrid(this, "maps/map02.dat");
		if(mGrid == null) {
			return false;
		}
		
		mGame.resize(
			(int)(mGrid.getncols()*Grid.TILE_SIZE)+Grid.PADDING_LEFT+Grid.PADDING_RIGHT, 
			(int)(mGrid.getnrows()*Grid.TILE_SIZE)+Grid.PADDING_TOP+Grid.PADDING_BOTTOM 
		);
		
		System.gc();
		
		return true;
	}
	
	@Override
	public void processKeyboard(KeyEvent e) {
		if(!mGameOver) mGrid.processKeyboard(e);
	}
	
	@Override
	public void processMouseClick(int x, int y) {
		if(mGameOver && mGameOverItem.mReplayBtn.checkClicked(x, y)) {
			for(int i=0; i<mActors.size(); i++) {
				if(mActors.get(i) instanceof GridEntity) {
					((GridEntity)mActors.get(i)).die();
					mActors.remove(i--);
				}
			}
			mPendingActors.clear();
			mGrid.resetGrid();
			mGameOver = false;
		}
	}
	
	@Override
	public void update(double deltaTime) {
		if(!mGameOver) {
			super.update(deltaTime);
		}
	}
	
	@Override
	public void generateOutput(Graphics g) {
		// clear background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, mGame.getWindowWidth(), mGame.getWindowHeight());
		
		super.generateOutput(g);
		
		if(mGameOver) {
			mGameOverItem.draw(g);
		}
	}

	public void endGame(boolean win) {
		mGameOver = true;
		mGameOverItem.setType(win);
	}
	
}

class GameOver {
	
	public static final int WIDTH = 288;
	public static final int HEIGHT = 63;
	
	public Game mGame;
	public BufferedImage mGameOverImage;
	public GameOverButton mReplayBtn;
	
	public GameOver(Game game) {
		mGame = game;
		mReplayBtn = new GameOverButton(game, "background/playbutton.png");
	}
	
	public void setType(boolean win) {
		if(win) {			
			mGameOverImage = mGame.getTexture("background/victory.png");			
		}else {
			mGameOverImage = mGame.getTexture("background/gameover.png");
		}
	}
	
	public void draw(Graphics g) {
		
		g.drawImage(mGameOverImage,
				(mGame.getWindowWidth()-WIDTH)>>1,
				(mGame.getWindowHeight()-3*HEIGHT)>>1,
				WIDTH, HEIGHT, null);
		mReplayBtn.draw(g);
	}
}

class GameOverButton{

	private Vector2 mPosition;
	private BufferedImage mImage;
	
	public GameOverButton(Game game, String imgFile) {
		mImage = game.getTexture(imgFile);
		mPosition = new Vector2(
				(game.getWindowWidth() - mImage.getWidth())>>1,
				(game.getWindowHeight() + mImage.getHeight())>>1);
	}
	
	public boolean checkClicked(int mouseX, int mouseY) {
		if((mouseX >= mPosition.x) && (mouseX <= mPosition.x + mImage.getWidth())
			&& (mouseY >= mPosition.y) && (mouseY <= mPosition.y + mImage.getHeight())) {
			return true;
		}
		return false;
	}
	
	public void draw(Graphics g) {
		g.drawImage(mImage, (int)mPosition.x, (int)mPosition.y, null);
	}
}

