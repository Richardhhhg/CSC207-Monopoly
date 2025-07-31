package main.interface_adapter.Dice;

import main.use_case.Dice.RollDice;

public class DiceController {
    private final RollDice rollDiceUseCase;

    public DiceController() {
        this.rollDiceUseCase = new RollDice();
    }

    public DiceViewModel execute() {
        RollDice.DiceResult result = rollDiceUseCase.execute();
        DicePresenter presenter = new DicePresenter();
        return presenter.execute(result);
    }
}
