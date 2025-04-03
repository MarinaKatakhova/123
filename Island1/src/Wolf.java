public class Wolf extends Predator {
    public Wolf() {
        super(AnimalType.WOLF);
        preyChances.put(AnimalType.HORSE, 10);
        preyChances.put(AnimalType.DEER, 15);
        preyChances.put(AnimalType.RABBIT, 60);
        preyChances.put(AnimalType.MOUSE, 80);
        preyChances.put(AnimalType.GOAT, 60);
        preyChances.put(AnimalType.SHEEP, 70);
        preyChances.put(AnimalType.BOAR, 15);
        preyChances.put(AnimalType.BUFFALO, 10);
        preyChances.put(AnimalType.DUCK, 40);
    }
}