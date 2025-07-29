// Data Access Implementation
package main.data_access;

import main.use_case.roll_dice.DiceRandomDataAccessInterface;
import java.util.Random;

public class RandomNumberGenerator implements DiceRandomDataAccessInterface {
    private final Random random;

    public RandomNumberGenerator() {
        this.random = new Random();
    }

    public RandomNumberGenerator(long seed) {
        this.random = new Random(seed);
    }

    @Override
    public int generateRandomNumber(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}