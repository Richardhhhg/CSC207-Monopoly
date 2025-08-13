package main.interface_adapter.dice;

import java.util.function.Consumer;

import main.use_case.dice.DiceOutputBoundary;
import main.use_case.dice.DiceOutputData;

/**
 * Presenter transforms use-case output into a DiceViewModel
 * and pushes it to the UI layer via a callback.
 */
public class DicePresenter implements DiceOutputBoundary {

    private final Consumer<DiceViewModel> viewCallback;

    public DicePresenter(Consumer<DiceViewModel> viewCallback) {
        this.viewCallback = viewCallback;
    }

    @Override
    public void presentDiceResult(DiceOutputData outputData) {
        final DiceViewModel viewModel = new DiceViewModel(
                outputData.getDice1(),
                outputData.getDice2(),
                outputData.getSum()
        );
        // Send the prepared view model to whoever is listening (e.g., DiceAnimator)
        viewCallback.accept(viewModel);
    }
}
