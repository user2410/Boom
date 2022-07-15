package actors;

import java.util.ArrayList;

import components.Component;
import math.Vector2;
import scene.Scene;

public class Actor {

	public enum State{
		EActive, EPaused, EDead
	};
	
	private Scene mScene;
	private State mState;
	private Vector2 mPosition;
	private Vector2 mOriginalSize;
	private double mScale;
	// private double mRotation;
	
	private ArrayList<Component> mComponents;
	
	public Actor(Scene scene) {
		mState = State.EActive;
		mPosition = Vector2.Zero();
		mOriginalSize = null;
		mScale = 1.0;
		mScene = scene;
		mComponents = new ArrayList<>();
		scene.addActor(this);
	}
	
	public void update(double deltaTime) {
		if(mState == State.EActive) {
			updateComponents(deltaTime);
			updateActor(deltaTime);
		}
	}
	
	public void updateComponents(double deltaTime) {
		for(Component comp : mComponents) {
			comp.update(deltaTime);
		}
	}
	
	public void updateActor(double deltaTime) {}

	public State getState() {
		return mState;
	}

	public void setState(State mState) {
		this.mState = mState;
	}

	public Vector2 getPosition() {
		return mPosition;
	}

	public void setPosition(Vector2 mPosition) {
		this.mPosition = mPosition;
	}

	public double getScale() {
		return mScale;
	}

	public void setScale(double mScale) {
		this.mScale = mScale;
	}

	public Scene getScene() {
		return mScene;
	}

	public Vector2 getOriginalSize() {
		return mOriginalSize;
	}

	public void setOriginalSize(Vector2 mOriginalSize) {
		this.mOriginalSize = mOriginalSize;
	}

	public void addComponent(Component comp) {
		int myOrder = comp.getUpdateOrder();
		int i=0;
		for(; i<mComponents.size(); i++) {
			if(myOrder < mComponents.get(i).getUpdateOrder())
				break;
		}
		mComponents.add(i, comp);
	}

	public void removeComponent(Component comp) {
		mComponents.remove(comp);
	}
	
	@Override
	public void finalize() {
		mScene.removeActor(this);
	}
}
