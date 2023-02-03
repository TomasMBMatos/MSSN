package aa;


import processing.core.PVector;

public class Pursuit2 extends Behavior2{
	
	public Pursuit2(float weight) {
		super(weight);
		
	}

	@Override
	public PVector getDesiredVelocity(Boid2 me) {
		Body2 bodyTarget = me.eye.target;
		PVector d = bodyTarget.getVel().mult(me.dna.deltaTPursuit);
		PVector target = PVector.add(bodyTarget.getPos(), d);
		return PVector.sub(target, me.getPos());
	}

}
