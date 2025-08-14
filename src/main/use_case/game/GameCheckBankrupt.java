package main.use_case.game;

import java.util.List;

import main.entity.Game;
import main.entity.players.AbstractPlayer;

public class GameCheckBankrupt {
    private final Game game;

    public GameCheckBankrupt(Game game) {
        this.game = game;
    }

    /**
     * Checks if the player is bankrupt.
     * A player is considered bankrupt if they have no money and no properties.
     */
    public void execute() {
        final List<AbstractPlayer> players = game.getPlayers();
        final int currentPlayerIndex = game.getCurrentPlayerIndex();
        boolean foundNext = false;

        for (int i = 1; i <= players.size(); i++) {
            final int nextIndex = (currentPlayerIndex + i) % players.size();
            if (!players.get(nextIndex).isBankrupt()) {
                foundNext = true;
                break;
            }
        }

        if (!foundNext) {
            // All players are bankrupt - game over
            game.endGame("All players are bankrupt");
            game.setCurrentPlayerIndex(-1);
        }

        // Check if only one player remains solvent
        final long solventPlayers = players.stream().filter(player -> !player.isBankrupt()).count();
        if (solventPlayers == 1) {
            game.endGame("Only one player remains solvent");
        }
    }
}
