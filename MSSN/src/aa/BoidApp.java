package aa;

import java.util.ArrayList;
import java.util.List;

import physics.CelestialBody;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

public class BoidApp implements IProcessingApp {

	private Boid b;
	private double[] window = {-10,10,-10,10};
	private float[] viewport = {0,0,1,1};
	private SubPlot plt;
	private CelestialBody target;
	private List<CelestialBody> allTrackingBodies;
	private int index = 2;
	
	
	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window,viewport,p.width,p.height);
		b = new Boid(new PVector(),1,0.5f,p.color(0),p,plt);
		b.addBehavior(new Seek(1f));
		b.addBehavior(new Flee(1f));
		b.addBehavior(new Wander(1f));
		
		target = new CelestialBody(new PVector(),new PVector(),1f,0.2f,p.color(255,0,0));
		allTrackingBodies = new ArrayList<CelestialBody>();
		allTrackingBodies.add(target);
		Eye eye = new Eye(b,allTrackingBodies);
		b.setEye(eye);
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(255);
		
		b.applyBehavior(index,dt);
		
		b.display(p, plt);
	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(PApplet p) {
		
		double[] ww = plt.getWorldCoord(p.mouseX,p.mouseY);
		target.setPos(new PVector((float) ww[0],(float) ww[1]));
		
	}

}
