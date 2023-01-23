package ecosystem;

import processing.core.PApplet;
import setup.IProcessingApp;
import setup.IProcessingApp2;
import tools.SubPlot;

import static ecosystem.WorldConstants.*;

public class FpTerrainApp implements IProcessingApp {

    private float[] viewport = {0f,0f,1f,1f};
    private SubPlot plt;
    private Terrain terrain;
    private boolean input = true;
    private Population population;

    @Override
    public void setup(PApplet p) {
        System.out.println(System.currentTimeMillis());
        plt = new SubPlot(WINDOW, viewport, p.width, p.height);
        terrain = new Terrain(p, plt);
        terrain.setStateColors(getColors(p));
        terrain.initRandomCustom(PATCH_TYPE_PROB);
        for(int i = 0; i<2; i++) terrain.majorityRule();
        if(input) {
            terrain.createRatCave(p);
            input = !input;
        }
        population = new Population(p, plt, terrain);
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
