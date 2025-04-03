public class Main {
    public static void main(String[] args) {
        try {
            Island island = new Island(100, 20);
            IslandSimulation simulation = new IslandSimulation(island);
            simulation.start();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                simulation.stop();
            }));

            while (true) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.err.println("Failed to start simulation: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}