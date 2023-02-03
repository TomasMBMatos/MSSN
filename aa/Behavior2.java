package aa;

import processing.core.PVector;

public abstract class Behavior2 implements IBehaviour2 {

	public float weight;
	
	public Behavior2(float weight) {
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
