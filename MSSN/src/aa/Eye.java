package aa;

import java.util.ArrayList;
import java.util.List;

import physics.CelestialBody;
import processing.core.PVector;

public class Eye {
	private List<CelestialBody> allTrackingBodies;
	private List<CelestialBody> farSight;
	private List<CelestialBody> nearSight;
	private Boid me;
	protected CelestialBody target;
	
	public Eye(Boid me,List<CelestialBody> allTrackingBodies) {
		this.me = me;
		this.allTrackingBodies = allTrackingBodies;
		target = allTrackingBodies.get(0);
	}
	
	public List<CelestialBody> getFarSight(){
		return farSight;
	}
	public List<CelestialBody> getNearSight(){
		return nearSight;
	}
	
	public void look() {
		farSight = new ArrayList<CelestialBody>();
		nearSight = new ArrayList<CelestialBody>();
		for(CelestialBody b : allTrackingBodies) {
			if(farSight(b.getPos()))
				farSight.add(b);
			if(nearSight(b.getPos()))
				nearSight.add(b);
		}
	}
	
	private boolean inSight(PVector t, float maxDistance, float maxAngle) {
		PVector r = PVector.sub(t, me.getPos());
		float d = r.mag();
		float angle = PVector.angleBetween(r, me.getVel());
		return ((d > 0) && (d < maxDistance) && (angle < maxAngle));
	}
	
	private boolean farSight(PVector t) {
		return inSight(t,me.dna.visionDistance, me.dna.visionAngle);	
	}
	
	private boolean nearSight(PVector t) {
		return inSight(t,me.dna.visionSafeDistance, (float) Math.PI);	
	}
}
