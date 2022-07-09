package components;

import actors.Actor;

public class Component {

	protected Actor mOwner;
	protected int mUpdateOrder;
	
	public Component(Actor owner) {
		mOwner = owner;
		owner.addComponent(this);
		mUpdateOrder = 100;
	}
	
	public Component(Actor owner, int updateOrder) {
		this(owner);
		mUpdateOrder = updateOrder;
	}

	public void update(double deltaTime) {
	}
	
	public int getUpdateOrder() {
		return mUpdateOrder;
	}
	
	@Override
	public void finalize() {
		mOwner.removeComponent(this);
	}
}
