package CG;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

public class ChaosGameApp implements IProcessingApp {
    ArrayList<PVector> initialPoints;
    PVector currentPoint;

    @Override
    public void setup(PApplet p) {
        initShape(p);
        drawInitialRandomPoint(p);
    }

    private void initShape(PApplet p) {
        initialPoints = new ArrayList<PVector>();
        initialPoints.add(new PVector(p.width / 2, 0));
        initialPoints.add(new PVector(0, p.height));
        initialPoints.add(new PVector(p.width, p.height));
        for(PVector p1 : initialPoints) {
            p.point(p1.x, p1.y);
        }
    }

    private void drawInitialRandomPoint(PApplet p) {
        PVector randomInitialPoint = new PVector(p.random(p.width), p.random(p.height));
        p.point(randomInitialPoint.x, randomInitialPoint.y);
        currentPoint = randomInitialPoint;
    }

    @Override
    public void draw(PApplet p, float dt) {
        for(int i = 0; i != 1000; ++i) {
            int r = (int) p.random(initialPoints.size());
            float x = PApplet.lerp(currentPoint.x, initialPoints.get(r).x, 0.5f);
            float y = PApplet.lerp(currentPoint.y, initialPoints.get(r).y, 0.5f);
            p.point(x, y);
            currentPoint = new PVector(x, y);
        }
    }

    @Override
    public void mousePressed(PApplet p) {
        
    }

    @Override
    public void mouseReleased(PApplet parent) {

    }

    @Override
    public void mouseDragged(PApplet parent) {

    }

    @Override
    public void keyPressed(PApplet p) {

    }

}