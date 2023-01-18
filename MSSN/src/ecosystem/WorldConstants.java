package ecosystem;

public class WorldConstants {

    // World
    public final static double[] WINDOW = {-10,10,-10,10};

    // Terrain
    public final static int NROWS = 45;
    public final static int NCOLS = 60;
    public static enum PatchType {
        EMPTY, OBSTACLE, FERTILE, FOOD, BASE
    }
    public final static double[] PATCH_TYPE_PROB = {0f, .3f, 0f, .7f};
    public final static int NSTATES = PatchType.values().length;
    public static int[][] TERRAIN_COLORS = {
            {150, 110, 100}, {160, 30, 70}, {200, 200, 60}, {40, 200, 20}
    };
    public final static float[] REGENERATION_TIME = {10.f, 20.f}; // seconds

    // Prey Population
    public static float PREY_SIZE = .2f;
    public static float PREY_MASS = 1f;
    public final static int INI_PREY_POPULATION = 100;
    public final static float INI_PREY_ENERGY = 10f;
    public final static float ENERGY_FROM_PLANT = 4f;
    public final static float PREY_ENERGY_TO_REPRODUCE = 25f;
    public static int[] PREY_COLOR = {80, 100, 220};

    // Predator Population
    public final static float PREDATOR_SIZE = 0.6f;
    public final static float PREDATOR_MASS = 1f;
    public final static int INI_PREDATOR_POPULATION = 5;
    public final static float INI_PREDATOR_ENERGY = 25f;
    public final static float ENERGY_FROM_RAT = 3f;
    public final static float PREDATOR_ENERGY_TO_REPRODUCE = 72.5f;
    public static int[] PREDATOR_COLOR = {255,0,0};
    //public final static long PREDATOR_REBORN_TIME = 2500;
}
