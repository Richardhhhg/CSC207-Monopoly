package main.view;

import javax.swing.*;
import java.util.Random;

/**
 * DiceAnimator handles the visual animation of rolling dice.  It accepts
 * predetermined final face values, shows random faces during the roll,
 * then displays the final faces when the animation finishes.
 */
public class DiceAnimator {
    private final ImageIcon[] diceIcons = new ImageIcon[7];
    private Timer diceTimer;
    private int frameCount;
    private int currentD1 = 1;
    private int currentD2 = 1;

    public DiceAnimator() {
        loadDiceIcons();
    }

    private void loadDiceIcons() {
        for (int i = 1; i <= 6; i++) {
            diceIcons[i] = new ImageIcon(getClass().getResource("images/dice" + i + ".png"));
        }
    }

    public void startDiceAnimation(int finalD1, int finalD2, Runnable onAnimationFrame, Runnable onComplete) {
        frameCount = 0;
        Random rand = new Random();
        diceTimer = new Timer(100, null);
        diceTimer.addActionListener(evt -> {
            if (frameCount < 10) {
                // random faces during animation
                currentD1 = rand.nextInt(6) + 1;
                currentD2 = rand.nextInt(6) + 1;
                frameCount++;
                onAnimationFrame.run();
            } else {
                diceTimer.stop();
                // show predetermined result
                currentD1 = finalD1;
                currentD2 = finalD2;
                onComplete.run();
            }
        });
        diceTimer.start();
    }

    public int getCurrentD1() {
        return currentD1;
    }
    // Aliases for backward compatibility with BoardRenderer
    public int getFinalD1() {
        return getCurrentD1();
    }
    public int getCurrentD2() {
        return currentD2;
    }
    public int getFinalD2() {
        return getCurrentD2();
    }

    public ImageIcon getDiceIcon(int face) {
        return diceIcons[face];
    }

    // For compatibility with BoardRenderer.drawDice()
    public int getLastDiceSum() {
        return currentD1 + currentD2;
    }
}
