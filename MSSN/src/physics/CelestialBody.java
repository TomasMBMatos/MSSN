package physics;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import tools.SubPlot;

public class CelestialBody extends Mover {

	private int color;
	private static double G = 6.67e-11;
	PImage img;

	public CelestialBody(PVector pos, PVector vel, float mass, float radius, int color) {
		super(pos, vel, mass, radius);
		this.color = color;
	}

	public PVector attraction(Mover m) {
		PVector r = PVector.sub(pos, m.pos);
		float dist = r.mag();
		float strenght = (float) ((G * mass * m.mass) / Math.pow(dist, 2));
		return r.normalize().mult(strenght);
	}

	public void display(PApplet p, SubPlot plt, PImage img) {
		p.pushStyle();
		float[] pp = plt.getPixelCoord(pos.x, pos.y);
		float[] r = plt.getDimInPixel(radius, radius);
		
		img.resize((int) r[0], (int) r[0]);
		p.image(img, pp[0] - r[0] / 2, pp[1] - r[0] / 2);
		p.popStyle();
	}

}
