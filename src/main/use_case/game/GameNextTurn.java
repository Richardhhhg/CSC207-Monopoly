package main.use_case.game;

import main.entity.Game;
import main.entity.players.AbstractPlayer;
import main.entity.players.applyAfterEffects;
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
        List<AbstractPlayer> abstractPlayers = game.getPlayers();
        if (game.getGameEnded()) return;

        int currentPlayerIndex = game.getCurrentPlayerIndex();
        AbstractPlayer currentAbstractPlayer = abstractPlayers.get(currentPlayerIndex);

        // Apply turn effects for the current player
        if (currentAbstractPlayer instanceof applyAfterEffects) {
            this.applyTurnEffects.execute(currentAbstractPlayer);
        }

        if (currentAbstractPlayer.isBankrupt()) {
            this.declareBankruptcy.execute(currentAbstractPlayer);
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