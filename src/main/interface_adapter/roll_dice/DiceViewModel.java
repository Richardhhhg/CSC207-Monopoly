// View Model for dice state
package main.interface_adapter.roll_dice;

public class DiceViewModel {
    private int dice1Value = 1;
    private int dice2Value = 1;
    private int sum = 2;
    private boolean isRolling = false;
    private boolean isDouble = false;

    public int getDice1Value() {
        return dice1Value;
    }

    public void setDice1Value(int dice1Value) {
        this.dice1Value = dice1Value;
    }

    public int getDice2Value() {
        return dice2Value;
    }

    public void setDice2Value(int dice2Value) {
        this.dice2Value = dice2Value;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public boolean isRolling() {
        return isRolling;
    }

    public void setRolling(boolean rolling) {
        isRolling = rolling;
    }

    public boolean isDouble() {
        return isDouble;
    }

    public void setDouble(boolean aDouble) {
        isDouble = aDouble;
    }
}
