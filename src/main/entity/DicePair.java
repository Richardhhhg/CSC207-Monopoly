// Entity: Represents a pair of dice and their sum
package main.entity;

public class DicePair {
    private final Dice dice1;
    private final Dice dice2;

    public DicePair(Dice dice1, Dice dice2) {
        this.dice1 = dice1;
        this.dice2 = dice2;
    }

    public Dice getDice1() {
        return dice1;
    }

    public Dice getDice2() {
        return dice2;
    }

    public int getSum() {
        return dice1.getValue() + dice2.getValue();
    }

    public boolean isDouble() {
        return dice1.getValue() == dice2.getValue();
    }
}
