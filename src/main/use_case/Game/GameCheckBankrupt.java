package main.use_case.Game;

import main.entity.Game;
import main.entity.players.Player;

import java.util.List;

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
        List<Player> players = game.getPlayers();
        int currentPlayerIndex = game.getCurrentPlayerIndex();
        boolean foundNext= false;

        for (int i = 1; i <= players.size(); i++) {
            int nextIndex = (currentPlayerIndex + i) % players.size();
            if (!players.get(nextIndex).isBankrupt()) {
                foundNext = true;
                break;
            }
        }

        // All players are bankrupt - game over
        if (!foundNext) {
            game.endGame("All players are bankrupt");
            game.setCurrentPlayerIndex(-1);
            return;
        }

        // Check if only one player remains solvent
        long solventPlayers = players.stream().filter(p -> !p.isBankrupt()).count();
        if (solventPlayers == 1) {
            game.endGame("Only one player remains solvent");
        }
    }
}
