package aa;

import processing.core.PVector;

public interface IBehaviour2 {
	public PVector getDesiredVelocity(Boid2 me);
	public void setWeight(float weight);
	public float getWeight();
}
