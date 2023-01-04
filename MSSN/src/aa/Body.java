package aa;

import physics.CelestialBody;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Body extends CelestialBody {
    public Body(PVector pos, PVector vel, float mass, float radius, int color) {
        super(pos, vel, mass, radius, color);
    }

    public void display(PApplet p, SubPlot plt) {
        p.pushStyle();
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        float[] r = plt.getVectorCoord(radius, radius);

        p.fill(color);
        p.circle(pp[0], pp[1], r[0]);
    }
}
