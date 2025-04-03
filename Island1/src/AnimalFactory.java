public class AnimalFactory {
    public static Animal createAnimal(AnimalType type) {
        if (type == null) return null;

        try {
            switch (type) {
                case WOLF: return new Wolf();
                case BOA: return new Boa();
                case FOX: return new Fox();
                case BEAR: return new Bear();
                case EAGLE: return new Eagle();
                case HORSE: return new Horse();
                case DEER: return new Deer();
                case RABBIT: return new Rabbit();
                case MOUSE: return new Mouse();
                case GOAT: return new Goat();
                case SHEEP: return new Sheep();
                case BOAR: return new Boar();
                case BUFFALO: return new Buffalo();
                case DUCK: return new Duck();
                case CATERPILLAR: return new Caterpillar();
                default: throw new IllegalArgumentException("Unknown animal type: " + type);
            }
        } catch (Exception e) {
            System.err.println("Error creating animal of type " + type + ": " + e.getMessage());
            return null;
        }
    }
}