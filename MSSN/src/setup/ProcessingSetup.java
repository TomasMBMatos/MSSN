package setup;


import java.util.Random;
import java.util.Scanner;

import aa.BoidApp;
import aa.FlockLeaderApp;
import aa.FlockTestApp;
import aa.ReynoldsTestApp;
import ca.TestCA;
import ca.TestMajorityCA;
import dla.DLA;
import ecosystem.TestEcosystemApp;
import ecosystem.TestObstacles;
import ecosystem.TestTerrainApp;
import fractals.ForestApp;
import fractals.LSystemApp;
import fractals.MandelbrotApp;
import gof.GoF;
import maj.Majority;
import physics.ControlGUIApp;
import physics.ParticleSystemApp;
import physics.SolarSystemApp;
import processing.core.PApplet;
import processing.core.PImage;


public class ProcessingSetup extends PApplet{

		public static IProcessingApp2 app;
		private int lastUpdate;
		PImage img;
		

		
	public void settings(){
		size(1000,667);
	
	}
	
	@Override
	public void setup(){
		app.setup(this);
		lastUpdate=millis();
	}
	public void mousePressed() {
		app.mousePressed(this);
	}
	public void mouseReleased() {
		app.mouseReleased(this);
	}
	public void mouseDragged() {
		app.mouseDragged(this);
	}
	public void keyPressed() {
		app.keyPressed(this);
	}

	@Override
	public void draw(){
	
		int now = millis();
		float dt = (now - lastUpdate)/1000f;
		lastUpdate = now;
		app.draw(this,dt);
		
	}
	
	public static void main(String[] args) {
		app = new TestEcosystemApp();

		/**
		 // ************** 1 Work Code Here ***********
		Scanner sc= new Scanner(System.in);    //System.in is a standard input stream  
		
		System.out.println("Select App:");
		System.out.println("1 -> CA     2-> DLA     3-> GoF     4-> Majority");
		
		
		while (true) {
			int input = sc.nextInt();   //reads string
		
			if (input == 1) {
				System.out.println("Selected CA");
				app= new TestCA();
				break;
			}else if (input == 2) {
				System.out.println("Selected DLA");
				app= new DLA();
				break;
			} if (input == 3) {
				System.out.println("Selected GoF");
				GoF holder = new GoF();
				System.out.println("");
				System.out.println("Please choose the rule you would like to see: x/y");
				System.out.println("Select the survive rule : x");
				int inputsurvive = sc.nextInt(); 
				holder.setSurvive(String.valueOf(inputsurvive));
				System.out.println("Select the born rule : y");
				int inputborn = sc.nextInt(); 
				holder.setBorn(String.valueOf(inputborn));
				System.out.println("Thank you!! Launching Game Of Life... enjoy :)");
				app= holder;
				break;
				} else 
				if(input == 4){
					System.out.println("Selected Majority");
					app= new Majority();
					break;
				}else {
				System.out.println("Please select one of the above");
			}
			
		}
		*/
		PApplet.main(ProcessingSetup.class);
	}

}