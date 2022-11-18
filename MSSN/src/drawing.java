import processing.core.PApplet;

public class drawing  extends PApplet {
	 PApplet papplet;

	   public void MyShape(PApplet papplet){
	      this.papplet = papplet;
	   }

	int radius;
	int x;
	int y;
	boolean rpress=false; 
	public void update(int nx,int ny,int nradius) {
		x=nx;
		y=ny;
		radius=nradius;
	}
	@Override
	public void draw() {
		papplet.clear();
		papplet.fill(350,350,0);
		papplet.ellipse(x,y,radius*2,radius*2);
		papplet.fill(0);
		papplet.ellipse(x+radius/3,y-radius/3,radius/3,radius/3);
		papplet.ellipse(x-radius/3,y-radius/3,radius/3,radius/3);
		papplet.bezier(x-(2*radius/3),y+radius/4,x-radius/4,y+(3*radius/4),x+radius/4,y+(3*radius/4),x+(2*radius/3),y+radius/4);
		papplet.fill(200,0,0);
		papplet.ellipse(x,y+(3*radius/5),2*radius/5,radius/2);
		papplet.fill(0);
		papplet.line(x,y+(4*radius/5),x,y+(2*radius/5));
	}
	

}
