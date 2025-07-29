package main.interface_adapter.dice;

import main.use_case.dice.RollDiceOutputBoundary;
import main.use_case.dice.RollDiceResponseModel;
import main.interface_adapter.view_model.DiceViewModel;

/**
 * Presenter for the roll‑dice use case.  Copies the dice values from the
 * response model into the view model.
 */
public class DicePresenter implements RollDiceOutputBoundary {
    private final DiceViewModel viewModel;

    public DicePresenter(DiceViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentDiceRoll(RollDiceResponseModel response) {
        viewModel.setDice(response.getDie1(), response.getDie2());
    }
}
