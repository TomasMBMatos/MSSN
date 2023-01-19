import fractals.LSystem;
import fractals.Rule;
import fractals.Turtle;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Tree {

	private LSystem lsys;
	private Turtle turtle;
	private PVector position;
	private float length;
	private int numberOfSeasonsToGrow;
	private float scalingFactor;
	private float intervalBetweenSeasons;
	private float birthtime;
	private float nextSeasonTime;
	private PApplet p;

	public Tree(String axiom, Rule[] rules, PVector position, float length, float angle, int niter, float scalingFactor,
			float intervalBetweenSeasons, PApplet p) {
		
		lsys = new LSystem(axiom, rules);
		turtle = new Turtle(length, angle);
		this.position = position;
		numberOfSeasonsToGrow = niter;
		this.scalingFactor = scalingFactor;
		this.intervalBetweenSeasons = intervalBetweenSeasons;
		birthtime = p.millis() / 1000f;
		nextSeasonTime = birthtime + intervalBetweenSeasons;
		this.p = p;
	}
	
	public void grow() {
		float now = p.millis();
		if(now > nextSeasonTime && lsys.getGeneration() < numberOfSeasonsToGrow) {
			lsys.nextGeneration();
			length *= scalingFactor;
			turtle.setLength(length);
			nextSeasonTime = now + intervalBetweenSeasons;
		}
		
	}
	
	public void display(PApplet p,SubPlot plt) {
		turtle.setPose(position, (float) Math.PI/2, plt, p);
	}

}
