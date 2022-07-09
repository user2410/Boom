package object;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import math.Vector2;

public class Button{

	private Vector2 mPosition;
	private BufferedImage mImage;
	
	public Button(Game game, String imgFile) {
		mImage = game.getTexture(imgFile);
		mPosition = new Vector2((Game.WIDTH - mImage.getWidth())>>1, (Game.HEIGHT + mImage.getHeight())>>1);
	}

	public void setPosition(Vector2 mPosition) {
		this.mPosition = mPosition;
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
