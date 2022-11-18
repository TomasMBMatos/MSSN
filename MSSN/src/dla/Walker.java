package dla;

import java.util.List;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PVector;

public class Walker {
	
	public enum State{
		STOPPED,WANDER
	}
	private PVector pos;
	private State state;
	private int color;
	private int radius = 4;
	public static int num_wanders = 0;
	public static int num_stopped = 0;
	Random r = new Random();
	PApplet pr= new PApplet();
	int randx = (int) pr.random(-3,4);
	int randy = (int) pr.random(-3,4);
	private double stickness = 0.08;
	
	
	
	public Walker(PApplet p, int c) {
		//pos = new PVector(p.random(p.width),p.random(p.height));
		
		if(c==0) { //Portugal Spawn
		pos = new PVector(p.width/2,p.height/2);
		PVector r = PVector.random2D();
		r.mult(2*p.width/3 );
		pos.add(r);
		setState(p,State.WANDER,0);
			}
		if(c==1) { //Rectangle Spawn
			pos = new PVector(p.width/2,p.height/2);
			PVector r = PVector.random2D();
			r.mult(p.width/8 );
			pos.add(r);
			setState(p,State.WANDER,0);
			}
		if(c==2) { //Line Spawn
			pos = new PVector(p.random(p.width),0);
			
			setState(p,State.WANDER,0);
			}
		if(c==3) { //Circle Spawn
			pos = new PVector(p.width/2,p.height/2);
			PVector r = PVector.random2D();
			r.mult(20);
			pos.add(r);
			setState(p,State.WANDER,0);
			}
	}
	
	public Walker(PApplet p, PVector pos, int c) {
		this.pos = pos;
		setState(p,State.STOPPED,c);	
	}

	public State getState() {
		return state;
	}
	
	public void setState(PApplet p , State state,int c) {
		this.state = state;
		
		 if( c==0) { // Portugal Stopped Colors
		 if(state == State.STOPPED ) {
			
			if(Math.abs(pos.x - p.width/2) <= 50 && Math.abs(pos.y - p.height/2) <= 50 ) color = p.color(200,200,0); // Make the yellow circle in portugal flag
			else if(pos.x<p.width/2)color = p.color(0,200,0); // Make the left side green just like portugal flag
			else if(pos.x>p.width/2)color = p.color(200,0,0); // Make the right side red just like portugal flag
			else color = p.color(0);
			
			num_stopped++;
		} else {
			color = p.color(255);
			num_wanders++;
		}
		 }

		 if( c==1) { // Rectangle Stopped Colors
			 
		if(state == State.STOPPED) {
			
			float dist = PVector.dist(pos, new PVector(p.width/2,p.height/2));
			if(dist >= 600) color = p.color(0,0,200);
			if(dist >= 500 && dist < 600) color = p.color(0,0,200);
			if(dist >= 400 && dist < 500) color = p.color(100,0,200);
			if(dist >= 300 && dist < 400) color = p.color(200,0,0);
			if(dist >= 200 && dist < 300) color = p.color(200,100,0);
			if( dist < 200) color = p.color(200,200,0);
			
			num_stopped++;
		} else {
			color = p.color(255);
			num_wanders++;
		}
		 }

		 if( c==2) { // Line Stopped Colors
		if(state == State.STOPPED ) {
			
			 if(pos.y < 50) {
				 radius=2;
				 color = p.color(0,0,200);
				 }
			 if(pos.y < 150 && pos.y >= 50) {
				 radius =2;
				 color = p.color(0,100,200);}
			 if(pos.y < 250 && pos.y >= 150) {
				 color = p.color(0,200,200);
			 radius =2;
			 }
			 if(pos.y < 375 && pos.y >= 250) { 
				 color = p.color(0,200,0);
			 radius =3;}
			 if(pos.y < 500 && pos.y >= 375) { 
				 radius =3;
				 color = p.color(100,200,0);}
			 if(pos.y < 625 && pos.y >= 500) { 
				 color = p.color(200,200,0);
			 	radius =3;
			 }
			 if(pos.y < 750 && pos.y >= 625) color = p.color(200,100,0);
			 
			 if(pos.y >= 750) color = p.color(200,0,0);
			 
			num_stopped++;
		} else {
			color = p.color(255);
			num_wanders++;
		}
		 }
		 if( c==3) { // Circle Stopped Colors
		if(state == State.STOPPED) {
			
			float dist = PVector.dist(pos, new PVector(p.width/2,p.height/2));
			if(dist >= 400 && dist < 500) { 
				color = p.color(100,0,200);
				radius = 2;
			}
			if(dist >= 300 && dist < 400) {
				radius = 3;
				color = p.color(200,0,0);
			}
			if(dist >= 200 && dist < 300) {
				radius=4;
				color = p.color(200,100,0);
			}
			if( dist < 200) {
				radius=5;
				color = p.color(200,200,0);
			}
			
			num_stopped++;
		} else {
			color = p.color(255);
			num_wanders++;
		}
		}
		
	}
	
	public void updateState(PApplet p , List<Walker> walkers, int c) {
		
		if(state == State.STOPPED) return;
		
		if(c==0) { // Portugal Stopping 
		for(Walker w:walkers) {
		
			if(w.state == State.STOPPED) {
				float dist = PVector.dist(pos, w.pos);
				if(dist<2*radius && Math.random() <= stickness) {
					setState(p,State.STOPPED,c);
					num_wanders--;
					break;
				}
			}
		}
		}
		if(c==1) { // Rectangle Stopping 
			for(Walker w:walkers) {
				if(w.state == State.STOPPED) {
					float dist = PVector.dist(pos, w.pos);
					if(dist  < 2*radius ) {
						setState(p,State.STOPPED,c);
						num_wanders--;
						break;
					}
				}
			}
			}
		if(c==2) { // Line Stopping 
			for(Walker w:walkers) {
				if(w.state == State.STOPPED) {
					float dist = PVector.dist(pos, w.pos);
					if(dist < 2*radius ) {
						setState(p,State.STOPPED,c);
						num_wanders--;
						break;
					}
				}
			}
			}
		if(c==3) { // Circle Stopping 
			for(Walker w:walkers) {
				if(w.state == State.STOPPED) {
					float dist = PVector.dist(pos, w.pos);
					if(dist < 2*radius ) {
						setState(p,State.STOPPED,c);
						num_wanders--;
						break;
					}
				}
			}
			}
	}
	
	
	public void wanderPortugal (PApplet p) { //Portugal Walker Movement
		PVector step = PVector.random2D();
		pos.add(step);
		pos.lerp(new PVector(p.width/2,p.height/2),0.0002f);
		pos.x=PApplet.constrain(pos.x, 0, p.width);
		pos.y=PApplet.constrain(pos.y, 0, p.height);
	}
	public void wanderRectangle (PApplet p) { //Rectangle Walker Movement
		PVector step = PVector.random2D();
		pos.add(step);
		pos.lerp(new PVector(pos.x * randx ,pos.y * randy),0.0001f);
		pos.x=PApplet.constrain(pos.x, 0, p.width);
		pos.y=PApplet.constrain(pos.y, 0, p.height);
	}
	public void wanderLine (PApplet p) { //Line Walker Movement
		PVector step = PVector.random2D();
		pos.add(step);
		pos.lerp(new PVector(pos.x,p.height),0.0002f);
		pos.x=PApplet.constrain(pos.x, 0, p.width);
		pos.y=PApplet.constrain(pos.y, 0, p.height);
	}
	public void wanderCircle (PApplet p) { //Circle Walker Movement
		PVector step = PVector.random2D();
		pos.add(step);
		pos.lerp(new PVector(pos.x * randx ,pos.y * randy),0.0001f);
		pos.x=PApplet.constrain(pos.x, 0, p.width);
		pos.y=PApplet.constrain(pos.y, 0, p.height);
	}
	
	public void display(PApplet p) {
		p.fill(color);
		p.circle(pos.x, pos.y, 2*radius);
	}
}
