// ===================== FRAMEWORKS & DRIVERS LAYER =====================

// Animation Controller (handles UI animation logic)
package main.view.dice;

import main.interface_adapter.roll_dice.DiceViewModel;
import javax.swing.*;
import java.util.Random;

public class DiceAnimationController {
    private final DiceViewModel diceViewModel;
    private final Runnable onAnimationFrameCallback;
    private Timer animationTimer;
    private int frameCount;
    private final Random animationRandom = new Random();

    public DiceAnimationController(DiceViewModel diceViewModel,
                                   Runnable onAnimationFrameCallback) {
        this.diceViewModel = diceViewModel;
        this.onAnimationFrameCallback = onAnimationFrameCallback;
    }

    public void startAnimation(Runnable onComplete) {
        frameCount = 0;
        diceViewModel.setRolling(true);

        animationTimer = new Timer(100, null);
        animationTimer.addActionListener(evt -> {
            if (frameCount < 10) {
                // Show random values during animation
                diceViewModel.setDice1Value(animationRandom.nextInt(6) + 1);
                diceViewModel.setDice2Value(animationRandom.nextInt(6) + 1);
                frameCount++;

                // Trigger view update
                if (onAnimationFrameCallback != null) {
                    onAnimationFrameCallback.run();
                }
            } else {
                animationTimer.stop();
                onComplete.run();
            }
        });
        animationTimer.start();
    }

    public void stopAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
    }
}