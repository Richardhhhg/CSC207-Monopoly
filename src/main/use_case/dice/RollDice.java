package main.use_case.dice;

import java.util.Random;

import main.constants.Constants;

/**
 * Use case that performs a roll of two dice.
 */
public class RollDice {

    /** Number of sides on each die. */
    private static final int SIDES = Constants.DICE_SIDES;
    private final Random random;

    public RollDice() {
        this.random = new Random();
    }

    /**
     * Rolls two dice and returns both values plus their sum.
     *
     * @return a {@link DiceResult} containing the two individual die results and their sum
     */
    public DiceResult execute() {
        final int dice1 = random.nextInt(SIDES) + 1;
        final int dice2 = random.nextInt(SIDES) + 1;
        final int sum = dice1 + dice2;

        return new DiceResult(dice1, dice2, sum);
    }

    public static class DiceResult {
        private final int dice1;
        private final int dice2;
        private final int sum;

        public DiceResult(int dice1, int dice2, int sum) {
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
}
