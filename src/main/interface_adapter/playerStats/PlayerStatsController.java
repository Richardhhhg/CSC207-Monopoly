package main.interface_adapter.playerStats;

import main.entity.Game;
import main.use_case.playerStats.PlayerStatsInputBoundary;

/**
 * Passes requests from the view to the Player Stats use case.
 */
public class PlayerStatsController {
    private final PlayerStatsInputBoundary inputBoundary;

    /**
     * Initialize a controller.
     * @param inputBoundary  propertyName.
     */
    public PlayerStatsController(PlayerStatsInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    /**
     * Makes the interactor excecute game.
     * @param game  propertyName.
     */
    public void execute(Game game) {
        inputBoundary.execute(game);
    }
}
