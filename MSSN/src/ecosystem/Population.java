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
	private List<Animal> allAnimals;
	private double[] window;
	private boolean mutate = true;
    private List<Animal> preys;
    private List<Animal> predators;
    private boolean zeroPrey;

	public Population(PApplet parent, SubPlot plt, Terrain terrain) {
		window = plt.getWindow();
		List<Body2> obstacles = terrain.getObstacles();
		preys = new ArrayList<Animal>();
		predators = new ArrayList<Animal>();
		allAnimals = new ArrayList<Animal>();
		for (int i = 0; i < WorldConstants.INI_PREY_POPULATION; i++) {
			PVector pos = new PVector(parent.random((float) window[0], (float) window[1]),
					parent.random((float) window[2], (float) window[3]));
			int color = parent.color(WorldConstants.PREY_COLOR[0], WorldConstants.PREY_COLOR[1],
					WorldConstants.PREY_COLOR[2]);
			Animal a = new Prey(pos, WorldConstants.PREY_MASS, WorldConstants.PREY_SIZE, color, parent, plt);
			a.addBehavior(new Wander2(1));
			a.addBehavior(new AvoidObstacle(1));
			Eye2 eye = new Eye2(a, obstacles);
			a.setEye(eye);
			allAnimals.add(a);

		}
	}

	   public void createPreyPopulation(PApplet p, SubPlot plt, Terrain terrain, List<Body2> obstacles) {
	        for(int i=0; i<INI_PREY_POPULATION; i++) {
	        	 System.out.println(terrain.getCavePos());
	        	PVector pos = terrain.getCavePos();
	            System.out.println(pos + "Hello");
	            int color = p.color(PREY_COLOR[0], PREY_COLOR[1], PREY_COLOR[2]);
	            Animal a = new Rat(pos, PREY_MASS, PREY_SIZE, color, p, plt);
	            a.addBehavior(new Wander2(1));
	            a.addBehavior(new AvoidObstacle(10));
	            Eye2 eye = new Eye2(a, obstacles);
	            a.setEye(eye);
	            preys.add(a);
	        }
	    }

	    public void createPredatorPopulation(PApplet p, SubPlot plt, Terrain terrain, List<Body2> obstacles, List<Animal> preys) {
	        for(int i=0; i<INI_PREY_POPULATION; i++) {
	            PVector pos = new PVector(p.random((float) window[0], (float) window[1]),
	                                        p.random((float)window[2], (float)window[3]));

	            Patch patch = (Patch) terrain.world2Cell(pos.x, pos.y);
	            if(patch.getState() == PatchType.OBSTACLE.ordinal()) {
	                i--;
	                continue;
	            }

	            int color = p.color(PREDATOR_COLOR[0],PREDATOR_COLOR[1],PREDATOR_COLOR[2]);

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

	    public List<Animal> getPreys(){
	    	return preys;
	    }
	    public void update(float dt, Terrain terrain) {
	        move(terrain, dt);
	        System.out.println(preys);
	        if(preys.size() > 1) {
	            for(int i=0; i<predators.size();i++) {
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
		for (Animal a : allAnimals)
			a.applyBehaviors(dt);
	}

	public void eat(Terrain terrain) {
		for (Animal a : allAnimals)
			a.eat(terrain);
	}

	public void energy_consumption(float dt, Terrain terrain) {
		for (Animal a : allAnimals)
			a.energy_consumption(dt, terrain);
	}

	public void die() {
		for (int i = allAnimals.size() - 1; i >= 0; i--) {
			Animal a = allAnimals.get(i);
			if (a.die()) {
				allAnimals.remove(a);
			}
		}
	}

	public void reproduce(boolean mutate) {
		for (int i = allAnimals.size() - 1; i >= 0; i--) {
			Animal a = allAnimals.get(i);
			Animal child = a.reproduce(mutate);
			if (child != null)
				allAnimals.add(child);
		}
	}

	public void display(PApplet p, SubPlot plt) {
		for (Animal a : allAnimals)
			a.display(p, plt);
	}

	public int getNumAnimals() {
		return allAnimals.size();
	}

	public float getMeanMaxSpeed() {
		float sum = 0;
		for (Animal a : allAnimals)
			sum += a.getDNA().maxSpeed;
		return sum / allAnimals.size();
	}

	public float[] getMeanWeights() {
		float[] sums = new float[2];
		for (Animal a : allAnimals) {
			sums[0] += a.getBehaviors().get(0).getWeight();
			sums[1] += a.getBehaviors().get(1).getWeight();
		}
		return sums;
	}

	public float getStdMaxSpeed() {
		float mean = getMeanMaxSpeed();
		float sum = 0;
		for (Animal a : allAnimals)
			sum += Math.pow(a.getDNA().maxSpeed - mean, 2);
		return (float) Math.sqrt(sum / allAnimals.size());
	}

}
