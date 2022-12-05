package fractals;

public class LSystem {
    private String sequence;
    private Rule[] ruleset;
    private int gen;

    public LSystem(String axiom, Rule[] ruleset) {
        sequence = axiom;
        this.ruleset = ruleset;
        gen = 0;
    }

    public int getGen() {
        return gen;
    }

    public String getSequence() {
        return sequence;
    }

    public void nextGen() {
        gen++;

        String nextGen = "";
        for(int i=0;i<sequence.length();i++) {
            char c = sequence.charAt(i);
            String replace = "" + c;
            for(int j=0;j< ruleset.length;j++) {
                if(ruleset[j].getSymbol() == c) {
                    replace = ruleset[j].getString();
                    break;
                }
            }
            nextGen += replace;
        }
        this.sequence = nextGen;
    }
}
