package ca;
import processing.core.PApplet;
import processing.sound.SoundFile;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CellularAutomata {
	private final int nrows;
	private final int ncols;
	private final int nstates;
	private final int radius;
	private final Cell[][] cells;
	private final int[] colors;
	protected final float cellwidth,cellheight;
	private PApplet p = new PApplet();
	protected float xmin, ymin;
	private SubPlot plt;

	private String survive;
	private String born;
	
	
	public CellularAutomata(PApplet p, SubPlot plt, int ncols, int nrows, int nstates, int radius, String survive, String born) {
		this.nrows=nrows;
		this.ncols=ncols;
		this.nstates=nstates;
		this.radius=radius;
		cells = new Cell[ncols][nrows];
		colors = new int[nstates];
		float[] bb = plt.getBoundingBox();
		xmin = bb[0];
		ymin = bb[1];
		cellwidth=bb[2]/ncols;
		cellheight=bb[3]/nrows;
		this.plt = plt;
		this.survive=survive;
		this.born=born;
		createCells();
		setStateColors(p);
		
	}
	
	public void setStateColors(PApplet p) {
		colors[0]=p.color(0);
		for(int i=1;i<colors.length;i++) {
			colors[i]=p.color(p.random(255),p.random(255),p.random(255));
		}
	}
	public int[] getStateColors() {
		return colors;
	}
	
	private void createCells() {
		for(int i=0;i<ncols;i++) {
			for(int j=0;j<nrows;j++) {
				cells[i][j]=new Cell(this,i,j);
			}
		}
		setMooreCells();
	}
	
	public void initRandom() {
		for(int i=0;i<ncols;i++) {
			for(int j=0;j<nrows;j++) {
				cells[i][j].setState((int) p.random(0,2));
				if(cells[i][j].getState()!=0) cells[i][j].setState((int) p.random(1,nstates));
				
			}
		}
	}
	public void initRandomMajority() {
		for(int i=0;i<ncols;i++) {
			for(int j=0;j<nrows;j++) {
				cells[i][j].setState((int) p.random(0,nstates));
				
				
			}
		}
	}
	public void init() {
		
	}

	public Cell world2Cell(double x, double y) {
		float[] xy = plt.getPixelCoord(x, y);
		return pixel2cell(xy[0], xy[1]);
	}
	public Cell pixel2cell(float x, float y) {
		int row = (int) ((int)(y-ymin)/cellheight);
		int col = (int) ((int)(x-xmin)/cellwidth);
		if (row >= nrows) row = nrows - 1;
		if (col >= ncols) col = ncols - 1;
		if(row < 0) row = 0;
		if(col < 0) row = 0;
		return cells[col][row];
	}
	
	private void setMooreCells() {
		int nn=(int)Math.pow(2*radius+1, 2);
		for(int i=0;i<nrows;i++) {
			for(int j=0;j<ncols;j++) {
				Cell[]neigh=new Cell[nn];
				int n=0;
				for(int ii=-radius;ii<=radius;ii++) {
					int row = (i + ii + nrows) % nrows;
					for(int jj=-radius;jj<=radius;jj++) {
						int col = (j + jj + ncols) % ncols;
						neigh[n++]=cells[col][row];
					}
				}
				cells[j][i].setNeighbours(neigh);
			}
		}
	}

	public void setAliveOrDead() {
		HashMap<Cell,Integer> alive = new HashMap<Cell,Integer>();
		HashMap<Cell,Integer> dead = new HashMap<Cell,Integer>();

		String[] surviveList= survive.split("");
		String[] bornList= born.split("");

		


		for(int i=0;i<nrows;i++) {
			for(int j=0;j<ncols;j++) {
				Cell currentCell = cells[j][i];
				Cell[] neigh = currentCell.getCells();
				int aliveNeigh = 0;
				HashMap<Integer, Integer> countmap = new HashMap<Integer, Integer>();
				int major=0;
				int times=0;
				
				for (Cell cell : neigh) {
					int currState = cell.getState();
					if (currState != 0) {
						countmap.merge(currState, 1, Integer::sum);	
						aliveNeigh++;
					}
					
				}
			
				for (int g : countmap.keySet()) {
					
					int gett= countmap.get(g);
					
					if(gett>times) {
						times = gett;
						major = g;
					}
					if(times==gett) {
						int d= (int) p.random(0,2);
						if(d==0) major = g;
					}
					}
				
				if(currentCell.getState()==0){
					
					for (int k = 0; k < bornList.length; k++){
						
						
						if (aliveNeigh == Integer.parseInt(bornList[k])) {
							alive.put(currentCell,major);

							break;
						}
						if (k==bornList.length-1) dead.put(currentCell,0);
					
					} 
				}
				if(currentCell.getState()!=0){
					for (int k = 0; k < surviveList.length; k++){

						if (aliveNeigh == Integer.parseInt(surviveList[k]) + 1 ) {
							 alive.put(currentCell,major);
							break;
						}
						if (k==surviveList.length-1) dead.put(currentCell,0);
					}
				}
			}
		}
		for (Cell cell : alive.keySet()) {
			cell.setState(alive.get(cell));
	
			
		}
		for (Cell cell : dead.keySet()) {
			cell.setState(dead.get(cell));
		}
	}
	public void majority() {
		HashMap<Cell,Integer> alive = new HashMap<Cell,Integer>();
		for(int i=0;i<nrows;i++) {
			for(int j=0;j<ncols;j++) {
				Cell currentCell = cells[j][i];
				Cell[] neigh = currentCell.getCells();
				HashMap<Integer, Integer> countmap = new HashMap<Integer, Integer>();
				int major=0;
				int times=0;
				for (Cell cell : neigh) {
					int currState = cell.getState();	
					if(currentCell!= cell)
						countmap.merge(currState, 1, Integer::sum);	
				}
				for (int g : countmap.keySet()) {				
					int gett= countmap.get(g);			
					if(gett>times) {
						times = gett;
						major = g;
					}
					if(gett==times) {
						int d= (int)p.random(0,2);
						if(d==1) major = g;
					}
					
				}
				
					 alive.put(currentCell, major);
			}
		}
		for (Cell cell : alive.keySet()) {
			cell.setState(alive.get(cell));
		}
	}

	public void display(PApplet p) {
		for(int i=0;i<ncols;i++) {
			for(int j=0;j<nrows;j++) {
				cells[i][j].display(p);
			}
		}
	}
}
