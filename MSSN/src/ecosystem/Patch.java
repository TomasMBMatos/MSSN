package ecosystem;

import ca.MajorityCell;

public class Patch extends MajorityCell {
    private long eatenTime;
    private int timeToGrow;
    public Patch(Terrain terrain, int col, int row, int timeToGrow) {
        super(terrain, col, row);
        this.timeToGrow = timeToGrow;
        eatenTime = System.currentTimeMillis();
    }

    public void setFertile() {
        state = WorldConstants.PatchType.FERTILE.ordinal();
        eatenTime = System.currentTimeMillis();
    }

    public void regenerate() {
        if(state == WorldConstants.PatchType.FERTILE.ordinal()
            && System.currentTimeMillis() > (eatenTime + timeToGrow))
            state = WorldConstants.PatchType.FOOD.ordinal();
    }
}
