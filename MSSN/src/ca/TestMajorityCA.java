package ca;

import processing.core.PApplet;
import setup.IProcessingApp2;
import tools.SubPlot;

public class TestMajorityCA implements IProcessingApp2 {

    private double[] window = {0, 10, 0, 10};
    private float[] viewport = {0.3f, 0.3f, 0.5f, 0.6f};
    private SubPlot plt;
    private MajorityCA ca;

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);
        ca = new MajorityCA(p, plt, 30, 40, 4, 1);
        ca.initRandomMajority();
    }

    @Override
    public void draw(PApplet p, float dt) {
        ca.display(p);
    }

    @Override
    public void mousePressed(PApplet p) {
        ca.majorityRule();
    }

    @Override
    public void mouseReleased(PApplet p) {

    }

    @Override
    public void mouseDragged(PApplet p) {

    }

    @Override
    public void keyPressed(PApplet p) {

    }
}
