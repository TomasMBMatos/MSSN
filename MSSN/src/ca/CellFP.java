package ca;

import processing.core.PApplet;

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
}