import java.util.concurrent.ThreadLocalRandom;

public class Caterpillar extends Herbivore {
    public Caterpillar() {
        super(AnimalType.CATERPILLAR);
    }

    @Override
    public void move() {
    }

    @Override
    public void reproduce() {
        if (satiety >= foodNeeded * 0.3) {
            int offspringCount = ThreadLocalRandom.current().nextInt(10) + 5;
            for (int i = 0; i < offspringCount; i++) {
                try {
                    location.addAnimal(new Caterpillar());
                } catch (Exception e) {
                    System.err.println("Error creating caterpillar: " + e.getMessage());
                }
            }
        }
    }
}