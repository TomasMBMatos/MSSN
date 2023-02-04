package ca;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;
import tools.CustomRandomGenerator;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CellularAutomataFP {
	protected final int nrows;
	protected final int ncols;
	protected int nstates;
	private final int radius;
	protected final CellFP[][] cells;
	private int[] colors;
	protected final float cellwidth, cellheight;
	protected float xmin, ymin;
	private SubPlot plt;

	public CellularAutomataFP(PApplet p, SubPlot plt, int ncols, int nrows, int nstates, int radius) {
		this.nrows = nrows;
		this.ncols = ncols;
		this.nstates = nstates;
		this.radius = radius;
		cells = new CellFP[ncols][nrows];
		colors = new int[nstates];
		float[] bb = plt.getBoundingBox();
		xmin = bb[0];
		ymin = bb[1];

		cellwidth = bb[2] / ncols;
		cellheight = bb[3] / nrows;
		this.plt = plt;
		createCells();
		setStateColors(p);

	}

	public void setStateColors(PApplet p) {

		for (int i = 0; i < colors.length; i++) {
			colors[i] = p.color(p.random(255), p.random(255), p.random(255));
		}
	}
	
	public void setStateColors(int[] colors) {
		this.colors = colors;

	}

	public int[] getStateColors() {
		return colors;
	}

	protected void createCells() {
		for (int i = 0; i < ncols; i++) {
			for (int j = 0; j < nrows; j++) {
				cells[i][j] = new CellFP(this, i, j);
			}
		}
		setMooreCells();
	}

	public void initRandom() {
		for (int i = 0; i < ncols; i++) {
			for (int j = 0; j < nrows; j++) {
				cells[i][j].setState((int) (nstates*Math.random()));

			}
		}
	}
	
	public void initRandomCustom(double[]pmf) {
		CustomRandomGenerator crg = new CustomRandomGenerator(pmf);
		for (int i = 0; i < ncols; i++) {
			for (int j = 0; j < nrows; j++) {
				cells[i][j].setState(crg.getRandomClass());
				System.out.println(cells[i][j].getState());

			}
		}
	}
	
	public PVector getCenterCell(int row,int col) {
		float x = (col + 0.5f) * cellwidth;
		float y = (row + 0.5f) * cellheight;
		double[] w = plt.getWorldCoord(x,y);
		return new PVector ((float)w[0],(float)w[1]);
	}


	public CellFP world2Cell(double x, double y) {
		float[] xy = plt.getPixelCoord(x, y);
		return pixel2Cell(xy[0], xy[1]);
	}

	public CellFP pixel2Cell(float x, float y) {
		int row = (int) ((y - ymin) / cellheight);
		int col = (int) ((x - xmin) / cellwidth);
		if (row >= nrows)
			row = nrows - 1;
		if (col >= ncols)
			col = ncols - 1;
		if (row < 0)
			row = 0;
		if (col < 0)
			col = 0;
		return cells[col][row]; // If errors try to change order
	}

	protected void setMooreCells() {
		int nn = (int) Math.pow(2 * radius + 1, 2);
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				CellFP[] neigh = new CellFP[nn];
				int n = 0;
				for (int ii = -radius; ii <= radius; ii++) {
					int row = (i + ii + nrows) % nrows;
					for (int jj = -radius; jj <= radius; jj++) {
						int col = (j + jj + ncols) % ncols;
						neigh[n++] = cells[col][row];
					}
				}
				cells[j][i].setNeighbours(neigh);
			}
		}
	}

	public void display(PApplet p) {
		for (int i = 0; i < ncols; i++) {
			for (int j = 0; j < nrows; j++) {
				cells[i][j].display(p);
			}
		}
	}
	public void display(PApplet p, List<PImage> img) {
		for (int i = 0; i < ncols; i++) {
			for (int j = 0; j < nrows; j++) {
				cells[i][j].display(p,img);
			}
		}
	}
}
