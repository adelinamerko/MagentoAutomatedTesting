package selenium.training.enums;

public enum ClothesColor {
    FIRST_COLOR(1), SECOND_COLOR(2), THIRD_COLOR(3);

    private final int ordinalNumber;

    ClothesColor(int ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public int getOrdinalNumber() {
        return ordinalNumber;
    }
}
