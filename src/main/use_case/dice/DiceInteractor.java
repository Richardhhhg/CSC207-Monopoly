package main.use_case.dice;

import java.util.Random;

import main.constants.Constants;

public class DiceInteractor implements DiceInputBoundary {
    private static final int SIDES = Constants.DICE_SIDES;
    private final DiceOutputBoundary outputBoundary;
    private final Random random;

    public DiceInteractor(DiceOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
        this.random = new Random();
    }

    @Override
    public void execute() {
        final int dice1 = random.nextInt(SIDES) + 1;
        final int dice2 = random.nextInt(SIDES) + 1;
        final int sum = dice1 + dice2;

        final DiceOutputData outputData = new DiceOutputData(dice1, dice2, sum);
        outputBoundary.presentDiceResult(outputData);
    }
}
