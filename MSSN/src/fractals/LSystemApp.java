package fractals;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

public class LSystemApp implements IProcessingApp {

    private LSystem lsys;
    private double[] window = {-15, 15, 0, 15};
    private float[] viewport = {.1f, .1f, .8f, .8f};
    private PVector startingPos = new PVector(0, 0);
    private SubPlot plt;
    private Turtle turtle;

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);
        Rule[] rules = new Rule[2];
        rules[0] = new Rule('X', "F+[[X]-X]-F[-FX]+X");
        rules[1] = new Rule('F', "FF");

        lsys = new LSystem("F", rules);
        turtle = new Turtle(5, PApplet.radians(25));
    }

    @Override
    public void draw(PApplet p, float dt) {
        float[] bb = plt.getBoundingBox();
        p.rect(bb[0], bb[1], bb[2], bb[3]);
        turtle.setPose(startingPos, PApplet.radians(90), p, plt);
        turtle.render(lsys, p, plt);
    }

    @Override
    public void keyPressed(PApplet p) {

    }

    @Override
    public void mousePressed(PApplet p) {
        System.out.println(lsys.getSequence());
        lsys.nextGen();
        turtle.scaling(0.5f);
    }
}
