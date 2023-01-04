package ca;

import processing.core.PApplet;
import tools.SubPlot;

public class MajorityCA extends CellularAutomata {

    public MajorityCA(PApplet p, SubPlot plt, int ncols, int nrows, int nstates, int radius) {
        super(p, plt, ncols, nrows, nstates, radius, "", "");
    }

    @Override
    protected void createCells() {
        for(int i = 0; i < ncols; i++) {
            for(int j = 0; j < nrows; j++) {
                cells[i][j] = new MajorityCell(this, i, j);
            }
        }
        setMooreCells();
    }

    public boolean majorityRule() {
        for(int i = 0; i < ncols; i++) {
            for(int j = 0; j < nrows; j++) {
                ((MajorityCell) cells[i][j]).computeHistogram();
            }
        }

        boolean anyChanged = false;
        for(int i = 0; i < ncols; i++) {
            for(int j = 0; j < nrows; j++) {
                boolean changed = ((MajorityCell) cells[i][j]).applyMajorityRule();
                if(changed) anyChanged = true;
            }
        }
        return anyChanged;
    }
}
