package ca;
import processing.core.PApplet;
import setup.IProcessingApp;
import tools.SubPlot;


public class TestCAFP implements IProcessingApp {
		
	private int nrows=15;
	private int ncols=20;
	private int nstates=4;
	private int radius=2;
	private SubPlot plt;
	private CellularAutomataFP ca;
	private double[] window = {0,10,0,10};
	private float[] viewport = {0.3f,0.3f,0.5f,0.6f};
	
	
	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window,viewport,p.width,p.height);
		ca = new CellularAutomataFP(p,plt,ncols,nrows,nstates,radius);
		ca.initRandom();
		ca.display(p);
	}

	@Override
	public void draw(PApplet p, float dt) {
		ca.display(p);
		
	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(PApplet p) {
		CellFP cell=ca.pixel2Cell(p.mouseX, p.mouseY);
		CellFP[] neigh = cell.getNeighbours();
		for(int i = 0; i<neigh.length;i++) {
			neigh[i].setState(nstates-1);
		}
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