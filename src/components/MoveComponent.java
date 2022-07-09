package components;

import actors.Actor;
import math.Vector2;

public class MoveComponent extends Component{

	private Vector2 mSrc;
	private Vector2 mDst;
	private Vector2 mDir;
	private Vector2 mCur;
	private double mSpeed;
	private boolean mIsMoving;
	
	public MoveComponent(Actor owner) {
		super(owner);
		mIsMoving = false;
	}
	
	public void setSrc(Vector2 src) {
		if(!mIsMoving) mSrc = src;
	}
	
	public void setDst(Vector2 dst) {
		if(!mIsMoving) mDst = dst;
	}

	public void setSpeed(double speed) {
		if(!mIsMoving) mSpeed = speed;
	}
	
	public void move() {
		mCur = new Vector2(mSrc);
		mIsMoving = true;
		mDir = Vector2.sub(mDst, mSrc);
		mDir.normalize();
	}
	
	public boolean isMoving() {
		return mIsMoving;
	}
	
	private boolean checkPassed() {
		// Check if passsed the dst
		Vector2 dir = Vector2.sub(mDst, mCur);
		dir.normalize();
		if((dir.x * mDir.x >= 0) && (dir.y * mDir.y >= 0)){
			return false;
		}
		return true;
	}
	
	@Override
	public void update(double deltaTime) {
		if(mIsMoving) {
    		mCur.x += mSpeed * deltaTime * mDir.x;
    		mCur.y += mSpeed * deltaTime * mDir.y;
    		if(checkPassed()) {
    			mIsMoving = false;
    			mCur = new Vector2(mDst);
    		}
		}
	}

	public Vector2 getCurPos() {
		return mCur;
	}
	
	
}
