package physics;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

public class SolarSystemApp implements IProcessingApp {

	private float sunMass = 1.989e30f;
	private float earthMass = 5.97e24f;
	private float mercuryMass = 0.0553e21f;
	private float venusMass = 4.8685e24f;
	private float marsMass = 6.4185e23f;
	private float jupiterMass = 1.8986e24f;
	private float saturnMass = 5.68460e27f;
	private float uranusMass = 8.6832e25f;
	private float neptuneMass = 1.02430e26f;

	private float distEarthSun = 1.496e11f;
	private float distMercurySun = 5.791e10f;
	private float distVenusSun = 1.082e11f;
	private float distMarsSun = 2.2794e11f;
	private float distJupiterSun = 7.7833e11f;
	private float distSaturnSun = 1.4294e12f;
	private float distUranusSun = 2.87099e12f;
	private float distNeptuneSun = 4.504e12f;

	private float earthSpeed = 3e4f;
	private float mercurySpeed = 4.8e4f;
	private float venusSpeed = 3.5e4f;
	private float marsSpeed = 2.4e4f;
	private float jupiterSpeed = 1.3e4f;
	private float saturnSpeed = 9e3f;
	private float uranusSpeed = 6e3f;
	private float neptuneSpeed = 5e3f;

	private float[] viewport = { 0.2f, 0.2f, 0.6f, 0.6f };
	private double[] window = { -1.2 * distUranusSun, 1.2 * distUranusSun, -1.2 * distUranusSun, 1.2 * distUranusSun };

	private SubPlot plt;
	private CelestialBody sun, earth, mercury, venus, mars, jupiter, saturn, uranus, neptune;

	private float speedUp = 60 * 60 * 24 * 60;
	PImage img, sunImg, mercuryImg, venusImg, earthImg, marsImg, jupiterImg, saturnImg, uranusImg, neptuneImg;

	@Override
	public void setup(PApplet p) {

		plt = new SubPlot(window, viewport, p.width, p.height);
		sun = new CelestialBody(new PVector(), new PVector(), sunMass * 3, distEarthSun * 2, p.color(255, 128, 0));
		earth = new CelestialBody(new PVector(0, distEarthSun * 3), new PVector(earthSpeed, 0), earthMass,
				distEarthSun * 2, p.color(0, 180, 120));
		mercury = new CelestialBody(new PVector(0, distMercurySun * 3), new PVector(mercurySpeed, 0), mercuryMass,
				distMercurySun * 2, p.color(0, 180, 120));
		venus = new CelestialBody(new PVector(0, distVenusSun * 3), new PVector(venusSpeed, 0), venusMass,
				distVenusSun * 2, p.color(0, 180, 120));
		mars = new CelestialBody(new PVector(0, distMarsSun * 3), new PVector(marsSpeed, 0), marsMass, distMarsSun * 2,
				p.color(0, 180, 120));
		jupiter = new CelestialBody(new PVector(0, distJupiterSun * 3), new PVector(jupiterSpeed, 0), jupiterMass,
				distJupiterSun, p.color(0, 180, 120));
		saturn = new CelestialBody(new PVector(0, distSaturnSun * 3), new PVector(saturnSpeed, 0), saturnMass,
				distJupiterSun, p.color(0, 180, 120));
		uranus = new CelestialBody(new PVector(0, distUranusSun * 2), new PVector(uranusSpeed, 0), uranusMass,
				distMarsSun, p.color(0, 180, 120));
		neptune = new CelestialBody(new PVector(0, distNeptuneSun+2*distUranusSun/3 ), new PVector(neptuneSpeed, 0), neptuneMass,
				distMarsSun, p.color(0, 180, 120));

		img = p.loadImage("Space.jpg");
		sunImg = p.loadImage("Sun4.png");
		mercuryImg = p.loadImage("mercury3.png");
		venusImg = p.loadImage("venus3.png");
		earthImg = p.loadImage("earth2.png");
		marsImg = p.loadImage("mars.png");
		jupiterImg = p.loadImage("jupiter.png");
		saturnImg = p.loadImage("saturn.png");
		uranusImg = p.loadImage("uranus3.png");
		neptuneImg = p.loadImage("neptune.png");
	}

	@Override
	public void draw(PApplet p, float dt) {

		p.background(img);

		sun.display(p, plt, sunImg);

		PVector f = sun.attraction(mercury);
		mercury.applyForce(f);
		mercury.move(dt * speedUp);
		mercury.display(p, plt, mercuryImg);

		f = sun.attraction(venus);
		venus.applyForce(f);
		venus.move(dt * speedUp);
		venus.display(p, plt, venusImg);

		f = sun.attraction(earth);
		earth.applyForce(f);
		earth.move(dt * speedUp);
		earth.display(p, plt, earthImg);

		f = sun.attraction(mars);
		mars.applyForce(f);
		mars.move(dt * speedUp);
		mars.display(p, plt, marsImg);

		f = sun.attraction(jupiter);
		jupiter.applyForce(f);
		jupiter.move(dt * speedUp);
		jupiter.display(p, plt, jupiterImg);

		f = sun.attraction(saturn);
		saturn.applyForce(f);
		saturn.move(dt * speedUp);
		saturn.display(p, plt, saturnImg);

		f = sun.attraction(uranus);
		uranus.applyForce(f);
		uranus.move(dt * speedUp);
		uranus.display(p, plt, uranusImg);

		f = sun.attraction(neptune);
		neptune.applyForce(f);
		neptune.move(dt * speedUp);
		neptune.display(p, plt, neptuneImg);

	}

	@Override
	public void keyPressed(PApplet p) {

	}

	@Override
	public void mousePressed(PApplet p) {

	}

}
