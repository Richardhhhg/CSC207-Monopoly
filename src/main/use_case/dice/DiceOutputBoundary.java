package main.use_case.dice;

public interface DiceOutputBoundary {
    /**
     * Presents the dice roll result to the user.
     *
     * @param outputData the result of the dice roll
     */
    void presentDiceResult(DiceOutputData outputData);
}
