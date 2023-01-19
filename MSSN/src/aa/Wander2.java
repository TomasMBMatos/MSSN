package aa;

import processing.core.PVector;

public class Wander2 extends Behavior2 {

	public Wander2(float weight) {
		super(weight);
	}
	
	@Override
	public PVector getDesiredVelocity(Boid2 me) {
		PVector center = me.getPos().copy();
		center.add(me.getVel().copy().mult(me.dna.deltaPhiWander));
		PVector target = new PVector( me.dna.radiusWander*(float)Math.cos(me.phiWander), me.dna.radiusWander*(float)Math.sin(me.phiWander));
		target.add(center);
		me.phiWander += 2*(Math.random()-0.5)*me.dna.deltaPhiWander;
		return PVector.sub(target, me.getPos());
	}

	

}
