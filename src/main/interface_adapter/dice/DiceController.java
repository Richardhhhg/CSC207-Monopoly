package main.interface_adapter.dice;

import main.use_case.dice.DiceInputBoundary;

public class DiceController {
    private final DiceInputBoundary diceInputBoundary;

    public DiceController(DiceInputBoundary diceInputBoundary) {
        this.diceInputBoundary = diceInputBoundary;
    }

    /**
     * Executes the dice roll use case.
     */
    public void execute() {
        diceInputBoundary.execute();
    }
}
