package ca;

import ecosystem.Terrain;
import tools.Histogram;

public class MajorityCell extends CellFP{

	private Histogram hist;
	private CellularAutomataFP cc;
	public MajorityCell(CellularAutomataFP ca, int col, int row) {
		super(ca, col, row);
		this.cc = ca;
	}

	public void computeHistogram() {
	
		CellFP[] neighbors = getNeighbours();
		int[] data = new int[neighbors.length];
		for(int i = 0; i<neighbors.length; i++)
			data[i] = neighbors[i].getState();
		hist = new Histogram(data,cc.nstates);
	}
	
	public boolean applyMajorityRule() {
		int mode = hist.getMode(0);
		boolean changed = false;
		if(getState() != mode) {
			setState(mode);
			changed = true;
		}
		return changed;
	}
}
