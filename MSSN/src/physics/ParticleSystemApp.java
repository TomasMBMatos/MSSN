package physics;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

public class ParticleSystemApp implements IProcessingApp{
	
	private List<ParticleSystem> pss;
	private double[] window = {-10, 10, -10, 10};
	private float[] viewport = {0,0,1,1};
	private SubPlot plt;
	

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window,viewport,p.width,p.height);
		pss= new ArrayList<ParticleSystem>();
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(100,100,250);
		
		for(ParticleSystem ps:pss) {
			ps.applyForce(new PVector(0,-1));
			
		}
		for(ParticleSystem ps:pss) {
		ps.move(dt);
		ps.display(p, plt);
		}
		
	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(PApplet p) {
		double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);
		
		int color= p.color(p.random(255),p.random(255),p.random(255));
		float vx=p.random(4,10);
		float vy=p.random(4,10);
		float lifespan = p.random(1,3);
		
		ParticleSystem ps = new ParticleSystem(new PVector((float)ww[0],(float)ww[1]), new PVector(),1f,0.2f,color,lifespan, new PVector(vx,vy));
		pss.add(ps);
		
	}

}
