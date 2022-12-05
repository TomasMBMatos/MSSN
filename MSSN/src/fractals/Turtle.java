package fractals;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Turtle {
    private float length;
    private float angle;

    public Turtle(float length, float angle) {
        this.length = length;
        this.angle = angle;
    }

    public void setPose(PVector position, float orietation, PApplet p, SubPlot plt) {
        float[] pp = plt.getPixelCoord(position.x, position.y);
        p.translate(pp[0], pp[1]);
        p.rotate(-orietation);
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getLength() {
        return length;
    }

    public void scaling(float s) {
        length *= s;
    }

    public void render(LSystem lsys, PApplet p, SubPlot plt) {
        p.stroke(0);
        float[] lenPix = plt.getVectorCoord(length, length);
        for(int i=0;i<lsys.getSequence().length();i++) {
            char c = lsys.getSequence().charAt(i);
            if(c == 'F' || c == 'G') {
                p.line(0, 0, lenPix[0], 0);
                p.translate(lenPix[0], 0);
            }
            else if (c == 'f') p.translate(lenPix[0], 0);
            else if (c == '+') p.rotate(angle);
            else if (c == '-') p.rotate(-angle);
            else if (c == '[') p.pushMatrix();
            else if (c == ']') p.popMatrix();
        }
    }
}
