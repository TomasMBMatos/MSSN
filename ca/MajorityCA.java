package ca;

import processing.core.PApplet;
import tools.SubPlot;

public class MajorityCA extends CellularAutomataFP {

	public MajorityCA(PApplet p, SubPlot plt, int ncols, int nrows, int nstates, int radius) {
		super(p, plt, ncols, nrows, nstates, radius);

	}

	@Override
	protected void createCells() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				cells[j][i] = new MajorityCell(this, i, j);
			}
		}
		setMooreCells();
	}

	public boolean majorityRule() {
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				((MajorityCell) cells[j][i]).computeHistogram();
			}
		}
		boolean anyChanged = false;
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				boolean changed = ((MajorityCell) cells[j][i]).applyMajorityRule();
				if (changed)
					anyChanged = true;
			}
		}
		return anyChanged;
	}
}
