package main.interface_adapter.dice;

import main.use_case.dice.RollDice;

public class DiceController {
    private final RollDice rollDiceUseCase;

    public DiceController() {
        this.rollDiceUseCase = new RollDice();
    }

    /**
     * Executes the dice roll and returns the outcome.
     *
     * @return a {RollDice.DiceResult} representing the rolled values
     */
    public RollDice.DiceResult execute() {
        return rollDiceUseCase.execute();
    }
}
