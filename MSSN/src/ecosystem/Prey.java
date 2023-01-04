package ecosystem;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import static ecosystem.WorldConstants.*;

public class Prey extends Animal {
    private PApplet p;
    private SubPlot plt;
    public Prey(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt) {
        super(pos, mass, radius, color, p, plt);
        this.p = p;
        this.plt = plt;
        energy = INI_PREY_ENERGY;
    }

    public Prey(Prey prey, boolean mutate, PApplet p, SubPlot plt) {
        super(prey,mutate, p, plt);
        this.p = p;
        this.plt = plt;
        energy = INI_PREY_ENERGY;
    }
    @Override
    public Animal reproduce(boolean mutate) {
        Animal child = null;
        if(energy > PREY_ENERGY_TO_REPRODUCE) {
            energy -= INI_PREY_ENERGY;
            child = new Prey(this, mutate, p, plt);
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
}
