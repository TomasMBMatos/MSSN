package ecosystem;

import processing.core.PApplet;
import setup.IProcessingApp;
import tools.SubPlot;

import static ecosystem.WorldConstants.*;

public class FpTerrainApp implements IProcessingApp {

    private float[] viewport = {0f,0f,1f,1f};
    private SubPlot plt;
    private Terrain terrain;
    private Population population;

    @Override
    public void setup(PApplet p) {
        System.out.println(System.currentTimeMillis());
        plt = new SubPlot(WINDOW, viewport, p.width, p.height);
        terrain = new Terrain(p, plt);
        terrain.setStateColors(getColors(p));
        terrain.initRandomCustom(PATCH_TYPE_PROB);
        for(int i = 0; i<2; i++) terrain.majorityRule();
        terrain.createRatCave(p);
        population = new Population(p, plt, terrain);
        population.createPreyPopulation(p, plt, terrain, terrain.getObstacles());
      //  population.createPredatorPopulation(p, plt, terrain, terrain.getObstacles(), population.getPreys());
    }

    @Override
    public void draw(PApplet p, float dt) {
        terrain.regenerate();
        population.update(dt, terrain);
        terrain.display(p);
        population.display(p, plt);
    }

    private int[] getColors(PApplet p) {
        int[] colors = new int[NSTATES];
        for(int i=0;i<NSTATES;i++)
            colors[i] = p.color(TERRAIN_COLORS[i][0],
                    TERRAIN_COLORS[i][1],
                    TERRAIN_COLORS[i][2]);
        return colors;
    }

    @Override
    public void mousePressed(PApplet p) {

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