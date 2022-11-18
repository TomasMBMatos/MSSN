package dla;

import java.util.ArrayList;

import java.util.List;

import dla.Walker.State;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;

public class DLA implements IProcessingApp {

	private List <Walker> walkers;
	private int NUM_WALKERS=150;
	private int STEPS_PER_FRAME=200;
	private boolean pressed = false;
	private boolean portugalpress = false;
	private boolean rectanglepress = false;
	private boolean linepress = false;
	private boolean circlepress = false;
	private boolean menu = false;
	
	@Override
	public void setup(PApplet p) {
		walkers = new ArrayList<Walker>();
		
		Walker w = new Walker(p, new PVector(p.width/2,p.height/2),0);
		
		if(portugalpress) walkers.add(w); //Only Portugal has a center
	
		//### this needs to be fixed ###
		w.num_wanders = 0; // We still need to create a walker to reset the num wanders count 
		w.num_stopped = 0;
		
		
		if(linepress) {
			for (int i=0; i< p.width;i+=10) {
				Walker walker= new Walker(p,new PVector(i,p.height),2);
					walkers.add(walker);
			}
		for (int i=0; i< NUM_WALKERS;i++) {
			Walker walker= new Walker(p,2);
				walkers.add(walker);
		}
		}
		if(portugalpress)
			for (int i=0; i< NUM_WALKERS;i++) {
				Walker walker= new Walker(p,0);
					walkers.add(walker);
			}
		if(rectanglepress) {
			for (int i=0; i< p.width;i+=10) {
				Walker walker= new Walker(p,new PVector(i,p.height),1);
				Walker walker2= new Walker(p,new PVector(i,0),1);
					walkers.add(walker);
					walkers.add(walker2);
			}
			for (int i=0; i< p.height;i+=10) {
				Walker walker= new Walker(p,new PVector(p.width,i),1);
				Walker walker2= new Walker(p,new PVector(0,i),1);
					walkers.add(walker);
					walkers.add(walker2);
			}
			for (int i=0; i< NUM_WALKERS;i++) {
				Walker walker= new Walker(p,1);
					walkers.add(walker);
			}
		}
		if(circlepress) {
			int r=5;
			double PI=3.1415;
			for (int i=0; i < 360 ;i++) {
				double x1 = r * Math.cos(i * PI / 180);
		           double y1 = r * Math.sin(i * PI / 180);
				Walker walker= new Walker(p,new PVector((int)(p.width/2+90*x1) ,(int)(p.height/2+70*y1)),3);
					walkers.add(walker);
			}
			
			for (int i=0; i< NUM_WALKERS;i++) {
				Walker walker= new Walker(p,3);
					walkers.add(walker);
			}
		}
		
		
	}

	@Override
	public void draw(PApplet p, float dt) {
		
		p.background(10);
		
		if(pressed)
		 {
		
		
		for(int i = 0; i<STEPS_PER_FRAME;i++)	
		for (Walker walker:walkers) {
			
			if(walker.getState()== State.WANDER && portugalpress) {
				walker.wanderPortugal(p);
				walker.updateState(p,walkers,0);
				
			}else if(walker.getState()== State.WANDER && rectanglepress) {
				walker.wanderRectangle(p);
				walker.updateState(p,walkers,1);
				
			}else if(walker.getState()== State.WANDER && linepress) {
				walker.wanderLine(p);
				walker.updateState(p,walkers,2);
				
			}else if(walker.getState()== State.WANDER && circlepress) {
				walker.wanderCircle(p);
				walker.updateState(p,walkers,3);
			}
			
			
		}
		if(NUM_WALKERS > Walker.num_wanders) {    //Responsible for infinite walkers spawning after first contact with center
			if(portugalpress) {
				Walker newWalker = new Walker(p,0);
				walkers.add(newWalker);
			}
			if(rectanglepress) {
				Walker newWalker = new Walker(p,1);
				walkers.add(newWalker);
			}
			if(linepress) {
				Walker newWalker = new Walker(p,2);
				walkers.add(newWalker);
			}
			if(circlepress) {
				Walker newWalker = new Walker(p,3);
				walkers.add(newWalker);
			}
			
		}
		
		for (Walker walker:walkers) {
			walker.display(p);
		}
		
		
		System.out.println(" Current Wanderers = " + Walker.num_wanders + " Current Stopped = " + Walker.num_stopped);
		}
		else {
			menu = true;
			//Portugal Button
			p.fill(200,0,0);
			p.rect(150, 150, 220, 150);
			p.textSize(20);
			String portugal = "Portugal Flag";
			p.fill(250);
			p.text(portugal, 190, 210, 150, 320);  // Text wraps within text box
			
			
			//Rectangle Button
			p.fill(0,200,0);
			p.rect(630, 150, 220, 150);
			p.textSize(20);
			String rectangle = "Rectangle attractor";
			p.fill(250);
			p.text(rectangle, 650, 210, 200, 320);  // Text wraps within text box
			
			
			//Line Button
			p.fill(200,200,0);
			p.rect(150, 450, 220, 150);
			p.textSize(20);
			String line = "Line attractor";
			p.fill(250);
			p.text(line, 190, 510, 200, 320);  // Text wraps within text box
			
			
			//Internal Circle Button
			p.fill(0,0,200);
			p.rect(630, 450, 220, 150);
			p.textSize(20);
			String circle = "Internal Circle";
			p.fill(250);
			p.text(circle, 670, 510, 200, 320);  // Text wraps within text box
			
			
			p.textSize(20);
			String b = "Press 'B' to go back to menu";
			p.fill(250);
			p.text(b, 350, 710, 400, 320);  // Text wraps within text box
			
		}
	}

	@Override
	public void keyPressed(PApplet p) {
		if(p.keyPressed && p.key == 'b' || p.key == 'B') {
		pressed = false;
		}
		
	}

	@Override
	public void mousePressed(PApplet p) {
		if(p.mouseX >=150 && p.mouseX <=370 && p.mouseY >=150 && p.mouseY <= 300 && menu) {
			menu=false;
			pressed=true;
			portugalpress = true;
			rectanglepress = false;
			linepress = false;
			circlepress = false;
			setup(p);
		}
		if(p.mouseX >=630 && p.mouseX <=850 && p.mouseY >=150 && p.mouseY <= 300  && menu) {
			menu=false;
			pressed=true;
			portugalpress = false;
			rectanglepress = true;
			linepress = false;
			circlepress = false;
			setup(p);
		}
		if(p.mouseX >=150 && p.mouseX <=370 && p.mouseY >=450 && p.mouseY <= 600  && menu) {
			menu=false;
			pressed=true;
			portugalpress = false;
			rectanglepress = false;
			linepress = true;
			circlepress = false;
			setup(p);
		}
		if(p.mouseX >=630 && p.mouseX <=850 && p.mouseY >=450 && p.mouseY <= 600  && menu) {
			menu=false;
			pressed=true;
			portugalpress = false;
			rectanglepress = false;
			linepress = false;
			circlepress = true;
			setup(p);
		}
	}

}
