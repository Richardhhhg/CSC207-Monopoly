package main.use_case.game;

import java.util.List;

import main.entity.Game;
import main.entity.players.Player;
import main.entity.players.applyAfterEffects;
import main.use_case.Player.ApplyTurnEffects;
import main.use_case.Player.DeclareBankruptcy;

/**
 * This is a use case of game for advancing to the next turn.
 */
public class GameNextTurn {
    private Game game;
    private ApplyTurnEffects applyTurnEffects;
    private DeclareBankruptcy declareBankruptcy;

    public GameNextTurn(Game game) {
        this.game = game;
        this.applyTurnEffects = new ApplyTurnEffects();
        this.declareBankruptcy = new DeclareBankruptcy();
    }

    /**
     * Executes the use case for the next turn.
     */
    public void execute() {
        final List<Player> players = game.getPlayers();

        final int currentPlayerIndex = game.getCurrentPlayerIndex();
        final Player currentPlayer = players.get(currentPlayerIndex);

        // Apply turn effects for the current player
        if (currentPlayer instanceof applyAfterEffects) {
            this.applyTurnEffects.execute(currentPlayer);
        }

        if (currentPlayer.isBankrupt()) {
            this.declareBankruptcy.execute(currentPlayer);
        }

        // Increment turn counter
        game.increaseTurn();

        // Find the next non-bankrupt player
        final int nextIndex = findNextActivePlayer(currentPlayerIndex);

        if (nextIndex == -1) {
            // All players are bankrupt
            game.endGame("All players are bankrupt");
            game.setCurrentPlayerIndex(-1);
        }

        // Check if we've completed a full round BEFORE setting the next player
        final boolean roundComplete = game.isRoundComplete(nextIndex);

        game.setCurrentPlayerIndex(nextIndex);

        // If round is complete, handle round transition
        if (roundComplete) {
            final GameNextRound nextRound = new GameNextRound(game);
            nextRound.execute();

            // Start a new round with the current next player
            game.startNewRound(nextIndex);
        }

        // Check for game-ending conditions
        final GameCheckBankrupt checkBankrupt = new GameCheckBankrupt(game);
        checkBankrupt.execute();
    }

    /**
     * Find the next active (non-bankrupt) player in turn order.
     * @param currentPlayerIndex Index of current player.
     * @return the next player.
     */
    private int findNextActivePlayer(int currentPlayerIndex) {
        return game.findNextActivePlayerFrom(currentPlayerIndex);
    }
}
