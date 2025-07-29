package main.use_case.dice;

import java.util.Random;

/**
 * Interactor for the roll‑dice use case.  Generates two random
 * dice values and passes them to the output boundary.
 */
public class RollDiceInteractor implements RollDiceInputBoundary {
    private final RollDiceOutputBoundary presenter;
    private final Random random = new Random();

    public RollDiceInteractor(RollDiceOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void rollDice() {
        int die1 = random.nextInt(6) + 1;
        int die2 = random.nextInt(6) + 1;
        presenter.presentDiceRoll(new RollDiceResponseModel(die1, die2));
    }
}
