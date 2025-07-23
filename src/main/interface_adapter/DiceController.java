package main.interface_adapter;

import javax.swing.*;
import java.util.Random;

/**
 * Specialized controller for dice rolling logic and animation
 */
public class DiceController {
    private final ImageIcon[] diceIcons = new ImageIcon[7];
    private final Random rand = new Random();
    private Timer diceTimer;
    private int frameCount;
    private int finalD1 = 1, finalD2 = 1;
    private int lastDiceSum = 2;

    public DiceController() {
        loadDiceIcons();
    }

    private void loadDiceIcons() {
        for (int i = 1; i <= 6; i++) {
            diceIcons[i] = new ImageIcon(getClass().getResource("images/dice" + i + ".png"));
        }
    }

    public void startDiceAnimation(Runnable onAnimationFrame, Runnable onComplete) {
        frameCount = 0;

        diceTimer = new Timer(100, null);
        diceTimer.addActionListener(evt -> {
            if (frameCount < 10) {
                finalD1 = rand.nextInt(6) + 1;
                finalD2 = rand.nextInt(6) + 1;
                frameCount++;
                onAnimationFrame.run();
            } else {
                diceTimer.stop();
                finalD1 = rand.nextInt(6) + 1;
                finalD2 = rand.nextInt(6) + 1;
                lastDiceSum = finalD1 + finalD2;
                onComplete.run();
            }
        });
        diceTimer.start();
    }

    // Getters
    public int getLastDiceSum() { return lastDiceSum; }
    public int getFinalD1() { return finalD1; }
    public int getFinalD2() { return finalD2; }
    public ImageIcon getDiceIcon(int face) { return diceIcons[face]; }
}