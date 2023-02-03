package ecosystem;

import java.util.ArrayList;
import java.util.List;

import aa.AvoidObstacle;
import aa.Body2;
import aa.Eye2;
import aa.Pursuit2;
import aa.Wander2;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;
import static ecosystem.WorldConstants.*;

public class Population {
	// private List<Animal> allAnimals;
	private double[] window;
	private boolean mutate = true;
	private List<Rat> preys;
	private List<Snake> predators;
	private boolean zeroPrey;

	public Population(PApplet p, SubPlot plt, Terrain terrain) {
		window = plt.getWindow();
		List<Body2> obstacles = terrain.getObstacles();
		preys = new ArrayList<Rat>();
		predators = new ArrayList<Snake>();
		// allAnimals = new ArrayList<Animal>();
		createPreyPopulation(p, plt, terrain, obstacles);
		createPredatorPopulation(p, plt, terrain, obstacles, preys);

	}

	public void createPreyPopulation(PApplet p, SubPlot plt, Terrain terrain, List<Body2> obstacles) {
		for (int i = 0; i < INI_PREY_POPULATION; i++) {
			//System.out.println(terrain.getCavePos());
			PVector pos = terrain.getCavePos();
			System.out.println(pos + "Hello");
			int color = p.color(PREY_COLOR[0], PREY_COLOR[1], PREY_COLOR[2]);
			Rat a = new Rat(pos, PREY_MASS, PREY_SIZE, color, p, plt);
			a.addBehavior(new Wander2(2));
			a.addBehavior(new AvoidObstacle(0));
			Eye2 eye = new Eye2(a, obstacles);
			a.setEye(eye);
			preys.add(a);
		}
	}

	public int getNPreys() {
		return preys.size();
	}

	public int getNPredators() {
		return predators.size();
	}

	public void createPredatorPopulation(PApplet p, SubPlot plt, Terrain terrain, List<Body2> obstacles,
			List<Rat> preys) {
		for (int i = 0; i < INI_PREY_POPULATION; i++) {
			PVector pos = new PVector(p.random((float) window[0], (float) window[1]),
					p.random((float) window[2], (float) window[3]));

			Patch patch = (Patch) terrain.world2Cell(pos.x, pos.y);
			if (patch.getState() == PatchType.OBSTACLE.ordinal()) {
				i--;
				continue;
			}

			int color = p.color(PREDATOR_COLOR[0], PREDATOR_COLOR[1], PREDATOR_COLOR[2]);

			Snake a = new Snake(pos, PREDATOR_MASS, PREDATOR_SIZE, color, p, plt, preys);

			a.addBehavior(new Pursuit2(3));
			a.addBehavior(new AvoidObstacle(15));

			Eye2 eyePredator = new Eye2(a, obstacles);
			int nPrey = a.getNearPrey(preys);
			eyePredator.setTarget(preys.get(nPrey));
			a.setEye(eyePredator);
			predators.add(a);
		}
	}

	public List<Rat> getPreys() {
		return preys;
	}

	public void update(float dt, Terrain terrain) {
		move(terrain, dt);
		if (preys.size() > 1) {
			for (int i = 0; i < predators.size(); i++) {
				int nPrey = ((Snake) predators.get(i)).getNearPrey(preys);
				predators.get(i).getEye().setTarget(preys.get(nPrey));
			}
		}
		eat(terrain);
		energy_consumption(dt, terrain);
		reproduce(mutate);
		die();
	}

	public void move(Terrain terrain, float dt) {
		for (Animal a : preys)
			a.applyBehaviors(dt);
		for (Animal a : predators)
			a.applyBehaviors(dt);
	}

	public void eat(Terrain terrain) {
		for (Animal a : preys)
			a.eat(terrain);
		for (Animal a : predators)
			a.eat(terrain);
	}

	public void energy_consumption(float dt, Terrain terrain) {
		for (Animal a : preys)
			a.energy_consumption(dt, terrain);
		for (Animal a : predators)
			a.energy_consumption(dt, terrain);
	}

	public void die() {
		for (int i = preys.size() - 1; i >= 0; i--) {
			Animal a = preys.get(i);
			if (a.die()) {
				preys.remove(a);
			}
		}

		for (int i = predators.size() - 1; i >= 0; i--) {
			Animal b = predators.get(i);
			if (b.die()) {
				predators.remove(b);
			}
		}
	}

	public void reproduce(boolean mutate) {
		for (int i =0; i < preys.size(); i++) {
			Rat a = preys.get(i);
			Rat child = a.reproduce(mutate);

			if (child != null) {
				preys.add(child);
			}
		}
		
		for(int i =0; i < predators.size(); i++) {
			Snake a = predators	.get(i);
			Snake child = a.reproduce(mutate);
			if(child != null) {
				predators.add(child);
			}
		}

		
	}

	public void display(PApplet p, SubPlot plt) {
		for(Rat a : preys) {
			a.display(p, plt);
		}

		for(Snake a : predators) {
			a.display(p, plt);
		}
	}

	public int getNumAnimals() {
		return preys.size() + predators.size();
	}

	public float getMeanMaxSpeed() {
		float sum = 0;
		for (Animal a : preys)
			sum += a.getDNA().maxSpeed;
		return sum / preys.size();
	}

	public float[] getMeanWeights() {
		float[] sums = new float[2];
		for (Animal a : preys) {
			sums[0] += a.getBehaviors().get(0).getWeight();
			sums[1] += a.getBehaviors().get(1).getWeight();
		}
		return sums;
	}

	public float getStdMaxSpeed() {
		float mean = getMeanMaxSpeed();
		float sum = 0;
		for (Animal a : preys)
			sum += Math.pow(a.getDNA().maxSpeed - mean, 2);
		return (float) Math.sqrt(sum / preys.size());
	}

}
