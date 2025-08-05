package main.use_case.Game;

import main.entity.Game;
import main.entity.players.Player;
import main.entity.players.applyAfterEffects;
import main.use_case.Player.ApplyTurnEffects;
import main.use_case.Player.DeclareBankruptcy;

import java.util.List;

/**
 * This is a use case of game for advancing to the next turn.
 */
public class GameNextTurn {
    private final Game game;
    private final ApplyTurnEffects applyTurnEffects;
    private final DeclareBankruptcy declareBankruptcy;

    public GameNextTurn(Game game) {
        this.game = game;
        this.applyTurnEffects = new ApplyTurnEffects();
        this.declareBankruptcy = new DeclareBankruptcy();
    }

    public void execute() {
        List<Player> players = game.getPlayers();
        if (game.getGameEnded()) return;

        int currentPlayerIndex = game.getCurrentPlayerIndex();
        Player currentPlayer = players.get(currentPlayerIndex);

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