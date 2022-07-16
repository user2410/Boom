package main;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import loader.GraphicsLoader;
import scene.HelpScene;
import scene.MainGameScene;
import scene.MenuScene;
import scene.Scene;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable {

	public static final int PREF_WIDTH = 750;
	public static final int PREF_HEIGHT = 674;

	@SuppressWarnings("unused")
	private Window mWindow;
	private boolean mIsRunning;
	private long mPastTime;
	public boolean mGameOver;

	private ArrayList<Scene> mScenes;
	private int mCurrentScene;
	private HashMap<String, BufferedImage> mTextures;
	
	public Game() {
		mWindow = null;
		mIsRunning = true;
		mTextures = new HashMap<String, BufferedImage>();
		mScenes = new ArrayList<Scene>();
	}

	public boolean init() {
		mWindow = new Window("2DBoomGame", PREF_WIDTH, PREF_HEIGHT, this);

		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				Scene scene = mScenes.get(mCurrentScene);
				scene.processKeyboard(e);
			}
			@Override
			public void keyReleased(KeyEvent e) {}
		});
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				Scene scene = mScenes.get(mCurrentScene);
				scene.processMouseClick(e.getX(), e.getY());
			}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});

		mPastTime = System.nanoTime();

		return loadData();
	}
	
	private boolean loadData() {
		
		Scene scene1 = new MenuScene(this);
		if(!scene1.init()) return false;
		mScenes.add(scene1);
		
		Scene scene2 = new MainGameScene(this);
		if(!scene2.init()) return false;
		mScenes.add(scene2);
		
		Scene scene3 = new HelpScene(this);
		if(!scene3.init()) return false;
		mScenes.add(scene3);
		
		mCurrentScene = 0;
		return true;
	}
	
	public void setCurrentScene(int scene){
		mCurrentScene = scene;
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
	
	@Override
	public void run() {
		this.requestFocus();
		while (mIsRunning) {
			// processInput();
			updateGame();
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
		
		Scene scene = mScenes.get(mCurrentScene);
		scene.update(deltaTime);
	}

	private void generateOutput() {
		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		
		Scene scene = mScenes.get(mCurrentScene);
		scene.generateOutput(g);
		
		g.dispose();
		bs.show();
		
	}
	
	public int getWindowWidth() {
		return mWindow.getWidth();
	}
	
	public int getWindowHeight() {
		return mWindow.getHeight();
	}
	
	@Override
	public void resize(int width, int height) {
		mWindow._resize(width, height);
	}
	
	public void terminate() {
		mIsRunning = false;
	}
	
}
