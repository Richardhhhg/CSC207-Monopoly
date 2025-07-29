// Entity: Core business data and rules for dice
package main.entity;

public class Dice {private final int value;
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 6;

    public Dice(int value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException("Dice value must be between 1 and 6");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static int getMinValue() {
        return MIN_VALUE;
    }

    public static int getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Dice dice = (Dice) obj;
        return value == dice.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
