package aa;

import processing.core.PVector;

public abstract class Behavior implements IBehaviour {

	private float weight;
	
	public Behavior(float weight) {
		this.weight = weight;
	}
	
	@Override
	public void setWeight(float weight) {
		this.weight = weight;
	}

	@Override
	public float getWeight() {
		return weight;
	}

}
