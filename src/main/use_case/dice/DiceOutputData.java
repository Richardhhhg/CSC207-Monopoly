package main.use_case.dice;

public class DiceOutputData {
    private final int dice1;
    private final int dice2;
    private final int sum;

    public DiceOutputData(int dice1, int dice2, int sum) {
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.sum = sum;
    }

    public int getDice1() {
        return dice1;
    }

    public int getDice2() {
        return dice2;
    }

    public int getSum() {
        return sum;
    }
}
