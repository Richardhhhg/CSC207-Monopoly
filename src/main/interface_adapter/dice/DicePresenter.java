package main.interface_adapter.dice;

import main.use_case.Dice.RollDice;

public class DicePresenter {
    public DiceViewModel execute(RollDice.DiceResult diceResult) {
        return new DiceViewModel(
                diceResult.getDice1(),
                diceResult.getDice2(),
                diceResult.getSum()
        );
    }
}
