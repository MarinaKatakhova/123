public class Bear extends Predator {
    public Bear() {
        super(AnimalType.BEAR);
        preyChances.put(AnimalType.BOA, 80);
        preyChances.put(AnimalType.HORSE, 40);
        preyChances.put(AnimalType.DEER, 80);
        preyChances.put(AnimalType.RABBIT, 90);
        preyChances.put(AnimalType.MOUSE, 90);
        preyChances.put(AnimalType.GOAT, 70);
        preyChances.put(AnimalType.SHEEP, 70);
        preyChances.put(AnimalType.BOAR, 50);
        preyChances.put(AnimalType.BUFFALO, 20);
        preyChances.put(AnimalType.DUCK, 10);
    }
}