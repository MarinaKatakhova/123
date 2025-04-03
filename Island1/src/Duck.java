import java.util.List;
import java.util.stream.Collectors;

public class Duck extends Herbivore {
    public Duck() {
        super(AnimalType.DUCK);
    }

    @Override
    public void eat() {
        super.eat();

        if (satiety < foodNeeded * 0.7) {
            List<Animal> caterpillars = location.getAnimals().stream()
                    .filter(a -> a.getType() == AnimalType.CATERPILLAR)
                    .collect(Collectors.toList());

            if (!caterpillars.isEmpty()) {
                Animal caterpillar = caterpillars.get(0);
                satiety += caterpillar.getWeight();
                caterpillar.die();
            }
        }
    }
}