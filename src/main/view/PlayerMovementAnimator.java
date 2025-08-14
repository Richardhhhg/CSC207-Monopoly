package main.view;

import javax.swing.Timer;

import main.entity.players.AbstractPlayer;

/**
 * PlayerMovementAnimator handles the animation of player movement across the board.
 */
public class PlayerMovementAnimator {

    private static final int MOVE_DELAY_MS = 300;

    /**
     * Animates player movement step by step.
     * @param abstractPlayer The player to animate
     * @param steps Number of steps to move
     * @param tileCount Total number of tiles on the board
     * @param onMoveStep Callback for each movement step (for repainting)
     * @param onComplete Callback when animation is complete
     */
    public void animatePlayerMovement(AbstractPlayer abstractPlayer, int steps, int tileCount,
                                      Runnable onMoveStep, Runnable onComplete) {
        final Timer moveTimer = new Timer(MOVE_DELAY_MS, null);
        final int[] movesLeft = {steps};

        moveTimer.addActionListener(actionEvent -> {
            handleMovementStep(abstractPlayer, tileCount, onMoveStep, onComplete, moveTimer, movesLeft);
        });
        moveTimer.start();
    }

    private void handleMovementStep(AbstractPlayer abstractPlayer, int tileCount, Runnable onMoveStep,
                                    Runnable onComplete, Timer moveTimer, int[] movesLeft) {
        if (movesLeft[0] > 0) {
            final int newPosition = (abstractPlayer.getPosition() + 1) % tileCount;
            abstractPlayer.setPosition(newPosition);
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
