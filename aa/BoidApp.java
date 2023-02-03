package aa;

import java.util.ArrayList;
import java.util.List;

import physics.CelestialBody;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

public class BoidApp implements IProcessingApp {

	private Boid b, b2;
	private double[] window = { -10, 10, -10, 10 };
	private float[] viewport = { 0, 0, 1, 1 };
	private SubPlot plt;
	private CelestialBody target;
	private List<CelestialBody> allTrackingBodies;
	private int index = 2;
	private int count = 0;
	Boid enemy = null;
	boolean velocity = false;
	boolean stop = false;
	boolean patrol = false;
	boolean selection = false;
	boolean wander = false;
	PVector wanderPos=null;
	private List<PVector> points;

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);

		b = new Boid(new PVector(), new PVector(), 1, 0.5f, p.color(0), p, plt);
		b.addBehavior(new Seek(1f));
		b.addBehavior(new Flee(1f));
		b.addBehavior(new Wander(1f));
		b.addBehavior(new Pursuit(1f));

		target = new CelestialBody(new PVector(), new PVector(), 1f, 0.2f, p.color(255, 200, 0));
		allTrackingBodies = new ArrayList<CelestialBody>();
		allTrackingBodies.add(target);
		Eye eye = new Eye(b, allTrackingBodies);
		b.setEye(eye);
		points = new ArrayList<PVector>();
	}

	@Override
	public void draw(PApplet p, float dt) {
		
		p.background(255);
		
		p.textSize(10);
		p.fill(0);
		p.text("Controls", 10, 10);
		p.text("1-> Seek", 10, 30);
		p.text("2-> Flee", 10, 50);
		p.text("3-> Wander", 10, 70);
		p.text("4-> Pursuit", 10, 90);
		p.text("F-> Modify Force with +/-", 10, 110);
		p.text("V-> Modify Velocity with +/-", 10, 130);
		p.text("P-> Stops Boid", 10, 150);
		p.text("W-> New Wander", 10, 170);
		p.text("S-> Activates Patrol, Press with LMB the loop of positions / Press again to confirm", 10, 190);
		
		if (patrol) {
			if (points.size() > 0) {
				target.setPos(points.get(count));
				if (b.getPos().dist(target.getPos()) <= 3) {
					if (count == points.size() - 1)
						count = 0;
					else
						count++;
				}
			}
		}
		if(wander) {
			
			if(wanderPos==null) {
				wanderPos = new PVector(p.random((float)window[0],(float)window[1]),p.random((float)window[2],(float)window[3]));
				target.setPos(wanderPos);
			}else {
				if(b.getPos().dist(target.getPos()) <= 3) {
					wanderPos = new PVector(p.random((float)window[0],(float)window[1]),p.random((float)window[2],(float)window[3]));
					target.setPos(wanderPos);
				}
			}
			
			
			
		}
		if (!stop && !selection)
			b.applyBehavior(index, dt);

		b.display(p, plt);


	}

	@Override
	public void keyPressed(PApplet p) {
		if (p.key == '1') {
			index = 0;
			patrol = false;
			wander = false;
		}

		if (p.key == '2') {
			index = 1;
			patrol = false;
			wander = false;
		}

		if (p.key == '3') {
			index = 2;
			patrol = false;
			wander = false;
		}

		if (p.key == '4') {
			index = 3;
			patrol = false;
			wander = false;
		}

		if (p.key == 's') {
			index = 0;
			patrol = true;
			selection = !selection;
		}
		if (p.key == 'w') {
			index=2;
			wander = true;
		}
		if (p.key == 'v')
			velocity = true;
		if (p.key == 'f')
			velocity = false;
		if (p.key == '+') {
			if (velocity) {
				PVector velocity = b.getVel();
				b.setVel(velocity.mult(2));
				System.out.println(b.getVel());
			} else {
				float mass = b.getMass();
				b.setMass(mass * 2);
				System.out.println(b.getMass());
			}
		}
		if (p.key == '-')
			if (velocity) {
				PVector velocity = b.getVel();
				b.setVel(velocity.div(2));
				System.out.println(b.getPos());
			} else {
				float mass = b.getMass();
				b.setMass(mass / 2);
				System.out.println(b.getMass());
			}
		if (p.key == 'p')
			stop = !stop;

	}

	@Override
	public void mousePressed(PApplet p) {

		double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);
		if (!patrol) {
			target.setPos(new PVector((float) ww[0], (float) ww[1]));
			System.out.println(target.getPos());
			points = new ArrayList<PVector>();
		} else {
			if (selection) {
				points.add(new PVector((float) ww[0], (float) ww[1]));
			}
		}

	}

	@Override
	public void mouseReleased(PApplet parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(PApplet parent) {
		// TODO Auto-generated method stub
		
	}

}
