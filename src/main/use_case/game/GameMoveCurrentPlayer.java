package main.use_case.game;

import main.constants.Constants;
import main.entity.Game;
import main.entity.players.Player;

public class GameMoveCurrentPlayer {
    private final Game game;

    public GameMoveCurrentPlayer(Game game) {
        this.game = game;
    }

    /**
     * Moves the current player by the specified number of steps.
     * @param steps Number of steps to move the player.
     */
    public void execute(int steps) {
        final Player player = game.getCurrentPlayer();

        final int tileCount = game.getTileCount();
        if (player.getPosition() + steps >= tileCount) {
            player.addMoney(Constants.FINISH_LINE_BONUS);
        }
    }
}
