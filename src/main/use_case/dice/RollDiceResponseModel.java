package main.use_case.dice;

/**
 * Data transfer object containing the results of a dice roll.
 */
public class RollDiceResponseModel {
    private final int die1;
    private final int die2;

    public RollDiceResponseModel(int die1, int die2) {
        this.die1 = die1;
        this.die2 = die2;
    }
    public int getDie1() {
        return die1;
    }
    public int getDie2() {
        return die2;
    }
    public int getSum() {
        return die1 + die2;
    }
}
