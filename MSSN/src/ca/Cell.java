package ca;
import processing.core.PApplet;

public class Cell {
	 int col,row;
	protected int state;
	private Cell[] neighbours;
	protected CellularAutomata ca;
	
	public Cell(CellularAutomata ca,int col, int row) {
		this.ca=ca;
		this.row=row;
		this.col=col;
		this.state=0;
		this.neighbours=null;
	}
	
	public void setNeighbours(Cell[] neigh) {
		this.neighbours=neigh;
	}
	public Cell[] getNeighbours() {
		return neighbours;
	}
	public void setState(int  state) {
		this.state=state;
	}
	public int getState() {
		return state;
	}

	public void display(PApplet p) {
		p.pushStyle();
		p.noStroke();
		p.fill(ca.getStateColors()[state]);
		p.rect(ca.xmin + col*ca.cellwidth,ca.ymin + row*ca.cellheight,ca.cellwidth,ca.cellheight);
		p.popStyle();
	}
}