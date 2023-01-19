package aa;

import physics.CelestialBody;
import physics.Mover;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Body2 extends Mover {
	protected int color;
	protected float radius;
    public Body2(PVector pos, PVector vel, float mass, float radius, int color) {
        super(pos, vel, mass);
        this.color = color;
        this.radius = radius;
    }

    public Body2(PVector pos) {
    	super(pos, new PVector(),0f);
    }
    public void display(PApplet p, SubPlot plt) {
        p.pushStyle();
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        float[] r = plt.getVectorCoord(radius, radius);

        p.fill(color);
        p.circle(pp[0], pp[1], r[0]);
    }
}
