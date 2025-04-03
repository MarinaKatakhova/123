import java.util.List;
import java.util.concurrent.locks.Lock;

public abstract class Herbivore extends Animal {
    public Herbivore(AnimalType type) {
        super(type);
    }

    @Override
    public void eat() {
        if (location == null) return;

        lock.lock();
        try {
            List<Plant> plants = location.getPlants();
            if (!plants.isEmpty()) {
                Plant plant = plants.get(0);
                satiety = Math.min(satiety + plant.getWeight(), foodNeeded);
                plant.die();
            }
        } finally {
            lock.unlock();
        }
    }
}