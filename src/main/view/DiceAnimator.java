package main.view;

import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import main.constants.Constants;
import main.interface_adapter.dice.DiceController;
import main.interface_adapter.dice.DicePresenter;
import main.interface_adapter.dice.DiceViewModel;
import main.use_case.dice.DiceInputBoundary;
import main.use_case.dice.DiceInteractor;

/**
 * DiceAnimator handles dice rolling animation and UI concerns.
 * Uses Clean Architecture: interactor -> presenter -> view model.
 */
public class DiceAnimator {
    private static final int SIDES = Constants.DICE_SIDES;
    private static final int ICON_COUNT = Constants.DICE_ICON_COUNT;
    private static final int FRAME_LIMIT = Constants.DICE_FRAME_LIMIT;
    private static final int DELAY_MS = Constants.DICE_DELAY_MS;
    private static final int MIN_SUM = Constants.DICE_MIN_SUM;
    private static final int DEFAULT_FACE = Constants.DICE_DEFAULT_FACE;

    private final ImageIcon[] diceIcons = new ImageIcon[ICON_COUNT];
    private final DiceController diceController;
    private final Random rand;
    private Timer diceTimer;
    private int frameCount;
    private DiceViewModel currentResult;
    private int animationD1;
    private int animationD2;

    /**
     * Constructs a DiceAnimator and loads dice face icons.
     */
    public DiceAnimator() {
        this.rand = new Random();

        // Presenter converts use-case output into a DiceViewModel
        // and pushes it back to this animator via applyResult(...)
        final DicePresenter presenter = new DicePresenter(this::applyResult);

        // Create interactor with the presenter
        final DiceInputBoundary diceInteractor = new DiceInteractor(presenter);
        this.diceController = new DiceController(diceInteractor);

        loadDiceIcons();
    }

    private void loadDiceIcons() {
        for (int i = 1; i <= SIDES; i++) {
            diceIcons[i] = new ImageIcon(getClass().getResource("/DicePicture/dice" + i + ".png"));
        }
    }

    // Called by the presenter when the final dice result is ready
    private void applyResult(DiceViewModel viewModel) {
        this.currentResult = viewModel;
    }

    /**
     * Starts dice animation and calls callbacks for each frame and completion.
     *
     * @param onAnimationFrame callback to run on each animation frame
     * @param onComplete       callback to run when animation completes
     */
    public void startDiceAnimation(Runnable onAnimationFrame, Runnable onComplete) {
        frameCount = 0;
        currentResult = null;

        diceTimer = new Timer(DELAY_MS, evt -> handleTimerEvent(onAnimationFrame, onComplete));
        diceTimer.start();
    }

    /**
     * Handles each timer tick during the dice animation.
     *
     * @param onAnimationFrame callback to invoke on each animation frame
     * @param onComplete       callback to invoke when animation completes
     */
    private void handleTimerEvent(Runnable onAnimationFrame, Runnable onComplete) {
        frameCount++;

        if (frameCount <= FRAME_LIMIT) {
            animationD1 = rand.nextInt(SIDES) + 1;
            animationD2 = rand.nextInt(SIDES) + 1;
            onAnimationFrame.run();
        }
        else if (frameCount == FRAME_LIMIT + 1) {
            diceController.execute();
            onAnimationFrame.run();
        }
        else {
            diceTimer.stop();
            onComplete.run();
        }
    }

    /**
     * Returns the last dice sum after animation, or a default minimal sum.
     *
     * @return the final dice sum, or MIN_SUM if unavailable
     */
    public int getLastDiceSum() {
        final int result;
        if (currentResult != null) {
            result = currentResult.getSum();
        }
        else {
            result = MIN_SUM;
        }
        return result;
    }

    /**
     * Returns the first die face to display, either during animation or final.
     *
     * @return the first die face value
     */
    public int getFinalD1() {
        final int result;
        if (diceTimer != null && diceTimer.isRunning() && frameCount <= FRAME_LIMIT) {
            result = animationD1;
        }
        else if (currentResult != null) {
            result = currentResult.getDice1();
        }
        else {
            result = DEFAULT_FACE;
        }
        return result;
    }

    /**
     * Returns the second die face to display, either during animation or final.
     *
     * @return the second die face value
     */
    public int getFinalD2() {
        final int result;
        if (diceTimer != null && diceTimer.isRunning() && frameCount <= FRAME_LIMIT) {
            result = animationD2;
        }
        else if (currentResult != null) {
            result = currentResult.getDice2();
        }
        else {
            result = DEFAULT_FACE;
        }
        return result;
    }

    /**
     * Returns the icon for a given die face.
     *
     * @param face the die face (1–SIDES)
     * @return the corresponding ImageIcon
     */
    public ImageIcon getDiceIcon(int face) {
        return diceIcons[face];
    }
}
