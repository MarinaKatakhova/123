public class Eagle extends Predator {
    public Eagle() {
        super(AnimalType.EAGLE);
        preyChances.put(AnimalType.FOX, 10);
        preyChances.put(AnimalType.RABBIT, 90);
        preyChances.put(AnimalType.MOUSE, 90);
        preyChances.put(AnimalType.DUCK, 80);
    }
}