package main.interface_adapter.dice;

import main.use_case.dice.RollDice;

public class DicePresenter {

    /**
     * Transforms the given dice roll result into a view model.
     *
     * @param diceResult the result of a dice roll, must not be null
     * @return a {@link DiceViewModel} populated with the two dice values and their sum
     */
    public DiceViewModel execute(RollDice.DiceResult diceResult) {
        return new DiceViewModel(
                diceResult.getDice1(),
                diceResult.getDice2(),
                diceResult.getSum()
        );
    }
}
