package ca;

import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

public class CellFP {
	int col, row;
	protected int state;
	private CellFP[] neighbours;
	private CellularAutomataFP ca;

	public CellFP(CellularAutomataFP ca, int col, int row) {
		this.ca = ca;
		this.row = row;
		this.col = col;
		this.state = 0;
		this.neighbours = null;
	}

	public void setNeighbours(CellFP[] neigh) {
		this.neighbours = neigh;
	}
	
	public CellFP[]  getNeighbours() {
		return this.neighbours;
	}
	public CellFP[] getCells() {
		return neighbours;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public void display(PApplet p) {
		p.pushStyle();
		p.noStroke();
		p.fill(ca.getStateColors()[state]);
		p.rect(ca.xmin + col * ca.cellwidth, row * ca.cellheight, ca.cellwidth, ca.cellheight);
		p.popStyle();
	}
	public void display(PApplet p,List<PImage> img) {
		p.pushStyle();
		p.noStroke();
		if(state == 1)p.image(img.get(0), ca.xmin + col * ca.cellwidth-12, row * ca.cellheight-8);
		else if(state == 3) p.image(img.get(1), ca.xmin + col * ca.cellwidth-15, row * ca.cellheight-10);
		else if(state == 2) p.image(img.get(2), ca.xmin + col * ca.cellwidth-15, row * ca.cellheight-10);
		else if(state == 0) p.image(img.get(3), ca.xmin + col * ca.cellwidth-15, row * ca.cellheight-10);
		else if(state == 4) p.image(img.get(4), ca.xmin + col * ca.cellwidth-15, row * ca.cellheight-10);
		else {p.fill(ca.getStateColors()[state]);
		p.rect(ca.xmin + col * ca.cellwidth, row * ca.cellheight, ca.cellwidth, ca.cellheight);
		}
		p.popStyle();
	}
}