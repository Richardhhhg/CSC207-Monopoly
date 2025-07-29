package main.interface_adapter.dice;

import main.use_case.dice.RollDiceInputBoundary;
import main.interface_adapter.view_model.DiceViewModel;

/**
 * Controller for the roll‑dice use case.  Called by the view when the
 * user clicks the Roll Dice button.
 */
public class RollDiceController {
    private final RollDiceInputBoundary interactor;
    private final DiceViewModel viewModel;

    public RollDiceController(RollDiceInputBoundary interactor, DiceViewModel viewModel) {
        this.interactor = interactor;
        this.viewModel = viewModel;
    }

    public void rollDice() {
        interactor.rollDice();
    }

    public int getDie1() {
        return viewModel.getDie1();
    }
    public int getDie2() {
        return viewModel.getDie2();
    }
    public int getSum() {
        return viewModel.getSum();
    }
}
