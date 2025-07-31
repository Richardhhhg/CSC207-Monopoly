package main.use_case.Dice;

import main.entity.Game;
import main.entity.players.Player;
import java.util.Random;

public class RollDice {
    private final Random random;

    public RollDice() {
        this.random = new Random();
    }

    public DiceResult execute() {
        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;
        int sum = dice1 + dice2;

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

        public int getDice1() { return dice1; }
        public int getDice2() { return dice2; }
        public int getSum() { return sum; }
    }
}
