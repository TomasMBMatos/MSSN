package ecosystem;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import static ecosystem.WorldConstants.*;

public class Rat extends Animal {

    private PApplet p;
    private SubPlot plt;
    private boolean eaten = false;

    protected Rat(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt) {
        super(pos, mass, radius, color, p, plt);
        energy = INI_PREY_ENERGY;
        // image_rat = p.loadImage("...");
        // image_rat.resize(.., ..);
    }

    public Rat(Rat prey, boolean mutate, PApplet p, SubPlot plt) {
        super(prey, mutate, p, plt);
        energy = INI_PREY_ENERGY;
        // image_rat = p.loadImage("..."):
        // image_rat.resize(.., ..);
    }

    @Override
    public Animal reproduce(boolean mutate) {
        Animal child = null;
        if(energy > PREY_ENERGY_TO_REPRODUCE) {
            energy -= INI_PREY_ENERGY;
            child = new Rat(this, mutate, p, plt);
            if(mutate) child.mutateBehaviors();
        }
        return child;
    }

    @Override
    public void eat(Terrain terrain) {
        Patch patch = (Patch) terrain.world2Cell(pos.x, pos.y);
        if(patch.getState() == PatchType.FOOD.ordinal()) {
            energy += ENERGY_FROM_PLANT;
            patch.setFertile();
        }
    }

    @Override
    public boolean die() {
        return eaten || energy < 0;
    }
    /**
    @Override
    public void display(PApplet p, SubPlot plt) {
        p.pushMatrix();
        float[] pp = plt.getPixelCoord(pos.x, pos.y);
        float[] vv = plt.getVectorCoord(vel.x, vel.y);
        PVector vAux = new PVector(vv[0], vv[1]);
        p.translate(pp[0], pp[1]);
        p.rotate(-vAux.heading());
        // p.image(image_rat, 0, 0);
        p.popMatrix();
    }
    */

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }
}