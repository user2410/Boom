package scene;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import main.Game;

public class MenuScene extends Scene {

	public MenuScene(Game game) {
		super(game);
	}

	@Override
	public boolean init() {
		return true;
	}

	@Override
	public boolean loadData() {
		return false;
	}
	
	@Override
	public void processKeyboard(KeyEvent e) {
		
	}
	
	@Override
	public void update(double deltaTime) {}
	
	@Override
	public void generateOutput(Graphics g) {
		
	}

}
