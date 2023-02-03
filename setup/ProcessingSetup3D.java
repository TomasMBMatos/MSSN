package setup;


import java.util.Random;
import java.util.Scanner;

import aa.BoidApp;
import aa.ReynoldsTestApp;
import aa.SpaceWarApp;
import ca.TestCA;
import dla.DLA;
import fractals.ChaosGame1App;
import fractals.ChaosGame2App;
import fractals.ForestApp;
import fractals.LSystem2App;
import fractals.MandelbrotApp;
import fractals.MandelbrotSet;
import gof.GoF;
import maj.Majority;
import physics.ControlGUIApp;
import physics.ParticleSystemApp;
import physics.SolarSystemApp;
import processing.core.PApplet;
import processing.core.PImage;


public class ProcessingSetup3D extends PApplet{

		public static IProcessingApp app;
		private int lastUpdate;
		PImage img;
		

		
	public void settings(){
		size(1000,667,P3D);
	
	}
	
	@Override
	public void setup(){
		app.setup(this);
		lastUpdate=millis();
	
	}
	public void keyPressed() {
		app.keyPressed(this);
		
	}
	public void mousePressed() {
		app.mousePressed(this);
	}
	
	public void mouseReleased() {
		app.mouseReleased(this);
	}
	
	@Override
	public void draw(){
	
		int now = millis();
		float dt = (now - lastUpdate)/1000f;
		lastUpdate = now;
		app.draw(this,dt);
		
	}
	
	public static void main(String[] args) {
		
		
		app = new ChaosGame2App();
	
		PApplet.main(ProcessingSetup3D.class);
	}

}