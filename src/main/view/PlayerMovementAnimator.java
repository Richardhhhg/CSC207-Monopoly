package main.view;

import main.entity.players.Player;
import javax.swing.Timer;

/**
 * PlayerMovementAnimator handles the animation of player movement across the board.
 */
public class PlayerMovementAnimator {

    /**
     * Animates player movement step by step.
     * @param player The player to animate
     * @param steps Number of steps to move
     * @param tileCount Total number of tiles on the board
     * @param onMoveStep Callback for each movement step (for repainting)
     * @param onComplete Callback when animation is complete
     */
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