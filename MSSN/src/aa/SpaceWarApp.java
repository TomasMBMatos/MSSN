package aa;

import java.util.ArrayList;
import java.util.List;

import physics.CelestialBody;
import physics.ParticleSystem;
import physics.Wrapper;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;
import setup.IProcessingApp;
import tools.SubPlot;

public class SpaceWarApp implements IProcessingApp {

	private float[] viewport = { 0, 0, 1, 1 };
	private double[] window = { -200, 200, -200, 200 };

	private SubPlot plt;
	List<CelestialBody> enemies, destroyed;
	List<Shot> shots, remove, enemyShots;
	PImage img;
	Boid b;
	int count, lives, times, score,endScore;
	boolean wpress, apress, spress, dpress;
	boolean restart=false;

	@Override
	public void setup(PApplet p) {

		plt = new SubPlot(window, viewport, p.width, p.height);
		img = p.loadImage("Space.jpg");
		b = new Boid(new PVector(), new PVector(), 100, 8f, p.color(250, 0, 0), p, plt);
		enemies = new ArrayList<CelestialBody>();
		shots = new ArrayList<Shot>();
		enemyShots = new ArrayList<Shot>();
		lives = 3;
		times = 0;
		score = 0;
		if(!restart) {
		SoundFile music=new SoundFile(p,"Megalovonia.mp3");
	    music.play();
	    endScore=0;
		}

		enemies.add(new CelestialBody(
				new PVector(p.random((float) window[0], (float) window[1]),
						p.random((float) window[2], (float) window[3])),
				new PVector(), 50, 30, p.color(p.random(255), p.random(255), p.random(255))));
		enemies.add(new CelestialBody(
				new PVector(p.random((float) window[0], (float) window[1]),
						p.random((float) window[2], (float) window[3])),
				new PVector(), 50, 30, p.color(p.random(255), p.random(255), p.random(255))));
		enemies.add(new CelestialBody(
				new PVector(p.random((float) window[0], (float) window[1]),
						p.random((float) window[2], (float) window[3])),
				new PVector(), 50, 30, p.color(p.random(255), p.random(255), p.random(255))));

	}

	@Override
	public void draw(PApplet p, float dt) {
		count++;
		remove = new ArrayList<Shot>();
		destroyed = new ArrayList<CelestialBody>();

		p.background(img);

		if (lives > 0) {

			for (CelestialBody enemy : enemies) {
				enemy.display(p, plt);
				if (count % 53 == 0) {
					enemyShots.add((new Shot(new Boid(enemy.getPos(), new PVector(), 50, 5, p.color(200, 0, 0), p, plt),
							new PVector(b.getPos().x, b.getPos().y))));
				}
				if(enemy.getPos().dist(b.getPos())<=enemy.radius)lives=0;
			}
			for (Shot shot : enemyShots) {
				shot.shot.applyForce(shot.force);
				if (shot.shot.moveShot(1, shot.force))
					remove.add(shot);
				shot.shot.display(p, plt, shot.shot.color);

				if (shot.shot.getPos().dist(b.getPos()) <= 5) {
					remove.add(shot);
					if (times % 2 == 0)
						lives--;
					times++;
				}
			}
			for (Shot shot : shots) {
				shot.shot.applyForce(shot.force);
				if (shot.shot.moveShot(1, shot.force))
					remove.add(shot);
				shot.shot.display(p, plt, shot.shot.color);
				for (CelestialBody enemy : enemies) {
					if (shot.shot.getPos().dist(enemy.getPos()) <= enemy.radius) {
						if(lives<3)lives++;
						score += 100;
						remove.add(shot);
						destroyed.add(enemy);
						int color = enemy.color;
						for (int i = 0; i < 5; i++) {
							enemy.color = p.color(255);
						}
						enemy.color = color;
					}

				}
			}
			for (CelestialBody enemy : destroyed) {
				enemies.remove(enemy);
				enemies.add(new CelestialBody(
						new PVector(p.random((float) window[0], (float) window[1]),
								p.random((float) window[2], (float) window[3])),
						new PVector(), 50, 30, p.color(p.random(255), p.random(255), p.random(255))));
			}
			for (Shot shot : remove) {
				shots.remove(shot);
			}
			if (wpress) {
				b.move(1, new PVector(0, 1));
			}
			if (apress) {
				b.move(1, new PVector(-1, 0));
			}

			if (spress) {
				b.move(1, new PVector(0, -1));
			}

			if (dpress) {
				b.move(1, new PVector(1, 0));
			}
			p.fill(255);
			p.textSize(15);
			p.text("Score : " + score, 50, 50);
			p.text("Lives : " + lives, 900, 650);
		}else {
			p.fill(255);
			p.textSize(15);
			p.text("Lives : " + lives, 900, 650);
			p.textSize(50);
			p.text("GAME OVER ", p.width/2-140, p.height/2-50);
			p.textSize(25);
			if(score>endScore) endScore = score;
			p.text("Score : " + score + " HighScore : " + endScore, p.width/2-70, p.height/2 +30);
			p.textSize(15);
			p.text("Press R to restart ", p.width/2-70, p.height/2 + 60);
			
		}
	
		
		

		b.display(p, plt);

	}

	@Override
	public void keyPressed(PApplet p) {
		if (p.key == 'w') {
			setAllFalse();
			wpress = true;
		}
		if (p.key == 'a') {
			setAllFalse();
			apress = true;
		}
		if (p.key == 's') {
			setAllFalse();
			spress = true;
		}
		if (p.key == 'd') {
			setAllFalse();
			dpress = true;
		}
		if (p.key == 'r' && lives==0) {
			setAllFalse();
			restart= true;
			setup(p);
		}

	}

	@Override
	public void mousePressed(PApplet p) {
		double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);
		shots.add(new Shot(new Boid(b.getPos(), new PVector(), 50, 10, p.color(0, 0, 200), p, plt),
				new PVector((float) ww[0], (float) ww[1])));

	}

	public void setAllFalse() {
		wpress = false;
		apress = false;
		spress = false;
		dpress = false;
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
