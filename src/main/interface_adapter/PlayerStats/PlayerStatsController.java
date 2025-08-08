package main.interface_adapter.PlayerStats;

import main.entity.Game;
import main.use_case.PlayerStats.PlayerStatsInputBoundary;

public class PlayerStatsController {
    private final PlayerStatsInputBoundary inputBoundary;

    /**
     * This class is a placeholder for constants used throughout the application.
     * @param inputBoundary  propertyName.
     */
    public PlayerStatsController(PlayerStatsInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @param game  propertyName.
     */
    public void execute(Game game) {
        inputBoundary.execute(game);
    }
}
