package main.view;

import main.interface_adapter.Dice.DiceViewModel;

import javax.swing.*;

/**
 * DiceController handles dice rolling logic and animation.
 * Now follows Clean Architecture by delegating business logic to use cases.
 */
public class DiceControllerView {
    private final ImageIcon[] diceIcons = new ImageIcon[7];
    private final main.interface_adapter.Dice.DiceController diceController;
    private Timer diceTimer;
    private int frameCount;
    private DiceViewModel currentResult;

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

        diceTimer = new Timer(100, null);
        diceTimer.addActionListener(evt -> {
            if (frameCount < 10) {
                // Animation frames - show random dice during animation
                frameCount++;
                onAnimationFrame.run();
            } else {
                diceTimer.stop();

                // Get final dice result from controller
                currentResult = diceController.execute();

                // Trigger completion callback
                onComplete.run();
            }
        });
        diceTimer.start();
    }

    public int getLastDiceSum() {
        return currentResult != null ? currentResult.getSum() : 2;
    }

    public int getFinalD1() {
        return currentResult != null ? currentResult.getDice1() : 1;
    }

    public int getFinalD2() {
        return currentResult != null ? currentResult.getDice2() : 1;
    }

    public ImageIcon getDiceIcon(int face) {
        return diceIcons[face];
    }
}
