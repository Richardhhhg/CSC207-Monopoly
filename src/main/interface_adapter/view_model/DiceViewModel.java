package main.interface_adapter.view_model;

/**
 * A simple view model for storing the values of two dice.  The presenter
 * writes to this object, and the controller reads from it.
 */
public class DiceViewModel {
    private int die1;
    private int die2;

    public int getDie1() {
        return die1;
    }
    public int getDie2() {
        return die2;
    }
    public int getSum() {
        return die1 + die2;
    }
    public void setDice(int die1, int die2) {
        this.die1 = die1;
        this.die2 = die2;
    }
}
