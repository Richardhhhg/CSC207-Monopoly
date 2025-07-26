package main.use_case.Game;

import main.entity.Game;
import main.use_case.Player;

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
                currentPlayerIndex = nextIndex; // idk if this is needed but i'll keep it since it was in original - Richard
                foundNext = true;
                break;
            }
        }

        // Idk if this condition will ever execute, but it's here just in case - Richard
        if (!foundNext) {
            // All players are bankrupt - game over
            game.endGame("All players are bankrupt");
            game.setCurrentPlayerIndex(-1); // idk if this is needed but i'll keep it since it was in original- Richard
            return;
        }

        // Check if only one player remains solvent
        long solventPlayers = players.stream().filter(p -> !p.isBankrupt()).count();
        if (solventPlayers == 1) {
            game.endGame("Only one player remains solvent");
        }
    }
}
