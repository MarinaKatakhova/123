import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class Predator extends Animal {
    public Predator(AnimalType type) {
        super(type);
    }

    @Override
    public void eat() {
        List<Animal> preyList = location.getAnimals().stream()
                .filter(Objects::nonNull)
                .filter(a -> preyChances.containsKey(a.getType()))
                .collect(Collectors.toList());

        if (!preyList.isEmpty()) {
            Animal prey = preyList.get(ThreadLocalRandom.current().nextInt(preyList.size()));
            int chance = preyChances.get(prey.getType());
            if (ThreadLocalRandom.current().nextInt(100) < chance) {
                satiety = Math.min(satiety + prey.getWeight(), foodNeeded);
                prey.die();
            }
        }
    }

    @Override
    public void reproduce() {
        if (satiety < foodNeeded * 0.7) return;

        long potentialMates = location.getAnimals().stream()
                .filter(Objects::nonNull)
                .filter(a -> a.getType() == this.type)
                .count();

        if (potentialMates >= 2) {
            int offspringCount = ThreadLocalRandom.current().nextInt(3) + 1;
            for (int i = 0; i < offspringCount; i++) {
                try {
                    Animal offspring = AnimalFactory.createAnimal(this.type);
                    location.addAnimal(offspring);
                } catch (Exception e) {
                    System.err.println("Error creating offspring: " + e.getMessage());
                }
            }
        }
    }
}