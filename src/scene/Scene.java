package scene;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import actors.Actor;
import components.SpriteComponent;
import main.Game;

public abstract class Scene {
	
	protected Game mGame;
	private boolean mUpdatingActors;
	protected ArrayList<Actor> mActors;
	protected ArrayList<Actor> mPendingActors;
	protected ArrayList<SpriteComponent> mSprites;
	
	public Scene(Game game) {
		mGame = game;
		mActors = new ArrayList<Actor>();
		mPendingActors = new ArrayList<Actor>();
		mSprites = new ArrayList<SpriteComponent>();
	}
	
	public abstract boolean init();
	
	public abstract boolean loadData();
	
	public void addActor(Actor actor) {
		if(mUpdatingActors) {
			mPendingActors.add(actor);
		}else {
			mActors.add(actor);
		}
	}
	
	public void removeActor(Actor actor) {
		mPendingActors.remove(actor);
		mActors.remove(actor);
	}
	
	public BufferedImage getTexture(String text) {
		return mGame.getTexture(text);
	}
	
	public void addSprite(SpriteComponent sprite) {
		int myDrawOrder = sprite.getDrawOrder();
		int i=0;
		for(; i<mSprites.size(); i++) {
			if(myDrawOrder < mSprites.get(i).getDrawOrder())
				break;
		}
		mSprites.add(i, sprite);
	}
	
	public void removeSprite(SpriteComponent sprite) {
		mSprites.remove(sprite);
	}
	
	public void processKeyboard(KeyEvent e) {}

	public void processMouseClick(int x, int y) {}
	
	public void update(double deltaTime) {
		mUpdatingActors = true;
		for(int i=0; i<mActors.size(); i++) {
			mActors.get(i).update(deltaTime);
		}
		mUpdatingActors = false;
		
		for(Actor actor : mPendingActors) {
			mActors.add(actor);
		}
		mPendingActors.clear();
		
		for(int i=0; i<mActors.size(); i++) {
			Actor actor = mActors.get(i);
			if(actor.getState() == Actor.State.EDead) {
				try{
					mActors.remove(i--);
				}catch(Exception e){}
			}
		}
	}
	
	public void generateOutput(Graphics g) {
		for(int i=0; i<mSprites.size(); i++) {
			mSprites.get(i).draw(g);
		}
	}
}
