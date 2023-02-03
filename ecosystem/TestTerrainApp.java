package ecosystem;

import processing.core.PApplet;
import setup.IProcessingApp;
import tools.SubPlot;

import static ecosystem.WorldConstants.*;

public class TestTerrainApp implements IProcessingApp {

	private float[] viewport = { 0f, 0f, 1f, 1f };
	private SubPlot plt;
	private Terrain terrain;

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(WINDOW, viewport, p.width, p.height);
		terrain = new Terrain(p, plt);
		terrain.setStateColors(getColors(p));
		terrain.initRandom();
		for (int i = 0; i < 5; i++)
			terrain.majorityRule();
	}

	@Override
	public void draw(PApplet p, float dt) {
		terrain.regenerate();
		terrain.display(p);
	}

	@Override
	public void mousePressed(PApplet p) {

	}

	private int[] getColors(PApplet p) {
		int[] colors = new int[NSTATES];
		for (int i = 0; i < NSTATES; i++)
			colors[i] = p.color(TERRAIN_COLORS[i][0], TERRAIN_COLORS[i][1], TERRAIN_COLORS[i][2]);
		return colors;
	}

	@Override
	public void mouseReleased(PApplet p) {

	}

	@Override
	public void mouseDragged(PApplet p) {

	}

	@Override
	public void keyPressed(PApplet p) {

	}
}