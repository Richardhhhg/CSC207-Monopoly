package main.use_case.game;

import main.entity.Game;
import main.entity.players.AbstractPlayer;
import main.entity.players.ApplyAfterEffects;
import main.use_case.player.ApplyTurnEffects;
import main.use_case.player.DeclareBankruptcy;

import java.util.List;

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

    public void execute() {
        List<AbstractPlayer> players = game.getPlayers();
        if (game.getGameEnded()) return;

        int currentPlayerIndex = game.getCurrentPlayerIndex();
        AbstractPlayer currentPlayer = players.get(currentPlayerIndex);

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
        int nextIndex = findNextActivePlayer(currentPlayerIndex);

        if (nextIndex == -1) {
            // All players are bankrupt
            game.endGame("All players are bankrupt");
            game.setCurrentPlayerIndex(-1);
            return;
        }

        // Check if we've completed a full round BEFORE setting the next player
        boolean roundComplete = game.isRoundComplete(nextIndex);

        game.setCurrentPlayerIndex(nextIndex);

        // If round is complete, handle round transition
        if (roundComplete) {
            GameNextRound nextRound = new GameNextRound(game);
            nextRound.execute();

            // Start a new round with the current next player
            game.startNewRound(nextIndex);
        }

        // Check for game-ending conditions
        GameCheckBankrupt checkBankrupt = new GameCheckBankrupt(game);
        checkBankrupt.execute();
    }

    /**
     * Find the next active (non-bankrupt) player in turn order
     */
    private int findNextActivePlayer(int currentPlayerIndex) {
        return game.findNextActivePlayerFrom(currentPlayerIndex);
    }
}