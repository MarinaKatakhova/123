public class Fox extends Predator {
    public Fox() {
        super(AnimalType.FOX);
        preyChances.put(AnimalType.RABBIT, 70);
        preyChances.put(AnimalType.MOUSE, 90);
        preyChances.put(AnimalType.DUCK, 60);
        preyChances.put(AnimalType.CATERPILLAR, 40);
    }
}