package aa;

import java.util.ArrayList;
import java.util.List;

import physics.CelestialBody;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;
import tools.SubPlot;

public class Boid extends CelestialBody {
	
	private SubPlot plt;
	private PShape shape;
	protected DNA dna;
	protected Eye eye;
	private List<Behavior> behaviors;
	protected float phiWander;
	private double[] window;
	
	
	protected Boid(PVector pos,PVector vel,float mass,float radius, int color,PApplet p, SubPlot plt) {
		super(pos, vel, mass, radius, color);
		behaviors = new ArrayList<Behavior>();
		this.plt=plt;
		window = plt.getWindow();
		dna = new DNA();
		setShape(p,plt);
	}
	
	public void setEye(Eye eye) {
		this.eye = eye;
		
	}
	
	public void setShape(PApplet p, SubPlot plt) {
		float[] rr = plt.getDimInPixel(radius,radius);
		shape = p.createShape();
		shape.beginShape();
		shape.vertex(-rr[0], rr[0]/2);
		shape.vertex(rr[0], 0);
		shape.vertex(-rr[0], -rr[0]/2);
		shape.vertex(-rr[0]/2, 0);
		
		shape.endShape(PConstants.CLOSE);
	}
	
	public void addBehavior(Behavior behavior) {
		behaviors.add(behavior);
	}
	public void removeBehavior(Behavior behavior) {	
		if(behaviors.contains(behavior))
		behaviors.remove(behavior);
	}
	
	public void applyBehavior(int i, float dt) {
		
		eye.look();
		Behavior behavior = behaviors.get(i);
		PVector vd = behavior.getDesiredVelocity(this);
		move(dt,vd);
		
	}
	
	public void applyBehaviors(float dt) {
		eye.look();
		PVector vd = new PVector();
		for(Behavior behavior : behaviors) {
			PVector vdd = behavior.getDesiredVelocity(this);
			vdd.mult(behavior.getWeight());
			vd.add(vdd);
		}
		move(dt,vd);
	}
	
	public void move(float dt, PVector vd) {
		vd.normalize().mult(dna.maxSpeed);
		PVector fs = PVector.sub(vd, vel);
		applyForce(fs.limit(dna.maxForce));
		super.move(dt);
		if(pos.x < window[0])
			pos.x += window[1] -window[0];
		if(pos.x >= window[1])
			pos.x -= window[1] - window[0];
		if(pos.y < window[2])
			pos.y += window[3] -window[2];
		if(pos.y >= window[3])
			pos.y -= window[3] -window[2];
	}
	
	public void display(PApplet p, SubPlot plt) {
		float[] pp = plt.getPixelCoord(pos.x,pos.y);
		p.translate(pp[0], pp[1]);
		p.rotate(vel.heading());
		p.shape(shape);
	}

}
