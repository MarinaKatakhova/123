import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.stream.Collectors;

public class IslandSimulation {
    private static final int PLANT_GROWTH_RATE = 10;
    private static final int SIMULATION_TICK_DELAY_MS = 1000;
    private static final int STATISTICS_PRINT_INTERVAL = 5000;
    private static final int INITIAL_PLANTS_PER_LOCATION = 5;
    private static final double INITIAL_ANIMAL_SPAWN_CHANCE = 0.3;

    private final Island island;
    private final ScheduledExecutorService scheduler;
    private final ExecutorService animalExecutor;
    private final Lock statisticsLock = new ReentrantLock();
    private volatile boolean isRunning = false;

    public IslandSimulation(Island island) {
        this.island = island;
        this.scheduler = Executors.newScheduledThreadPool(3);
        this.animalExecutor = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            scheduler.scheduleAtFixedRate(this::growPlants,
                    0, SIMULATION_TICK_DELAY_MS, TimeUnit.MILLISECONDS);
            scheduler.scheduleAtFixedRate(this::animalLifeCycle,
                    0, SIMULATION_TICK_DELAY_MS, TimeUnit.MILLISECONDS);
            scheduler.scheduleAtFixedRate(this::printStatistics,
                    0, STATISTICS_PRINT_INTERVAL, TimeUnit.MILLISECONDS);
        }
    }

    private void growPlants() {
        for (int x = 0; x < island.getWidth(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                try {
                    if (ThreadLocalRandom.current().nextInt(100) < PLANT_GROWTH_RATE) {
                        island.getLocation(x, y).addPlant(new Plant());
                    }
                } catch (Exception e) {
                    System.err.println("Error growing plants at [" + x + "," + y + "]: " + e.getMessage());
                }
            }
        }
    }

    private void animalLifeCycle() {
        List<Future<?>> futures = new ArrayList<>();

        for (int x = 0; x < island.getWidth(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                try {
                    Location location = island.getLocation(x, y);
                    if (location != null) {
                        for (Animal animal : location.getAnimals()) {
                            futures.add(animalExecutor.submit(() -> {
                                try {
                                    animal.liveCycle();
                                } catch (Exception e) {
                                    System.err.println("Error in animal life cycle: " + e.getMessage());
                                }
                            }));
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error processing location [" + x + "," + y + "]: " + e.getMessage());
                }
            }
        }

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Error executing animal task: " + e.getMessage());
            }
        }
    }

    private void printStatistics() {
        statisticsLock.lock();
        try {
            Map<String, Integer> animalCounts = new HashMap<>();
            int totalPlants = 0;
            int totalAnimals = 0;

            for (int x = 0; x < island.getWidth(); x++) {
                for (int y = 0; y < island.getHeight(); y++) {
                    try {
                        Location loc = island.getLocation(x, y);
                        if (loc != null) {
                            totalPlants += loc.getPlants().size();
                            for (Animal animal : loc.getAnimals()) {
                                String name = animal.getType().name();
                                animalCounts.merge(name, 1, Integer::sum);
                                totalAnimals++;
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Error collecting stats at [" + x + "," + y + "]: " + e.getMessage());
                    }
                }
            }

            System.out.println("\n=== Island Statistics ===");
            System.out.printf("Total plants: %d | Total animals: %d%n", totalPlants, totalAnimals);
            System.out.println("Animals by type:");
            animalCounts.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(e -> System.out.printf("%-12s: %d%n", e.getKey(), e.getValue()));

            System.out.println("\nIsland map (sample 10x10):");
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    try {
                        Location loc = island.getLocation(x, y);
                        if (loc != null && !loc.getAnimals().isEmpty()) {
                            System.out.print(loc.getAnimals().get(0).getUnicodeSymbol());
                        } else if (loc != null && !loc.getPlants().isEmpty()) {
                            System.out.print("🌿");
                        } else {
                            System.out.print("·");
                        }
                    } catch (Exception e) {
                        System.out.print("?");
                    }
                }
                System.out.println();
            }
        } finally {
            statisticsLock.unlock();
        }
    }

    public void stop() {
        isRunning = false;
        scheduler.shutdown();
        animalExecutor.shutdown();
        try {
            if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
            if (!animalExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
                animalExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            animalExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("Simulation stopped");
    }
}