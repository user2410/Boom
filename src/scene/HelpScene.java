package scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import actors.Grid;
import main.Game;

public class HelpScene extends Scene {

	private int commandNum = 1;

	public HelpScene(Game game) {
		super(game);
	}

	@Override
	public boolean init() {
		return loadData();
	}

	@Override
	public boolean loadData() {
		return true;
	}
	
	@Override
	public void processKeyboard(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ENTER:
			if(commandNum == 1) {
				mGame.setCurrentScene(0);
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
		String text = "HELP";
		int textWidth = g.getFontMetrics().stringWidth(text);
		int x = (mGame.getWindowWidth()-textWidth)/2;
		int y = (int) (Grid.TILE_SIZE)*3;
		
		g.setColor(Color.gray);
		g.drawString(text, x+5, y+5);
		
		g.setColor(Color.white);
		g.drawString(text, x, y);
		
              g.setFont(g.getFont().deriveFont(Font.BOLD,48F));
		
		text = "W : Move up";
		textWidth = g.getFontMetrics().stringWidth(text);
		x = (mGame.getWindowWidth()-textWidth)/2;
		y+= (int) (Grid.TILE_SIZE)*2;
		g.drawString(text, x, y);
		
		text = "S : Move down";
		textWidth = g.getFontMetrics().stringWidth(text);
		x = (mGame.getWindowWidth()-textWidth)/2;
		y+= (int) (Grid.TILE_SIZE);
		g.drawString(text, x, y);
		
		text = "A : Move left";
		textWidth = g.getFontMetrics().stringWidth(text);
		x = (mGame.getWindowWidth()-textWidth)/2;
		y+= (int) (Grid.TILE_SIZE);
		g.drawString(text, x, y);
		
		text = "D : Move right";
		textWidth = g.getFontMetrics().stringWidth(text);
		x = (mGame.getWindowWidth()-textWidth)/2;
		y+= (int) (Grid.TILE_SIZE);
		g.drawString(text, x, y);
		
		text = "ENTER :Place bomb / Select";
		textWidth = g.getFontMetrics().stringWidth(text);
		x = (mGame.getWindowWidth()-textWidth)/2;
		y+= (int) (Grid.TILE_SIZE);
		g.drawString(text, x, y);
		
		text = "Left Click : Play again";
		textWidth = g.getFontMetrics().stringWidth(text);
		x = (mGame.getWindowWidth()-textWidth)/2;
		y+= (int) (Grid.TILE_SIZE);
		g.drawString(text, x, y);
		
		text = "Back";
		textWidth = g.getFontMetrics().stringWidth(text);
		x = (mGame.getWindowWidth()-textWidth)/2;
		y+= (int) (Grid.TILE_SIZE)*2;
		g.drawString(text, x, y);
		if(commandNum == 1) {
			g.drawString(">", x - (int) (Grid.TILE_SIZE), y);
		}
		}

}
