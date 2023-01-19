package fractals;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

public class LSystem2App implements IProcessingApp {

	private LSystem lsys;
	private double[] window = { -15, 15,0, 15 };
	private float[] viewport = { 0.1f, 0.1f, 0.8f, 0.8f };
	private PVector startingPos = new PVector();
	private SubPlot plt;
	private Turtle turtle;
	private ArrayList<Wrapper> ruleList;
	private int index;

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);
		ruleList = new ArrayList<Wrapper>();
		
		Rule[] rulesA = new Rule[1];
		Rule[] rulesB = new Rule[1];
		Rule[] rulesC = new Rule[1];
		Rule[] rulesD = new Rule[2];
		Rule[] rulesE = new Rule[2];
		Rule[] rulesF = new Rule[2];
		Rule[] rulesT = new Rule[2];
		Rule[] rulesS = new Rule[1];
		Rule[] rules2 = new Rule[2];
		
		rulesA[0] = new Rule('F', "F[+F]F[-F]F"); // A  Axiom -> F / Ang -> 25.7	 Need to change size of Rule to 1 or null exception fires
		
		rulesB[0] = new Rule('F', "F[+F]F[-F][F]"); // B  Axiom -> F / Ang -> 20 	Need to change size of Rule to 1 or null exception fires
	
		rulesC[0] = new Rule('F', "FF-[-F+F+F]+[+F-F-F]");  // C  Axiom -> F / Ang -> 22.5 Need to change size of Rule to 1 or null exception fires
		
		rulesD[0] = new Rule('X', "F[+X]F[-X]+X"); // D  Axiom -> X / Ang -> 20
		rulesD[1] = new Rule('F', "FF");
		
		rulesE[0] = new Rule('X', "F[+X][-X]FX"); // E  Axiom -> X / Ang -> 25.7
		rulesE[1] = new Rule('F', "FF");
		
		rulesF[0] = new Rule('X', "F-[[X]+X]+F[+FX]-X"); // F  Axiom -> X / Ang -> 22.5
		rulesF[1] = new Rule('F', "FF");
		
		rulesT[0] = new Rule('F', "F-G+F+G-F"); // Extra Sierpinski Triangle  Axiom -> F / Ang -> 120
		rulesT[1] = new Rule('G', "GG");
		
		rulesS[0] = new Rule('F', "FF+F-F+F+FF"); // Extra Square  Axiom -> F+F+F+F/ Ang -> 90
		
		rules2[0] = new Rule('F', "G[+F]-F"); // 2. Exercise  Axiom -> F / Ang -> 22.5
		rules2[1] = new Rule('G', "GG");
	
		ruleList.add(new Wrapper(rulesA,"F",25.7f));
		ruleList.add(new Wrapper(rulesB,"F",20f));
		ruleList.add(new Wrapper(rulesC,"F",22.5f));
		ruleList.add(new Wrapper(rulesD,"X",20f));
		ruleList.add(new Wrapper(rulesE,"X",25.7f));
		ruleList.add(new Wrapper(rulesF,"X",22.5f));
		ruleList.add(new Wrapper(rulesT,"F",120f));
		ruleList.add(new Wrapper(rulesS,"F+F+F+F",90f));
		ruleList.add(new Wrapper(rules2,"F",22.5f));
		
		
		
		index=0;
		lsys = new LSystem(ruleList.get(0).getAxiom(), ruleList.get(0).getRule());
		turtle = new Turtle(7, PApplet.radians(ruleList.get(0).getAngle()));

	}

	@Override
	public void draw(PApplet p, float dt) {
		float[] bb = plt.getBoundingBox();
		p.rect(bb[0], bb[1], bb[2], bb[3]);
		turtle.setPose(startingPos, PApplet.radians(90), plt, p);
		turtle.render(lsys, plt, p);

	}

	@Override
	public void keyPressed(PApplet p) {
		if(p.key == '+') {
			if(index == ruleList.size()-1) index=0;
			else index ++;
			lsys = new LSystem(ruleList.get(index).getAxiom(), ruleList.get(index).getRule());
			turtle = new Turtle(7, PApplet.radians(ruleList.get(index).getAngle()));
		};
		if(p.key == '-') {
			if(index == 0) index=ruleList.size()-1;
			else index --;
			lsys = new LSystem(ruleList.get(index).getAxiom(), ruleList.get(index).getRule());
			turtle = new Turtle(7, PApplet.radians(ruleList.get(index).getAngle()));
		};
	}

	@Override
	public void mousePressed(PApplet p) {
		System.out.println(lsys.getSequence());
		lsys.nextGeneration();
		turtle.scaling(0.5f);
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
