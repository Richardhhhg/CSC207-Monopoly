// Use Case Interactor
package main.use_case.roll_dice;

import main.entity.Dice;
import main.entity.DicePair;

public class RollDiceInteractor implements RollDiceInputBoundary {
    private final RollDiceOutputBoundary outputBoundary;
    private final DiceRandomDataAccessInterface randomDataAccess;

    public RollDiceInteractor(RollDiceOutputBoundary outputBoundary,
                              DiceRandomDataAccessInterface randomDataAccess) {
        this.outputBoundary = outputBoundary;
        this.randomDataAccess = randomDataAccess;
    }

    @Override
    public void rollDice(RollDiceInputData inputData) {
        // Generate random values for both dice
        int value1 = randomDataAccess.generateRandomNumber(
                Dice.getMinValue(), Dice.getMaxValue());
        int value2 = randomDataAccess.generateRandomNumber(
                Dice.getMinValue(), Dice.getMaxValue());

        // Create dice entities
        Dice dice1 = new Dice(value1);
        Dice dice2 = new Dice(value2);
        DicePair dicePair = new DicePair(dice1, dice2);

        // Create output data
        RollDiceOutputData outputData = new RollDiceOutputData(dicePair);

        // Present the result
        outputBoundary.presentDiceRoll(outputData);
    }
}
