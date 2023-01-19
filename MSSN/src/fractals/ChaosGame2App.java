package fractals;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

public class ChaosGame2App implements IProcessingApp {
	ArrayList<PVector> initialPoints;
	PVector currentPoint;
	PVector x, y, z;
	boolean classic = true;
	boolean restricted, restricted2, color, circle = false;

	@Override
	public void setup(PApplet p) {
		trianguleShape(p);
		drawInitialPoint(p);
	}

	private void drawInitialPoint(PApplet p) {
		PVector initialPoint = new PVector(p.width / 2, p.height / 2);
		p.point(initialPoint.x, initialPoint.y);
		currentPoint = initialPoint;
	}

	@Override
	public void draw(PApplet p, float dt) {
		int random;
		int pastRandom = 0;
		float times;
		boolean check = false;
		for (int i = 0; i != 500; ++i) {
			check = false;

			random = (int) p.random(initialPoints.size());

			times = 0.55f;
			if (initialPoints.size() == 3)
				times = 0.5f;
			if (initialPoints.size() == 5)
				times = 0.625f;

			if (restricted2 && !circle)
				times = 0.5f;

			if (restricted && random == 0)
				times = 0.67f;

			float px = PApplet.lerp(currentPoint.x, initialPoints.get(random).x, times);
			float py = PApplet.lerp(currentPoint.y, initialPoints.get(random).y, times);

			PVector pos = new PVector(px, py);
			if ((restricted2 && checkRandom(random, pastRandom)) || !restricted2) {
				currentPoint = pos;
				pastRandom = random;
				check = true;
			}
			if (color) {
				if (restricted2) {
					if (pastRandom == 0)
						p.stroke(255, 0, 0);
					if (pastRandom == 1)
						p.stroke(0, 255, 0);
					if (pastRandom == 2)
						p.stroke(0, 0, 255);
					if (pastRandom == 3)
						p.stroke(255, 255, 0);
					if (pastRandom == 4)
						p.stroke(0, 255, 255);
					if (pastRandom == 5)
						p.stroke(255, 0, 255);
				} else {

					if (random == 0)
						p.stroke(255, 0, 0);
					if (random == 1)
						p.stroke(0, 255, 0);
					if (random == 2)
						p.stroke(0, 0, 255);
					if (random == 3)
						p.stroke(255, 255, 0);
					if (random == 4)
						p.stroke(0, 255, 255);
					if (random == 5)
						p.stroke(255, 0, 255);
				}
			} else
				p.stroke(0);

			p.point(currentPoint.x, currentPoint.y);

		}
	}

	private boolean checkRandom(int random, int random2) {
		if (Math.abs(random - random2) == 3 || (random == 1 && random2 == 2) || (random == 2 && random2 == 1))
			return false;

		return true;
	}

	private void trianguleShape(PApplet p) {
		initialPoints = new ArrayList<PVector>();
		x = new PVector(p.width / 2, 50);
		y = new PVector(100, p.height - 200);
		z = new PVector(p.width - 100, p.height - 200);

		initialPoints.add(x);
		initialPoints.add(y);
		initialPoints.add(z);

		for (PVector p1 : initialPoints) {
			p.point(p1.x, p1.y);
		}
	}

	private void squareShape(PApplet p) {
		initialPoints = new ArrayList<PVector>();
		PVector s1 = new PVector(200, 50);
		PVector s2 = new PVector(p.width - 200, 50);
		PVector s3 = new PVector(200, p.height - 17);
		PVector s4 = new PVector(p.width - 200, p.height - 17);

		initialPoints.add(s1);
		initialPoints.add(s2);
		initialPoints.add(s3);
		initialPoints.add(s4);

		for (PVector p1 : initialPoints) {
			p.point(p1.x, p1.y);
		}
	}

	private void pentagonShape(PApplet p) {
		circle = false;
		initialPoints = new ArrayList<PVector>();

		PVector s1 = new PVector(p.width / 2, 5);
		PVector s2 = new PVector(p.width / 2 - 500, 200);
		PVector s3 = new PVector(p.width / 2 + 500, 200);
		PVector s4 = new PVector(p.width / 2 + 300, p.height - 5);
		PVector s5 = new PVector(p.width / 2 - 300, p.height - 5);

		initialPoints.add(s1);
		initialPoints.add(s2);
		initialPoints.add(s3);
		initialPoints.add(s4);
		initialPoints.add(s5);

		for (PVector p1 : initialPoints) {
			p.point(p1.x, p1.y);
		}
	}

	private void diamondShape(PApplet p) {
		circle = true;
		initialPoints = new ArrayList<PVector>();

		PVector s1 = new PVector(p.width / 2, p.height / 2 + 200);
		PVector s2 = new PVector(p.width / 2 + 200, p.height / 2);
		PVector s3 = new PVector(p.width / 2 - 200, p.height / 2);
		PVector s4 = new PVector(p.width / 2, p.height / 2 - 200);

		initialPoints.add(s1);
		initialPoints.add(s2);
		initialPoints.add(s3);
		initialPoints.add(s4);

		for (PVector p1 : initialPoints) {
			p.point(p1.x, p1.y);
		}
	}

	@Override
	public void keyPressed(PApplet p) {
		p.background(200);
		if (p.key == '+') {
			if (initialPoints.size() == 3)
				squareShape(p);
			else if (initialPoints.size() == 4 && !circle)
				pentagonShape(p);
			else if (initialPoints.size() == 5)
				diamondShape(p);
		}
		if (p.key == '-') {
			if (circle)
				pentagonShape(p);
			else if (initialPoints.size() == 5)
				squareShape(p);
			else if (initialPoints.size() == 4)
				trianguleShape(p);
		}
		if (p.key == 'c' || p.key == 'C')
			color = !color;

		if (p.key == 'x' || p.key == 'X') {
			allFalse();
			classic = true;
		}
		if (p.key == 'r' || p.key == 'R') {
			allFalse();
			restricted = true;
		}
		if (p.key == 't' || p.key == 'T') {
			allFalse();
			restricted2 = true;
		}

	}

	private void allFalse() {
		restricted = false;
		restricted2 = false;
		classic = false;

	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(PApplet p) {
		// TODO Auto-generated method stub

	}

}