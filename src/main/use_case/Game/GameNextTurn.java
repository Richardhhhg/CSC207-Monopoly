package main.use_case.Game;

import main.entity.Game;
import main.use_case.Player;

import java.util.List;

/**
 * This is a use case of game for advancing to the next turn.
 */
public class GameNextTurn {
    private Game game;
    private static final int TURNS_PER_ROUND = 4; // TODO: This is hardcoded, this does not account for player deaths

    public GameNextTurn(Game game) {
        this.game = game;
    }

    public void execute() {
        List<Player> players = game.getPlayers();
        int totalTurns = game.getTotalTurns();
        if (game.getGameEnded()) return;

        int currentPlayerIndex = game.getCurrentPlayerIndex();
        Player currentPlayer = players.get(currentPlayerIndex);
        boolean foundNext = false;

        // FIXME: There may be a bug if current player has no turn effects
        currentPlayer.applyTurnEffects();
        game.increaseTurn();

        // FIXME: this does not account for player deaths
        if (game.getTotalTurns() % TURNS_PER_ROUND == 0) {
            GameNextRound nextRound = new GameNextRound(game);
            nextRound.execute();
        }

        // Temp Comment: This checks if any player is bankrupt
        // TODO: I don't think it's possible for any player but current player to be bankrupt on current player turn
        // TODO: Make this separate class for checking if game ended
        GameCheckBankrupt checkBankrupt = new GameCheckBankrupt(game);
        checkBankrupt.execute();
    }
}
