package maj;

import ca.Cell;
import ca.CellularAutomata;
import processing.core.PApplet;
import processing.core.PImage;
import processing.sound.SoundFile;
import setup.IProcessingApp;

public class Majority implements IProcessingApp {
    private int rows = 150;
    private int cols = 200;
    private final int states = 3;
    private final int radius= 1;
    private boolean Paused = true;
    private String survive = "";
    private String born = "";
    SoundFile moving;
    
    

    private CellularAutomata ca;

    public void setColsRows(int cols, int rows) {
    	this.cols = cols;
    	this.rows = rows;
    }
    @Override
    public void setup(PApplet p) {
       // SoundFile music=new SoundFile(p,"Undertale.mp3");
       // music.play();
        ca = new CellularAutomata(p,cols, rows, states, radius,survive,born);
        ca.initRandomMajority();
        ca.display(p);
      
    }

    @Override
    public void draw(PApplet p, float dt) {
        if(!Paused) {
                ca.majority();
                ca.display(p);
            }
    }

    @Override
    public void keyPressed(PApplet p) {
        if(p.key  == 'p' )Paused = !Paused;
        if(p.key  == 'n' && Paused ) {
           ca.majority();
            ca.display(p);
        }

    }

    @Override
    public void mousePressed(PApplet p) {
        if(Paused) {
            Cell cell=ca.pixel2cell(p.mouseX, p.mouseY);
            
            if(cell.getState() == 0) {
            	cell.setState((int)p.random(1,states));
            } else cell.setState(0);
 
            ca.display(p);
        }
        else {
            ca.majority();
            ca.display(p);
        }
    }
	@Override
	public void mouseReleased(PApplet parent) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(PApplet parent) {
		// TODO Auto-generated method stub
		
	}
}