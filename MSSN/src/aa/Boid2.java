package aa;

import java.util.ArrayList;
import java.util.List;

import physics.CelestialBody;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;
import tools.SubPlot;

public class Boid2 extends Body2 {
	
	private SubPlot plt;
	private PShape shape;
	protected DNA dna;
	protected Eye2 eye;
	protected List<Behavior2> behaviors;
	protected float phiWander;
	private double[] window;
	private float sumWeights;

	private float lifeTimer;
	private boolean alive = false;

	
	
	protected Boid2(PVector pos,float mass,float radius, int color,PApplet p, SubPlot plt) {
		super(pos, new PVector(), mass, radius, color);
		behaviors = new ArrayList<Behavior2>();
		this.plt=plt;
		window = plt.getWindow();
		dna = new DNA();
		setShape(p,plt);
	}
	
	public void mutateBehaviors() {
		for(Behavior2 behavior : behaviors) {
			if(behavior instanceof AvoidObstacle) {
				behavior.weight += DNA.random(-0.5f, -0.5f);
				behavior.weight = Math.max(0, behavior.weight);
				
			}
		}
		updateSumWeights();
	}

	public List<Behavior2> getBehaviors() {
		return behaviors;
	}

	public DNA getDNA() {
		return dna;
	}
	
	public void setEye(Eye2 eye) {
		this.eye = eye;
		
	}
	public Eye2 getEye() {
		return eye;
	}

	public float getRadius() {
		return radius;
	}
	public void setShape(PApplet p, SubPlot plt, float radius, int color) {
		this.radius = radius;
		this.color = color;
		setShape(p, plt);
	}

	public void setShape(PApplet p, SubPlot plt) {
		float[] rr = plt.getVectorCoord(radius,radius);
		shape = p.createShape();
		shape.beginShape();
		shape.noStroke();
		shape.fill(color);
		shape.vertex(-rr[0], rr[0]/2);
		shape.vertex(rr[0], 0);
		shape.vertex(-rr[0], -rr[0]/2);
		shape.vertex(-rr[0]/2, 0);
		
		shape.endShape(PConstants.CLOSE);
	}

	public float getLifeTimer() {
		return lifeTimer;
	}

	public void setAlive() {
		this.alive = !alive;
	}
	public void setLifeTimer(float time) {
		this.lifeTimer = time;
	}
	private void updateSumWeights() {
		sumWeights = 0;
		for(Behavior2 behavior: behaviors) {
			sumWeights += behavior.getWeight();
		}
	}

	public void addBehavior(Behavior2 behavior) {
		behaviors.add(behavior);
		updateSumWeights();
	}
	public void removeBehavior(Behavior2 behavior) {	
		if(behaviors.contains(behavior))
			behaviors.remove(behavior);
		updateSumWeights();
	}
	
	public void applyBehavior(int i, float dt) {
		if(eye != null) eye.look();
		Behavior2 behavior = behaviors.get(i);
		PVector vd = behavior.getDesiredVelocity(this);
		move(dt,vd);
		
	}
	
	public void applyBehaviors(float dt) {
		if(eye != null) eye.look();
		PVector vd = new PVector();
		for(Behavior2 behavior : behaviors) {
			PVector vdd = behavior.getDesiredVelocity(this);
			vdd.mult(behavior.getWeight()/sumWeights);
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
		if(alive) {
			lifeTimer -= dt;
			System.out.println(lifeTimer);
		}
	}
	
	public void display(PApplet p, SubPlot plt) {
		p.pushMatrix();
		float[] pp = plt.getPixelCoord(pos.x,pos.y);
		float[] vv = plt.getVectorCoord(vel.x, vel.y);
		PVector vaux = new PVector(vv[0], vv[1]);
		p.translate(pp[0], pp[1]);
		p.rotate(-vaux.heading());
		p.shape(shape);
		p.popMatrix();
	}

	public void displayMoveVector(PApplet p, SubPlot plt) {
		p.pushStyle();
		p.pushMatrix();
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		p.translate(pp[0], pp[1]);
		p.rotate(-vel.heading());
		p.noFill();
		p.stroke(0,255,0);
		p.strokeWeight(3);
		float[] dd = plt.getVectorCoord(dna.visionDistance, dna.visionDistance);
		p.line(0,0,dd[0],0);
		p.popMatrix();
		p.popStyle();
	}
}