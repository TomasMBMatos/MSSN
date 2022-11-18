package aa;

import physics.CelestialBody;
import processing.core.PVector;

public class Pursuit extends Behavior {

	public Pursuit(float weight) {
		super(weight);
	}
	
	@Override
	public PVector getDesiredVelocity(Boid me) {
		CelestialBody bodyTarget = me.eye.target;
		PVector d = bodyTarget.getVel().mult(me.dna.deltaTPursuit);
		PVector target = PVector.add(bodyTarget.getPos(), d);
		PVector vd = PVector.sub(target, me.getPos());
		return vd.mult(-1);
	}

}
