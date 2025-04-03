import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Location {
    private final int x;
    private final int y;
    private final Island island;
    private final List<Animal> animals = new CopyOnWriteArrayList<>();
    private final List<Plant> plants = new CopyOnWriteArrayList<>();

    public Location(int x, int y, Island island) {
        this.x = x;
        this.y = y;
        this.island = island;
    }

    public synchronized void addAnimal(Animal animal) {
        if (animal == null) return;

        long sameTypeCount = animals.stream()
                .filter(a -> a != null && a.getType() == animal.getType())
                .count();

        if (sameTypeCount < animal.getMaxPerCell()) {
            animals.add(animal);
            animal.setLocation(this);
        }
    }

    public synchronized void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public synchronized void addPlant(Plant plant) {
        if (plant != null && plants.size() < plant.getMaxPerCell()) {
            plants.add(plant);
            plant.setLocation(this);
        }
    }

    public synchronized void removePlant(Plant plant) {
        plants.remove(plant);
    }

    public List<Location> getAdjacentLocations() {
        List<Location> adjacent = new ArrayList<>();
        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (newX >= 0 && newX < island.getWidth() &&
                    newY >= 0 && newY < island.getHeight()) {
                Location loc = island.getLocation(newX, newY);
                if (loc != null) {
                    adjacent.add(loc);
                }
            }
        }
        return adjacent;
    }

    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

    public List<Plant> getPlants() {
        return Collections.unmodifiableList(plants);
    }
}