package ca;
import processing.core.PApplet;
import setup.IProcessingApp;
import tools.SubPlot;


public class TestCA implements IProcessingApp {
		
	private int nrows=100;
	private int ncols=200;
	private int nstates=10;
	private int radius=1;
	
	private CellularAutomata ca;
	private SubPlot plt;
	private double[] window = {0, 10, 0, 10};
	private float[] viewport = {0.3f, 0.3f, 0.5f, 0.6f};
	
	
	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);
		ca = new CellularAutomata(p, plt, ncols,nrows,nstates,radius,"","");
		ca.initRandom();
		ca.display(p);
	}

	@Override
	public void draw(PApplet p, float dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(PApplet p) {
		Cell cell=ca.pixel2cell(p.mouseX, p.mouseY);
		cell.setState(nstates-1);
	}
}