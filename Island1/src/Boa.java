public class Boa extends Predator {
    public Boa() {
        super(AnimalType.BOA);
        preyChances.put(AnimalType.FOX, 15);
        preyChances.put(AnimalType.RABBIT, 20);
        preyChances.put(AnimalType.MOUSE, 40);
        preyChances.put(AnimalType.DUCK, 10);
    }
}