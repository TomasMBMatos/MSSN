package physics;

import processing.core.PImage;

public class Wrapper {
	public CelestialBody planet;
	public PImage img;
	
	public Wrapper(CelestialBody planet, PImage img) {
		this.planet = planet;
		this.img = img;
	}

}
