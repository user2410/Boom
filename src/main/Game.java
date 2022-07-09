package main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import actors.*;
import components.SpriteComponent;
import loader.GraphicsLoader;
import object.GameOver;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable {

	public static final int WIDTH = 750;
	public static final int HEIGHT = 674;

	@SuppressWarnings("unused")
	private Window mWindow;
	private boolean mIsRunning;
	private boolean mUpdatingActors;
	private long mPastTime;
	public boolean mGameOver;

	private ArrayList<Actor> mActors;
	private ArrayList<Actor> mPendingActors;
	private HashMap<String, BufferedImage> mTextures;
	private ArrayList<SpriteComponent> mSprites;
	
	// Game specific
	private Grid mGrid; 
	private GameOver mGameOverItem;
	
	public Game() {
		mWindow = null;
		mIsRunning = true;
		mUpdatingActors = false;
		mActors = new ArrayList<Actor>();
		mPendingActors = new ArrayList<Actor>();
		mTextures = new HashMap<String, BufferedImage>();
		mSprites = new ArrayList<SpriteComponent>();
	}

	public boolean init() {
		mWindow = new Window("Boom2d", WIDTH, HEIGHT, this);

		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				if(!mGameOver) {
					mGrid.processKeyboard(e);
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {}
		});
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				if(mGameOver) {
					if(mGameOverItem.mReplayBtn.checkClicked(e.getX(), e.getY())) {
						loadData();
						mGameOver = false;
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});

		mPastTime = System.nanoTime();

		loadData();

		return true;
	}
	
	private void loadData() {
		mActors.clear();
		mPendingActors.clear();
		mSprites.clear();
		
		mGrid = new Grid(this);
		mGrid.loadMap("maps/map01.dat");
		
		mGameOverItem = new GameOver(this);
		
		System.gc();
	}
	
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
	
	public BufferedImage getTexture(String filename) {
		BufferedImage bi = mTextures.get(filename);
		if(bi==null) {
			bi = GraphicsLoader.loadGraphics(filename);
			if(bi==null) {
				System.err.println("Failed to load " + filename);
				return null;
			}
			mTextures.put(filename, bi);
		}
		return bi;
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
	
	@Override
	public void run() {
		this.requestFocus();
		while (mIsRunning) {
			// processInput();
			if(!mGameOver) {
				updateGame();
			}
			generateOutput();
		}

	}

	private void updateGame() {
				
		while(System.nanoTime() - mPastTime < 16000000);
		
		double deltaTime = (System.nanoTime() - mPastTime)/1000000000.0f;
		if(deltaTime > 0.05) {
			deltaTime = 0.05;
		}
		mPastTime = System.nanoTime();
		
		mUpdatingActors = true;
		for(Actor actor : mActors) {
			actor.update(deltaTime);
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

	private void generateOutput() {
		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		
		// clear background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		for(int i=0; i<mSprites.size(); i++) {
			mSprites.get(i).draw(g);
		}

		if(mGameOver) {
			mGameOverItem.draw(g);
		}
		
		
		g.dispose();
		bs.show();
		
	}
}
