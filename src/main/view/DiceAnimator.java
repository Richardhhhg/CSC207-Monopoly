package main.view;

import main.interface_adapter.Dice.DiceController;
import main.interface_adapter.Dice.DicePresenter;
import main.interface_adapter.Dice.DiceViewModel;
import main.use_case.Dice.RollDice;

import javax.swing.*;
import java.util.Random;

/**
 * DiceAnimator handles dice rolling animation and UI concerns.
 * Delegates business logic to controllers and use cases.
 */
public class DiceAnimator {
    private final ImageIcon[] diceIcons = new ImageIcon[7];
    private final DiceController diceController;
    private final DicePresenter dicePresenter;
    private final Random rand = new Random();
    private Timer diceTimer;
    private int frameCount;
    private DiceViewModel currentResult;

    // Animation state - these change during animation
    private int animationD1 = 1, animationD2 = 1;

    public DiceAnimator() {
        loadDiceIcons();
        this.diceController = new DiceController();
        this.dicePresenter = new DicePresenter();
    }

    private void loadDiceIcons() {
        for (int i = 1; i <= 6; i++) {
            diceIcons[i] = new ImageIcon(getClass().getResource("/DicePicture/dice" + i + ".png"));
        }
    }

    /**
     * Starts dice animation and calls onComplete when finished.
     */
    public void startDiceAnimation(Runnable onAnimationFrame, Runnable onComplete) {
        frameCount = 0;
        currentResult = null; // Clear previous result

        diceTimer = new Timer(100, null);
        diceTimer.addActionListener(evt -> {
            if (frameCount < 9) {
                // During animation: show random dice faces for animation effect
                animationD1 = rand.nextInt(6) + 1;
                animationD2 = rand.nextInt(6) + 1;
                frameCount++;
                onAnimationFrame.run();
            } else if (frameCount == 9) {
                // Last animation frame: get the final result and show it immediately
                RollDice.DiceResult result = diceController.execute();
                currentResult = dicePresenter.execute(result);
                frameCount++;

                // Show the final result immediately (no pause)
                onAnimationFrame.run();
            } else {
                // Animation truly complete
                diceTimer.stop();
                onComplete.run();
            }
        });
        diceTimer.start();
    }

    public int getLastDiceSum() {
        return currentResult != null ? currentResult.getSum() : 2;
    }

    public int getFinalD1() {
        // During animation (frames 0-8), return animation dice
        // On frame 9+, return real result
        if (diceTimer != null && diceTimer.isRunning() && frameCount < 9) {
            return animationD1;
        }
        return currentResult != null ? currentResult.getDice1() : 1;
    }

    public int getFinalD2() {
        // During animation (frames 0-8), return animation dice
        // On frame 9+, return real result
        if (diceTimer != null && diceTimer.isRunning() && frameCount < 9) {
            return animationD2;
        }
        return currentResult != null ? currentResult.getDice2() : 1;
    }

    public ImageIcon getDiceIcon(int face) {
        return diceIcons[face];
    }
}