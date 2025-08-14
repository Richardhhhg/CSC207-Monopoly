package main.use_case.game;

import java.util.List;

import main.entity.Game;
import main.entity.players.AbstractPlayer;
import main.entity.players.ApplyAfterEffects;
import main.use_case.player.ApplyTurnEffects;
import main.use_case.player.DeclareBankruptcy;

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
     * Executes the logic for advancing to the next turn in the game.
     * This includes applying turn effects, checking for bankruptcy,
     * and determining the next active player.
     *
     * @throws IllegalStateException if the game has already ended.
     */
    public void execute() {
        final List<AbstractPlayer> players = game.getPlayers();
        if (game.getGameEnded()) {
            throw new IllegalStateException("Game has already ended");
        }

        final int currentPlayerIndex = game.getCurrentPlayerIndex();
        final AbstractPlayer currentPlayer = players.get(currentPlayerIndex);

        // Apply turn effects for the current player
        if (currentPlayer instanceof ApplyAfterEffects) {
            this.applyTurnEffects.execute(currentPlayer);
        }

        if (currentPlayer.isBankrupt()) {
            this.declareBankruptcy.execute(currentPlayer);
        }

        // Increment turn counter
        game.increaseTurn();

        // Find the next non-bankrupt player
        final int nextIndex = findNextActivePlayer(currentPlayerIndex);

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
     *
     * @param currentPlayerIndex the index of the current player
     * @return the index of the next active player, or -1 if no active players remain
     */
    private int findNextActivePlayer(int currentPlayerIndex) {
        return game.findNextActivePlayerFrom(currentPlayerIndex);
    }
}
