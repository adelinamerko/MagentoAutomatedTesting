package selenium.training.enums;

public enum ClothesSize {
    XS(1), S(2), M(3), L(4), XL(5);

    private final int ordinalNumber;

    ClothesSize(int ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public int getOrdinalNumber() {
        return ordinalNumber;
    }
}
