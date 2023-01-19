package ecosystem;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.List;

import static ecosystem.WorldConstants.*;

public class Snake extends Animal {
    private PApplet p;
    private SubPlot plt;
    private List<Animal> preys;

    protected Snake(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt, List<Animal> preys) {
        super(pos, mass, radius, color, p, plt);
        this.p = p;
        this.plt = plt;
        this.preys = preys;
        energy = INI_PREDATOR_ENERGY;

        //imagem_snake = p.loadImage("...");
        //imagem_snake.resize(.., ..);
    }

    protected Snake(Animal a, boolean mutate, PApplet p, SubPlot plt, List<Animal> preys) {
        super(a, mutate, p, plt);
        this.p = p;
        this.plt = plt;
        this.preys = preys;
        energy = INI_PREDATOR_ENERGY;

        //imagem_snake = p.loadImage("...");
        //imagem_snake.resize(.., ..);
    }

    @Override
    public Animal reproduce(boolean mutate) {
        Animal child = null;
        if(energy > PREDATOR_ENERGY_TO_REPRODUCE) {
            energy -= INI_PREDATOR_ENERGY;
            child = new Snake(this, mutate, p, plt, preys);
            if(mutate) child.mutateBehaviors();
        }
        return child;
    }

    @Override
    public void eat(Terrain terrain) {
        for(int i=0; i<preys.size();i++) {
            if(PVector.dist(pos, preys.get(i).getPos()) < 1f) {
                ((Rat) preys.get(i)).setEaten(true);
                energy += ENERGY_FROM_RAT;
            }
        }
    }

    public int getNearPrey(List<Animal> preys) {
        int indice = 0;
        float dist = PVector.dist(pos, preys.get(0).getPos());

        for(int i=0; i<preys.size(); i++) {
            if(PVector.dist(pos, preys.get(i).getPos()) < dist) {
                indice = i;
                dist = PVector.dist(pos, preys.get(i).getPos());
            }
        }
        return indice;
    }

    @Override
    public void display(PApplet p, SubPlot plt) {
        p.pushMatrix();
        float[] pp = plt.getPixelCoord(pos.x,pos.y);
        float[] vv = plt.getVectorCoord(vel.x, vel.y);
        PVector vAux = new PVector(vv[0],vv[1]);
        p.translate(pp[0], pp[1]);
        //System.out.println(-vaux.heading());
        /**
        if(Math.abs(-vAux.heading()) >= 1.5f) {
            p.rotate(-vAux.heading() + p.PI);
            p.image(imagem_papa_1, -(imagem_papa_1.width/1.5f),-(imagem_papa_1.height/1.5f));
        }
        else {
            p.rotate(-vAux.heading());
            p.image(imagem_papa_2, -(imagem_papa_1.width/1.5f),-(imagem_papa_1.height/1.5f));
        }
         */
        p.popMatrix();
    }
}