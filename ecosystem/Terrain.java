package ecosystem;

import ca.MajorityCA;
import physics.CelestialBody;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;
import static ecosystem.WorldConstants.PatchType.*;

import java.util.ArrayList;
import java.util.List;

import aa.Body;
import aa.Body2;

import static ecosystem.WorldConstants.*;

public class Terrain extends MajorityCA {
	PVector cavePos;

	public Terrain(PApplet p, SubPlot plt) {
		super(p, plt, NCOLS, NROWS, NSTATES, 1);
	}

	public void createRatCave(PApplet p) {
		int rndX = (int) p.random(ncols);
		int rndY = (int) p.random(nrows);
		boolean spawn = false;
		while (!spawn) {
			if (cells[rndX][rndY].getState() == EMPTY.ordinal()) {
				spawn = true;
				cells[rndX][rndY].setState(BASE.ordinal());
				PVector array = getCenterCell(rndX, rndY);
				setCavePos(array);
			}
			rndX = (int) p.random(ncols);
			rndY = (int) p.random(nrows);
		}
	}

	public void setCavePos(PVector cavePos) {
		this.cavePos = cavePos;
		
	}

	public PVector getCavePos() {
		return cavePos;
	}

	@Override
	protected void createCells() {
		int minRT = (int) (REGENERATION_TIME[0] * 1000);
		int maxRT = (int) (REGENERATION_TIME[1] * 1000);
		for (int i = 0; i < ncols; i++) {
			for (int j = 0; j < nrows; j++) {
				int timeToGrow = (int) (minRT + (maxRT - minRT) * Math.random());
				cells[i][j] = new Patch(this, i, j, timeToGrow);
			}
		}
		setMooreCells();
	}

	public List<Body2> getObstacles() {
		List<Body2> bodies = new ArrayList<Body2>();
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				if (cells[j][i].getState() == WorldConstants.PatchType.OBSTACLE.ordinal()) {
					Body2 b = new Body2(this.getCenterCell(i, j));
					bodies.add(b);
				}
			}
		}
		return bodies;
	}

	public void regenerate() {
		for (int i = 0; i < ncols; i++) {
			for (int j = 0; j < nrows; j++) {
				((Patch) cells[i][j]).regenerate();
			}
		}
	}
}