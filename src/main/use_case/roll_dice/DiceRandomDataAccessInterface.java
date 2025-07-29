// Data Access Interface for random number generation
package main.use_case.roll_dice;

public interface DiceRandomDataAccessInterface {
    int generateRandomNumber(int min, int max);
}
