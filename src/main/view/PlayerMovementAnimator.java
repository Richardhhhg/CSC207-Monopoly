package main.view;

import javax.swing.Timer;

import main.entity.players.Player;

/**
 * PlayerMovementAnimator handles the animation of player movement across the board.
 */
public class PlayerMovementAnimator {

    private static final int MOVE_DELAY_MS = 300;

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
        final Timer moveTimer = new Timer(MOVE_DELAY_MS, null);
        final int[] movesLeft = {steps};

        moveTimer.addActionListener(actionEvent -> {
            handleMovementStep(player, tileCount, onMoveStep, onComplete, moveTimer, movesLeft);
        });
        moveTimer.start();
    }

    private void handleMovementStep(Player player, int tileCount, Runnable onMoveStep,
                                    Runnable onComplete, Timer moveTimer, int[] movesLeft) {
        if (movesLeft[0] > 0) {
            final int newPosition = (player.getPosition() + 1) % tileCount;
            player.setPosition(newPosition);
            movesLeft[0]--;

            // Trigger repaint
            onMoveStep.run();
        }
        else {
            moveTimer.stop();
            // Animation complete
            onComplete.run();
        }
    }
}
