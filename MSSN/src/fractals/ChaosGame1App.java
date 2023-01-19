package fractals;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

public class ChaosGame1App implements IProcessingApp {
	ArrayList<PVector> initialPoints;
	PVector currentPoint;
	boolean start = false;

	@Override
	public void setup(PApplet p) {
		initShape(p);
		drawInitialRandomPoint(p);
	}

	private void initShape(PApplet p) {
		initialPoints = new ArrayList<PVector>();
	}

	private void drawInitialRandomPoint(PApplet p) {
		PVector randomInitialPoint = new PVector(p.random(p.width), p.random(p.height));
		p.point(randomInitialPoint.x, randomInitialPoint.y);
		currentPoint = randomInitialPoint;
	}

	@Override
	public void draw(PApplet p, float dt) {
		if(start)
		for (int i = 0; i != 1000; ++i) {
			int r = (int) p.random(initialPoints.size());
			float x = PApplet.lerp(currentPoint.x, initialPoints.get(r).x, 0.5f);
			float y = PApplet.lerp(currentPoint.y, initialPoints.get(r).y, 0.5f);
			p.point(x, y);
			currentPoint = new PVector(x, y);
		}
	}

	@Override
	public void keyPressed(PApplet p) {
	if(p.key == 's'|| p.key == 'S') start = true;

	}

	@Override
	public void mousePressed(PApplet p) {
		initialPoints.add(new PVector(p.mouseX,p.mouseY));

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