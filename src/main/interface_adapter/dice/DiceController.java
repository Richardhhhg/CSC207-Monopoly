package main.interface_adapter.dice;

import main.use_case.Dice.RollDice;

public class DiceController {
    private final RollDice rollDiceUseCase;

    public DiceController() {
        this.rollDiceUseCase = new RollDice();
    }

    public RollDice.DiceResult execute() {
        return rollDiceUseCase.execute();
    }
}
