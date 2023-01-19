package fractals;

import processing.core.PApplet;
import fractals.Complex;
import tools.SubPlot;

public class MandelbrotSet {
    private int nIter;
    private int x0, y0;
    private int dimx, dimy;

    public MandelbrotSet(int nIter, SubPlot plt) {
        this.nIter = nIter;
        float[] bb = plt.getBoundingBox();
        x0 = (int) bb[0];
        y0 = (int) bb[1];
        dimx = (int) bb[2];
        dimy = (int) bb[3];
    }

    public void display(PApplet p, SubPlot plt) {
        int tt = p.millis();
        p.loadPixels();
        for(int xx=x0;xx<x0+dimx;xx++) {
            for(int yy=y0;yy<y0+dimy;yy++) {
                double[] cc = plt.getWorldCoord(xx, yy);
                Complex c = new Complex(cc);
                Complex x = new Complex();
                Complex y = new Complex(0, 0.8);
                int i;
                for(i=0;i<nIter;i++) {
                    x.mult().add(c);
                    if(x.norm() > 16) break;
                }
                p.colorMode(PApplet.RGB,255);
                p.pixels[yy*p.width+xx] = (i == nIter) ? p.color(0) : p.color((i % 16)*16);
                p.colorMode(PApplet.HSB,255);
                if((i % 16)*16 > 130) {
                    float hue = PApplet.map((i % 16)*15, 130, 255, 0, 255);
                    p.pixels[yy*p.width+xx] = (i == nIter) ? p.color(0) : p.color(hue,255,255);
                }
                // Slow Method
                //int color = (i == nIter) ? p.color(0) : p.color((i % 16)*16);
                //p.stroke(color);
                //p.point(xx, yy);
            }
        }
        p.updatePixels();
        System.out.println(p.millis()-tt);
    }
}