package aa;

import physics.CelestialBody;
import processing.core.PVector;

public class Brake extends Behavior {

	public Brake(float weight) {
		super(weight);
		
	}
	
	@Override
	public PVector getDesiredVelocity(Boid me) {
		CelestialBody bodyTarget = me.eye.target;
		PVector vd = PVector.sub(bodyTarget.getPos(), me.getPos());
		return vd.mult(-1);
	}

}
