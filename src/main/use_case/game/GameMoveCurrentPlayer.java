package main.use_case.game;

import main.constants.Constants;
import main.entity.Game;
import main.entity.players.AbstractPlayer;

public class GameMoveCurrentPlayer {
    private final Game game;

    public GameMoveCurrentPlayer(Game game) {
        this.game = game;
    }

    /**
     * Moves the current player forward by a specified number of steps.
     * If the player reaches or exceeds the finish line, they receive a bonus.
     *
     * @param steps the number of steps to move the current player
     * @throws IllegalStateException if the game has ended or there is no current player
     */
    public void execute(int steps) throws IllegalStateException {
        if (game.getGameEnded() || game.getCurrentPlayer() == null) {
            throw new IllegalStateException("Game has ended or no current player.");
        }
        final AbstractPlayer player = game.getCurrentPlayer();

        final int tileCount = game.getTileCount();
        if (player.getPosition() + steps >= tileCount) {
            player.addMoney(Constants.FINISH_LINE_BONUS);
        }
    }
}
