package ecosystem;

import aa.Behavior;
import aa.Behavior2;
import aa.Boid;
import aa.Boid2;
import aa.DNA;
import aa.Eye;
import aa.Eye2;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public abstract class Animal extends Boid2 implements IAnimal {
	protected float energy;
	protected Animal(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt) {
		super(pos, mass, radius, color, p, plt);

	}

	protected Animal(Animal a,boolean mutate ,PApplet p, SubPlot plt) {
		super(a.pos, a.mass, a.radius, a.color, p, plt);
		for (Behavior2 b : a.behaviors) {
			this.addBehavior(b);
		}
		if (a.eye != null) {
			eye = new Eye2(this, a.eye);

		}
		dna = new DNA(a.dna,mutate);

	}


	@Override
	public boolean die() {
		return (energy<0);
	}
	@Override
	public void energy_consumption(float dt, Terrain terrain) {
		//energy -=dt;
		energy -= mass * Math.pow(vel.mag(), 2)*dt;
		Patch patch = (Patch) terrain.world2Cell(pos.x, pos.y);
		if(patch.getState()==WorldConstants.PatchType.OBSTACLE.ordinal())
			energy -=50*dt;

	}

	

}
