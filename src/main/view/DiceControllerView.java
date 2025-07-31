package main.view;

import main.interface_adapter.Dice.DiceViewModel;

import javax.swing.*;
import java.util.Random;

/**
 * DiceController handles dice rolling logic and animation.
 * Now follows Clean Architecture by delegating business logic to use cases.
 */
public class DiceControllerView {
    private final ImageIcon[] diceIcons = new ImageIcon[7];
    private final main.interface_adapter.Dice.DiceController diceController;
    private final Random rand = new Random();
    private Timer diceTimer;
    private int frameCount;
    private DiceViewModel currentResult;

    // Animation state - these change during animation
    private int animationD1 = 1, animationD2 = 1;

    public DiceControllerView() {
        loadDiceIcons();
        this.diceController = new main.interface_adapter.Dice.DiceController();
    }

    private void loadDiceIcons() {
        for (int i = 1; i <= 6; i++) {
            diceIcons[i] = new ImageIcon(getClass().getResource("/dice" + i + ".png"));
        }
    }

    /**
     * Starts dice animation and calls onComplete when finished.
     */
    public void startDiceAnimation(Runnable onAnimationFrame, Runnable onComplete) {
        frameCount = 0;

        // Get the final result first, but don't show it until animation ends
        currentResult = diceController.execute();

        diceTimer = new Timer(100, null);
        diceTimer.addActionListener(evt -> {
            if (frameCount < 10) {
                // During animation: show random dice faces for animation effect
                animationD1 = rand.nextInt(6) + 1;
                animationD2 = rand.nextInt(6) + 1;
                frameCount++;
                onAnimationFrame.run();
            } else {
                diceTimer.stop();
                // Animation complete - now we show the real result
                onComplete.run();
            }
        });
        diceTimer.start();
    }

    public int getLastDiceSum() {
        return currentResult != null ? currentResult.getSum() : 2;
    }

    public int getFinalD1() {
        // During animation, return animation dice; after animation, return real result
        if (diceTimer != null && diceTimer.isRunning()) {
            return animationD1;
        }
        return currentResult != null ? currentResult.getDice1() : 1;
    }

    public int getFinalD2() {
        // During animation, return animation dice; after animation, return real result
        if (diceTimer != null && diceTimer.isRunning()) {
            return animationD2;
        }
        return currentResult != null ? currentResult.getDice2() : 1;
    }

    public ImageIcon getDiceIcon(int face) {
        return diceIcons[face];
    }
}
