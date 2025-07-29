// Controller
package main.interface_adapter.roll_dice;

import main.use_case.roll_dice.RollDiceInputBoundary;
import main.use_case.roll_dice.RollDiceInputData;

public class RollDiceController {
    private final RollDiceInputBoundary rollDiceInteractor;

    public RollDiceController(RollDiceInputBoundary rollDiceInteractor) {
        this.rollDiceInteractor = rollDiceInteractor;
    }

    public void rollDice() {
        RollDiceInputData inputData = new RollDiceInputData();
        rollDiceInteractor.rollDice(inputData);
    }
}
