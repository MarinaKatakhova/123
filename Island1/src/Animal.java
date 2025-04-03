import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.*;
import java.util.stream.Collectors;

public abstract class Animal extends LivingEntity {
    protected AnimalType type;
    protected int maxSpeed;
    protected String unicodeSymbol;
    protected Map<AnimalType, Integer> preyChances = new HashMap<>();
    protected final Lock lock = new ReentrantLock();

    public Animal(AnimalType type) {
        this.type = type;
        this.weight = type.getWeight();
        this.maxPerCell = type.getMaxPerCell();
        this.maxSpeed = type.getMaxSpeed();
        this.foodNeeded = type.getFoodNeeded();
        this.unicodeSymbol = type.getUnicodeSymbol();
        this.satiety = foodNeeded * 0.5;
    }

    @Override
    public void liveCycle() {
        if (location == null) return;

        lock.lock();
        try {
            move();
            eat();
            reproduce();
            satiety = Math.max(satiety - (foodNeeded * 0.1), 0);
            if (satiety <= 0) die();
        } catch (Exception e) {
            System.err.println("Life cycle error in " + getClass().getSimpleName() + ": " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    protected void move() {
        if (location == null || maxSpeed <= 0) return;

        try {
            int steps = ThreadLocalRandom.current().nextInt(maxSpeed) + 1;
            for (int i = 0; i < steps; i++) {
                List<Location> adjacent = safeGetAdjacentLocations();
                if (adjacent.isEmpty()) continue;

                Location newLocation = adjacent.get(ThreadLocalRandom.current().nextInt(adjacent.size()));
                if (newLocation != null && canMoveTo(newLocation)) {
                    safeMoveTo(newLocation);
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Move error in " + getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    protected void eat() {
        if (location == null) return;

        lock.lock();
        try {
            if (isPredator()) {
                eatAsPredator();
            } else {
                eatAsHerbivore();
            }
        } finally {
            lock.unlock();
        }
    }

    protected void reproduce() {
        if (location == null || satiety < foodNeeded * 0.5) return;

        lock.lock();
        try {
            long matesCount = location.getAnimals().stream()
                    .filter(Objects::nonNull)
                    .filter(a -> a.getType() == this.type)
                    .count();

            if (matesCount >= 2) {
                int offspringCount = ThreadLocalRandom.current().nextInt(3) + 1;
                for (int i = 0; i < offspringCount; i++) {
                    safeCreateOffspring();
                }
            }
        } catch (Exception e) {
            System.err.println("Reproduction error in " + getClass().getSimpleName() + ": " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    private List<Location> safeGetAdjacentLocations() {
        try {
            return location != null ? location.getAdjacentLocations() : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private void safeMoveTo(Location newLocation) {
        try {
            if (location != null) location.removeAnimal(this);
            newLocation.addAnimal(this);
            location = newLocation;
        } catch (Exception e) {
            System.err.println("Move failed: " + e.getMessage());
            if (location != null) location.addAnimal(this);
        }
    }

    private void eatAsPredator() {
        try {
            List<Animal> preyList = location.getAnimals().stream()
                    .filter(Objects::nonNull)
                    .filter(a -> preyChances.containsKey(a.getType()))
                    .collect(Collectors.toList());

            if (!preyList.isEmpty()) {
                Animal prey = preyList.get(ThreadLocalRandom.current().nextInt(preyList.size()));
                int chance = preyChances.getOrDefault(prey.getType(), 0);
                if (ThreadLocalRandom.current().nextInt(100) < chance) {
                    satiety = Math.min(satiety + prey.getWeight(), foodNeeded);
                    prey.die();
                }
            }
        } catch (Exception e) {
            System.err.println("Predator eating error: " + e.getMessage());
        }
    }

    private void eatAsHerbivore() {
        try {
            List<Plant> plants = location.getPlants();
            if (!plants.isEmpty()) {
                Plant plant = plants.get(0);
                satiety = Math.min(satiety + plant.getWeight(), foodNeeded);
                plant.die();
            }
        } catch (Exception e) {
            System.err.println("Herbivore eating error: " + e.getMessage());
        }
    }

    private void safeCreateOffspring() {
        try {
            Animal offspring = AnimalFactory.createAnimal(this.type);
            if (offspring != null) {
                location.addAnimal(offspring);
            }
        } catch (Exception e) {
            System.err.println("Failed to create offspring: " + e.getMessage());
        }
    }

    protected boolean isPredator() {
        return !preyChances.isEmpty();
    }

    protected boolean canMoveTo(Location location) {
        return location != null;
    }

    @Override
    public void die() {
        if (location != null) {
            location.removeAnimal(this);
        }
    }

    public String getUnicodeSymbol() {
        return unicodeSymbol;
    }

    public AnimalType getType() {
        return type;
    }
}