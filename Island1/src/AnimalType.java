public enum AnimalType {
    WOLF("🐺", 50, 30, 3, 8),
    BOA("🐍", 15, 30, 1, 3),
    FOX("🦊", 8, 30, 2, 2),
    BEAR("🐻", 500, 5, 2, 80),
    EAGLE("🦅", 6, 20, 3, 1),
    HORSE("🐎", 400, 20, 4, 60),
    DEER("🦌", 300, 20, 4, 50),
    RABBIT("🐇", 2, 150, 2, 0.45),
    MOUSE("🐁", 0.05, 500, 1, 0.01),
    GOAT("🐐", 60, 140, 3, 10),
    SHEEP("🐑", 70, 140, 3, 15),
    BOAR("🐗", 400, 50, 2, 50),
    BUFFALO("🐃", 700, 10, 3, 100),
    DUCK("🦆", 1, 200, 4, 0.15),
    CATERPILLAR("🐛", 0.01, 1000, 0, 0);

    private final String unicodeSymbol;
    private final double weight;
    private final int maxPerCell;
    private final int maxSpeed;
    private final double foodNeeded;

    AnimalType(String unicodeSymbol, double weight, int maxPerCell, int maxSpeed, double foodNeeded) {
        this.unicodeSymbol = unicodeSymbol;
        this.weight = weight;
        this.maxPerCell = maxPerCell;
        this.maxSpeed = maxSpeed;
        this.foodNeeded = foodNeeded;
    }

    public String getUnicodeSymbol() {
        return unicodeSymbol;
    }

    public double getWeight() {
        return weight;
    }

    public int getMaxPerCell() {
        return maxPerCell;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public double getFoodNeeded() {
        return foodNeeded;
    }
}