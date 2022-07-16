package scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import actors.Grid;
import main.Game;

public class MenuScene extends Scene {

	private BufferedImage mPlayerImg;
	private int commandNum = 0;

	public MenuScene(Game game) {
		super(game);
	}

	@Override
	public boolean init() {
		return loadData();
	}

	@Override
	public boolean loadData() {
		mPlayerImg = mGame.getTexture("player/khokho_down.png");
		return mPlayerImg != null;
	}
	
	@Override
	public void processKeyboard(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			commandNum--;
			if(commandNum < 0) {
				commandNum = 2;
			}
			break;
		case KeyEvent.VK_S:
			commandNum++;
        	if(commandNum > 2) {
				commandNum = 0;
			}
			break;
		case KeyEvent.VK_ENTER:
			if(commandNum == 0) {
				mGame.setCurrentScene(1);
			}
        	if(commandNum == 1) {
        		mGame.setCurrentScene(2);
			}
        	if(commandNum == 2) {
        		System.exit(0);
			}
			break;
		}
	}
	
	@Override
	public void update(double deltaTime) {}
	
	@Override
	public void generateOutput(Graphics g) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, mGame.getWindowWidth(), mGame.getWindowHeight());
			g.setFont(g.getFont().deriveFont(Font.BOLD,96F));
			String text = "2D Boom Game";
			int textWidth = g.getFontMetrics().stringWidth(text);
			int x = (mGame.getWindowWidth()-textWidth)/2;
			int y = (int) (Grid.TILE_SIZE*3);
			
			g.setColor(Color.gray);
			g.drawString(text, x+5, y+5);
			
			g.setColor(Color.white);
			g.drawString(text, x, y);
			
			x = (mGame.getWindowWidth()-mPlayerImg.getWidth())/2;
			y += (int) (Grid.TILE_SIZE);
			g.drawImage(mPlayerImg, x, y,mPlayerImg.getWidth()*3, mPlayerImg.getHeight()*3, null);
			
			g.setFont(g.getFont().deriveFont(Font.BOLD,48F));
			
			text = "Start";
			textWidth = g.getFontMetrics().stringWidth(text);
			x = (mGame.getWindowWidth()-textWidth)/2;
			y += (int) (Grid.TILE_SIZE)*6;
			g.drawString(text, x, y);	
			if(commandNum == 0) {
				g.drawString(">", x - (int) (Grid.TILE_SIZE), y);
			}
			
			text = "Help";
			textWidth = g.getFontMetrics().stringWidth(text);
			x = (mGame.getWindowWidth()-textWidth)/2;
			y += (int) (Grid.TILE_SIZE);
			g.drawString(text, x, y);
			if(commandNum == 1) {
				g.drawString(">", x - (int) (Grid.TILE_SIZE), y);
			}
			
			text = "Quit";
			textWidth = g.getFontMetrics().stringWidth(text);
			x = (mGame.getWindowWidth()-textWidth)/2;
			y += (int) (Grid.TILE_SIZE);
			g.drawString(text, x, y);
			if(commandNum == 2) {
				g.drawString(">", x - (int) (Grid.TILE_SIZE), y);
			}
		}

}
