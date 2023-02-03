package aa;

import java.util.ArrayList;
import java.util.List;

import ecosystem.Animal;
import physics.CelestialBody;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Eye2 {
	protected List<Body2> allTrackingBodies;
	protected List<Body2> farSight;
	protected List<Body2> nearSight;
	private Boid2 me;
	protected Body2 target;
	
	public Eye2(Boid2 me,List<Body2> allTrackingBodies) {
		this.me = me;
		this.allTrackingBodies = allTrackingBodies;
		if(allTrackingBodies.size()>0) target=allTrackingBodies.get(0);
		target = allTrackingBodies.get(0);
	}
	
	public Eye2(Boid2 me, Eye2 eye) {
		
		allTrackingBodies = eye.allTrackingBodies;
		this.me = me;
		target = eye.target;
	}

	public List<Body2> getFarSight(){
		return farSight;
	}
	public List<Body2> getNearSight(){
		return nearSight;
	}
	
	public void setTarget(Body2 target) {
		this.target = target;
	}
	public void look() {
		farSight = new ArrayList<Body2>();
		nearSight = new ArrayList<Body2>();
		for(Body2 b : allTrackingBodies) {
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
	public void display(PApplet p, SubPlot plt) {
		p.pushStyle();
		p.pushMatrix();
		float[] pp = plt.getPixelCoord(me.getPos().x, me.getPos().y);
		p.translate(pp[0], pp[1]);
		p.rotate(-me.getVel().heading());
		p.noFill();
		p.stroke(255,0,0);
		p.strokeWeight(3);
		float[] dd1 = plt.getVectorCoord(me.dna.visionDistance, me.dna.visionDistance);
		float[] dd2 = plt.getVectorCoord(me.dna.visionSafeDistance, me.dna.visionSafeDistance);
		p.rotate(me.dna.visionAngle);
		p.line(0,0,dd1[0],0);
		p.rotate(-2*me.dna.visionAngle);
		p.line(0,0,dd1[0],0);
		p.rotate(me.dna.visionAngle);
		p.arc(0,0,2*dd1[0],2*dd1[0], -me.dna.visionAngle, me.dna.visionAngle);
		p.stroke(255,0,255);
		p.circle(0,0,2*dd2[0]);
		p.popMatrix();
		p.popStyle();
	}
}
