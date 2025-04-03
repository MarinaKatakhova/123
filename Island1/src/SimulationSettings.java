public final class SimulationSettings {
    public static final int PLANT_GROWTH_RATE = 15;
    public static final int PLANT_GROWTH_DELAY_MS = 2000;
    public static final int INITIAL_PLANTS_PER_LOCATION = 10;
    public static final int MAX_PLANTS_PER_LOCATION = 200;

    public static final int ANIMAL_CYCLE_DELAY_MS = 1500;
    public static final int MAX_ACTION_ATTEMPTS = 3;
    public static final int ACTION_RETRY_DELAY_MS = 50;
    public static final double INITIAL_ANIMAL_SPAWN_CHANCE = 0.35;

    public static final int STATISTICS_PRINT_INTERVAL_MS = 3000;
    public static final int MAX_SIMULATION_TASKS = 1000;

    private SimulationSettings() {}
}