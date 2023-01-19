package ca;
import processing.core.PApplet;
import setup.IProcessingApp;


public class TestCA implements IProcessingApp {
		
	private int nrows=100;
	private int ncols=200;
	private int nstates=10;
	private int radius=1;
	
	private CellularAutomata ca;
	
	
	@Override
	public void setup(PApplet p) {
		ca = new CellularAutomata(p,ncols,nrows,nstates,radius,"","");
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

	@Override
	public void mouseReleased(PApplet parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(PApplet parent) {
		// TODO Auto-generated method stub
		
	}
}