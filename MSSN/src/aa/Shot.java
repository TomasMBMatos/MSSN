package aa;

import physics.CelestialBody;
import processing.core.PVector;

public class Shot {
	public Boid shot;
	public PVector force;
	
	public Shot(Boid shot, PVector force) {
		this.shot=shot;
		this.force=force;
	}
}
