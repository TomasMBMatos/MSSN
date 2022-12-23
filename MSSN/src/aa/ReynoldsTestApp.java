package aa;

import physics.CelestialBody;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReynoldsTestApp implements IProcessingApp {
    private Boid wander, seeker, pursuer, boid;
    private int BoidN;
    private Flock flock;
    private float[] sacWeights = {1f, 1f, 1f};
    private double[] window = {-10, 10, -10, 10};
    private float[] view1 = {0.02f, 0.51f, 0.96f, 0.47f};
    private float[] view2 = {0.02f, 0.02f, 0.47f, 0.47f};
    private float[] view3 = {0.51f, 0.02f, 0.47f, 0.47f};
    private SubPlot plt1, plt2, plt3;
    private Body target;
    private int ix = 0;
    private boolean boidDebug = false;
    private boolean alive = false;
    @Override
    public void setup(PApplet p) {
        plt1 = new SubPlot(window, view1, p.width, p.height);
        plt2 = new SubPlot(window, view2, p.width, p.height);
        plt3 = new SubPlot(window, view3, p.width, p.height);
        // Flock
        flock = new Flock(200, .1f, 0.3f, p.color(0, 100, 200), sacWeights, p, plt1);
        BoidN = new Random().nextInt(0, 199);
        boid = flock.getBoid(BoidN);
        // Wander
        wander = new Boid(new PVector(p.random((float)window[0],(float)window[1]), p.random((float)window[2],(float)window[3])),
                    0.5f, 0.5f, p.color(255, 0, 0), p, plt2);
        wander.addBehavior(new Wander(1f));

        // Pursuer
        pursuer = new Boid(new PVector(p.random((float)window[0],(float)window[1]), p.random((float)window[2],(float)window[3])),
                0.5f, 0.5f, p.color(0, 255, 0), p, plt2);
        pursuer.addBehavior(new Pursuit(1f));
        List<CelestialBody> allTrackingBodies = new ArrayList<>();
        allTrackingBodies.add(wander);
        pursuer.setEye(new Eye(pursuer, allTrackingBodies));

        target = new Body(new PVector(), new PVector(), 1f, .3f, p.color(255, 0, 0));

        // Seeker
        seeker = new Boid(new PVector(p.random((float)window[0],(float)window[1]), p.random((float)window[2],(float)window[3])),
                0.5f, 0.5f, p.color(0, 0, 255), p, plt3);
        seeker.addBehavior(new Seek(1f));
        seeker.addBehavior(new Wander(1f));
        seeker.addBehavior(new Flee(1f));
        allTrackingBodies = new ArrayList<>();
        allTrackingBodies.add(target);
        seeker.setEye(new Eye(seeker, allTrackingBodies));
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(255);
        float[] bb = plt1.getBoundingBox();
        p.fill(255, 64);
        p.rect(bb[0], bb[1], bb[2], bb[3]);

        bb = plt2.getBoundingBox();
        p.fill(190, 170, 45, 64);
        p.rect(bb[0], bb[1], bb[2], bb[3]);

        bb = plt3.getBoundingBox();
        p.fill(120, 170, 150, 64);
        p.rect(bb[0], bb[1], bb[2], bb[3]);

        wander.applyBehaviors(dt);
        pursuer.applyBehaviors(dt);
        seeker.applyBehavior(ix, dt);
        flock.applyBehavior(dt);

        wander.display(p, plt2);
        pursuer.display(p, plt2);
        seeker.display(p, plt3);
        flock.display(p, plt1);
        if(boidDebug) {
            flock.debugBoid(p, plt1, BoidN);
        }
        target.display(p, plt3);

        if(alive) flock.updateBoidTimer();
    }

    @Override
    public void keyPressed(PApplet p) {
        if(p.key == 't') {
            ix = (ix + 1) % 2;
        }
        if(p.key == 'e') {
            boidDebug = !boidDebug;
        }
        if(p.key == 'r') {
            alive = !alive;
            flock.startBoidTimer(3);
            System.out.println(alive);
        }
    }

    @Override
    public void mousePressed(PApplet p) {
        if(plt3.isInside(p.mouseX, p.mouseY)) {
            double[] w = plt3.getWorldCoord(p.mouseX, p.mouseY);
            target.setPos(new PVector((float) w[0], (float) w[1]));
        }
    }
}
