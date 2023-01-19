package fractals;

public class Wrapper {
	
	private Rule[] rule;
	private String axiom;
	private float angle;
	
	public Wrapper(Rule[] rule,String axiom,float angle) {
		this.rule = rule;
		this.axiom = axiom;
		this.angle = angle;
	}

	public Rule[] getRule() {
		return rule;
	}
	
	public void setRule(Rule[] rule) {
		this.rule = rule;
	}
	
	public String getAxiom() {
		return axiom;
	}
	
	public void setAxiom(String axiom) {
		this.axiom = axiom;
	}
	
	public float getAngle() {
		return angle;
	}
	
	public void setAngle(float angle) {
		this.angle = angle;
	}
}
