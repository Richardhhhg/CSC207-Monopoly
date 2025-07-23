package main.view;

import main.entity.Player;
import javax.swing.Timer;

/**
 * Handles player movement animation - pure view concern
 */
public class PlayerMovementAnimator {

    public void animatePlayerMovement(Player player, int steps, int tileCount,
                                      Runnable onMoveStep, Runnable onComplete) {
        Timer moveTimer = new Timer(300, null);
        final int[] movesLeft = {steps};

        moveTimer.addActionListener(e -> {
            if (movesLeft[0] > 0) {
                int newPosition = (player.getPosition() + 1) % tileCount;
                player.setPosition(newPosition);
                movesLeft[0]--;
                onMoveStep.run();
            } else {
                ((Timer) e.getSource()).stop();
                onComplete.run();
            }
        });
        moveTimer.start();
    }
}