package aa;

import java.util.ArrayList;
import java.util.List;

import physics.CelestialBody;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

public class FlockLeaderApp implements IProcessingApp {
    private Flock flock;
    private Boid flockLeader;
    private float[] sacWeights = { 1f, 1f, 1f };
    private double[] window = { -10, 10, -10, 10 };
    private float[] viewport = { 0, 0, 1, 1, };
    private SubPlot plt;
    private Body target;
    private PVector toAdd = new PVector(1,0);

    @Override
    public void setup(PApplet p) {
        target = new Body(new PVector(), new PVector(), 1f, 0.3f, p.color(0));
        plt = new SubPlot(window, viewport, p.width, p.height);
        flockLeader = new Boid(new PVector(),new PVector(), 0.03f, 0.5f, p.color(255, 0, 0), p, plt);
        flockLeader.addBehavior2(new Seek(1f));
        List<CelestialBody> track = new ArrayList<>();
        track.add(target);
        flockLeader.setEye(new Eye(flockLeader, track));
        flock = new Flock(100, .5f, .3f, p.color(p.random(255)), sacWeights, p, plt, flockLeader);
    }

    @Override
    public void draw(PApplet p, float dt) {
        // p.background(255);
        float[] bb = plt.getBoundingBox();
        p.fill(255, 64);
        p.rect(bb[0], bb[1], bb[2], bb[3]);
        flock.applyBehavior(dt);
        flock.display(p, plt);
        target.setPos(PVector.add(flockLeader.getPos(), toAdd));
        target.move(dt);
        flockLeader.applyBehaviors(dt);
        flockLeader.display(p, plt);

    }

    @Override
    public void mousePressed(PApplet p) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(PApplet p) {
        if(p.key == 'w')
            toAdd = new PVector(0,1);
        else if(p.key == 'a')
            toAdd = new PVector(-1,0);
        else if(p.key == 's')
            toAdd = new PVector(0,-1);
        else if(p.key == 'd')
            toAdd = new PVector(1,0);

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
