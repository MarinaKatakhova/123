import java.util.Random;

public class Island {
    private final Location[][] locations;
    private final int width;
    private final int height;

    public Island(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Island dimensions must be positive");
        }
        this.width = width;
        this.height = height;
        this.locations = new Location[width][height];
        initializeLocations();
        populateIsland();
    }

    private void initializeLocations() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                locations[x][y] = new Location(x, y, this);
            }
        }
    }

    private void populateIsland() {
        Random random = new Random();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                try {
                    for (int i = 0; i < SimulationSettings.INITIAL_PLANTS_PER_LOCATION; i++) {
                        locations[x][y].addPlant(new Plant());
                    }

                    if (random.nextDouble() < SimulationSettings.INITIAL_ANIMAL_SPAWN_CHANCE) {
                        spawnRandomAnimals(locations[x][y]);
                    }
                } catch (Exception e) {
                    System.err.println("Error populating location [" + x + "," + y + "]: " + e.getMessage());
                }
            }
        }
    }

    private void spawnRandomAnimals(Location location) {
        if (location == null) return;

        Random random = new Random();
        AnimalType[] possibleAnimals = AnimalType.values();
        int animalsToSpawn = random.nextInt(5) + 1;

        for (int i = 0; i < animalsToSpawn; i++) {
            try {
                AnimalType type = possibleAnimals[random.nextInt(possibleAnimals.length)];
                Animal animal = AnimalFactory.createAnimal(type);
                if (animal != null) {
                    location.addAnimal(animal);
                }
            } catch (Exception e) {
                System.err.println("Error creating animal: " + e.getMessage());
            }
        }
    }

    public Location getLocation(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return locations[x][y];
        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}