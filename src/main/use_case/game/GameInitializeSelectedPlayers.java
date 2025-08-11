package main.use_case.game;

import main.entity.Game;
import main.entity.players.Player;

import java.util.List;

/**
 * Use case for setting the player list chosen from character selection.
 */
public class GameInitializeSelectedPlayers {
    private final Game game;
    private final List<Player> selectedPlayers;

    public GameInitializeSelectedPlayers(Game game, List<Player> selectedPlayers) {
        this.game = game;
        this.selectedPlayers = selectedPlayers;
    }

    public void execute() {
        game.setPlayers(selectedPlayers);
    }
}
