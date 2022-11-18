package aa;

import physics.CelestialBody;
import processing.core.PVector;

public class Evade extends Behavior {

	public Evade(float weight) {
		super(weight);
	}
	
	@Override
	public PVector getDesiredVelocity(Boid me) {
		CelestialBody bodyTarget = me.eye.target;
		PVector d = bodyTarget.getVel().mult(me.dna.deltaTPursuit);
		PVector target = PVector.add(bodyTarget.getPos(), d);
		return PVector.sub(target, me.getPos());
	}

}
