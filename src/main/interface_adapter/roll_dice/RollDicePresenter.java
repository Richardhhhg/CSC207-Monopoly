// Presenter
package main.interface_adapter.roll_dice;

import main.use_case.roll_dice.RollDiceOutputBoundary;
import main.use_case.roll_dice.RollDiceOutputData;

public class RollDicePresenter implements RollDiceOutputBoundary {
    private final DiceViewModel diceViewModel;
    private final DiceAnimationCallback animationCallback;

    public interface DiceAnimationCallback {
        void onDiceRollComplete(DiceViewModel viewModel);
    }

    public RollDicePresenter(DiceViewModel diceViewModel,
                             DiceAnimationCallback animationCallback) {
        this.diceViewModel = diceViewModel;
        this.animationCallback = animationCallback;
    }

    @Override
    public void presentDiceRoll(RollDiceOutputData outputData) {
        // Update view model with new dice values
        diceViewModel.setDice1Value(outputData.getDice1Value());
        diceViewModel.setDice2Value(outputData.getDice2Value());
        diceViewModel.setSum(outputData.getSum());
        diceViewModel.setDouble(outputData.isDouble());
        diceViewModel.setRolling(false);

        // Notify the view through callback
        if (animationCallback != null) {
            animationCallback.onDiceRollComplete(diceViewModel);
        }
    }

    public void presentDiceRolling() {
        diceViewModel.setRolling(true);
    }
}
