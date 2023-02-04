package physics;

import java.util.ArrayList;
import java.util.List;

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
	private List<ParticleSystem> pss;
	private List<Wrapper> wrapper;
	private List<ParticleSystem> remove = new ArrayList<ParticleSystem>();

	private float speedUp = 60 * 60 * 24 * 60;
	PImage img, sunImg, mercuryImg, venusImg, earthImg, marsImg, jupiterImg, saturnImg, uranusImg, neptuneImg;

	@Override
	public void setup(PApplet p) {

		plt = new SubPlot(window, viewport, p.width, p.height);
		wrapper = new ArrayList<Wrapper>();
		
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
		
	
		
		pss= new ArrayList<ParticleSystem>();
		wrapper.add(new Wrapper(sun,sunImg));
		wrapper.add(new Wrapper(mercury,mercuryImg));
		wrapper.add(new Wrapper(venus,venusImg));
		wrapper.add(new Wrapper(earth,earthImg));
		wrapper.add(new Wrapper(mars,marsImg));
		wrapper.add(new Wrapper(jupiter,jupiterImg));
		wrapper.add(new Wrapper(saturn,saturnImg));
		wrapper.add(new Wrapper(uranus,uranusImg));
		wrapper.add(new Wrapper(neptune,neptuneImg));
		
	}

	@Override
	public void draw(PApplet p, float dt) {
		

		p.background(img);

		sun.display(p, plt, sunImg);
		
		for (Wrapper wrapper : wrapper) {
			 	if(wrapper.planet==sun) wrapper.planet.display(p,plt,wrapper.img);
			 	else {
			 		PVector f = sun.attraction(wrapper.planet);
			 		wrapper.planet.applyForce(f);
			 		wrapper.planet.move(dt * speedUp);
			 		wrapper.planet.display(p, plt, wrapper.img);
			 	}
			 	
		}
		 
		for(ParticleSystem ps:pss) {
		
		for (Wrapper wrapper : wrapper) {
			if(wrapper.planet.getPos().dist(ps.getPos()) <= wrapper.planet.radius)
		 		ps.setColor(p.color(200,0,0));
			if(wrapper.planet.getPos().dist(ps.getPos()) <= 2*wrapper.planet.radius/3) 
				ps.setColor(p.color(250,165,0));
			if(wrapper.planet.getPos().dist(ps.getPos()) <= wrapper.planet.radius/3) 
				remove.add(ps);
			
		 		
		}
		if(!remove.contains(ps)) {
			ps.applyForce(new PVector(distEarthSun/2,0));
			ps.move(dt);
		}
		ps.display(p, plt);
		}
		if(remove.size()>0) for(ParticleSystem pd:remove) {
					pss.remove(pd);
		}
		

	}

	@Override
	public void keyPressed(PApplet p) {

	}

	@Override
	public void mousePressed(PApplet p) {
		double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);
		
		int color= p.color(150, 75, 0);
		float vx=10;
		float vy=distEarthSun;
		float lifespan = p.random(1,3);
		
		ParticleSystem ps = new ParticleSystem(new PVector((float)ww[0],(float)ww[1]), new PVector(),1f,distMercurySun/3,color,lifespan, new PVector(vx,vy));
		pss.add(ps);
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
