package aa;

import physics.CelestialBody;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static processing.core.PApplet.abs;
import static processing.core.PApplet.max;

public class Flock {
    private List<Boid> boids;

    public Flock(int nboids, float mass, float radius, int color, float[] sacWeights, PApplet p, SubPlot plt) {
        double[] w = plt.getWindow();
        boids = new ArrayList<Boid>();
        for(int i=0; i<nboids; i++) {
            float x = p.random((float)w[0], (float)w[1]);
            float y = p.random((float)w[2], (float)w[3]);
            Boid b = new Boid(new PVector(x,y),new PVector(), mass, radius, color, p, plt);
            b.addBehavior2(new Separate(sacWeights[0]));
            b.addBehavior2(new Align(sacWeights[1]));
            b.addBehavior2(new Cohesion(sacWeights[2]));
            boids.add(b);
        }
        List<CelestialBody> bodies = bL2cBL(boids);
        for(Boid b: boids) {
            b.setEye(new Eye(b, bodies));
        }
    }

    public Flock(int nboids, float mass, float radius, int color, float[] sacWeights, PApplet p, SubPlot plt, Boid leader) {
        this(nboids,mass,radius,color,sacWeights,p,plt);
        List<CelestialBody> bodies = bL2cBL(boids);
        //bodies.add(0, leader);
        for(Boid b : boids) {
            b.setEye(new Eye(b, bodies));
            b.addBehavior2(new Pursuit(1f));
        }
    }

    private List<CelestialBody> bL2cBL(List<Boid> boids) {
        List<CelestialBody> bodies = new ArrayList<CelestialBody>();
        for(Boid b: boids) {
         //   bodies.add(b);
        }
        return bodies;
    }

    public void applyBehavior(float dt) {
        for(Boid b: boids) {
            b.applyBehaviors2(dt);
        }
    }

    public Boid getBoid(int i) {
        return boids.get(i);
    }

    public void display(PApplet p, SubPlot plt) {
        for(Boid b: boids) {
            b.display(p, plt);
        }
    }

    public void displayBoidsInRange(PApplet p, SubPlot plt, int boid) {
        float visionDistance = boids.get(boid).dna.visionDistance;
        PVector pos = boids.get(boid).getPos();
        List<Boid> inRange = new ArrayList<>();
        List<Boid> outRange = new ArrayList<>();
        for(Boid b: boids) {
            float dx = abs(b.getPos().x - pos.x);
            float dy = abs(b.getPos().y - pos.y);
            if(dx*dx + dy*dy <= visionDistance*visionDistance) {
                inRange.add(b);
            }
            else {
                outRange.add(b);
            }
        }
        for(Boid b: inRange) {
            b.setShape(p, plt, b.getRadius(),p.color(0,255,0));
        }
        for(Boid b: outRange) {
            b.setShape(p, plt, b.getRadius(),p.color(0, 100, 200));
        }
    }

    public void startBoidTimer(float maxTime, PApplet p) {
     
        for(Boid b: boids) {
            b.setAlive();
            float time = p.random(1f, maxTime);
            b.setLifeTimer(time);
        }
    }

    public void updateBoidTimer() {
        boids.removeIf(b -> b.getLifeTimer() <= 0);
    }


    public void debugBoid(PApplet p, SubPlot plt, int boid) {
        Boid db = boids.get(boid);
        db.getEye().display(p, plt);
        db.displayMoveVector(p, plt);
        displayBoidsInRange(p, plt, boid);
    }
}
