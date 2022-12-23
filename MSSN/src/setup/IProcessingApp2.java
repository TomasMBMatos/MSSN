package setup;

import processing.core.PApplet;

public interface IProcessingApp2 {
    void setup(PApplet p);
    void draw(PApplet p, float dt);
    void mousePressed(PApplet p);
    void mouseReleased(PApplet p);
    void mouseDragged(PApplet p);
    void keyPressed(PApplet p);
}
