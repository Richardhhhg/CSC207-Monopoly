package main.interface_adapter.Dice;

public class DiceViewModel {
    private final int dice1;
    private final int dice2;
    private final int sum;

    public DiceViewModel(int dice1, int dice2, int sum) {
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.sum = sum;
    }

    public int getDice1() { return dice1; }
    public int getDice2() { return dice2; }
    public int getSum() { return sum; }

}
