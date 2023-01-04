package ecosystem;

import ca.MajorityCA;
import physics.CelestialBody;
import processing.core.PApplet;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

import static ecosystem.WorldConstants.*;

public class Terrain extends MajorityCA {
    public Terrain(PApplet p, SubPlot plt) {
        super(p, plt, NCOLS, NROWS, NSTATES, 1);
    }
    @Override
    protected void createCells() {
        int minRT = (int) (REGENERATION_TIME[0]*1000);
        int maxRT = (int) (REGENERATION_TIME[1]*1000);
        for(int i = 0; i < ncols; i++) {
            for(int j = 0; j < nrows; j++) {
                int timeToGrow = (int) (minRT+(maxRT-minRT)*Math.random());
                cells[i][j] = new Patch(this, i, j, timeToGrow);
            }
        }
        setMooreCells();
    }

    public List<CelestialBody> getObstacles() {
        List<CelestialBody> bodies = new ArrayList<>();
        for(int i=0; i<ncols;i++) {
            for(int j=0;j<nrows;j++) {
                if(cells[i][j].getState() == PatchType.OBSTACLE.ordinal()) {
                    CelestialBody b = new CelestialBody(this.getCenterCell(i,j));
                    bodies.add(b);
                }
            }
        }
        return bodies;
    }

    public void regenerate() {
        for(int i=0; i<ncols; i++) {
            for(int j=0; j<nrows; j++) {
                ((Patch) cells[i][j]).regenerate();
            }
        }
    }
}
