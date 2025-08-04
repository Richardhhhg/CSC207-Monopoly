package main.interface_adapter.PlayerStats;

import main.entity.Game;
import main.use_case.PlayerStats.PlayerStatsInputBoundary;

public class PlayerStatsController {
    private final PlayerStatsInputBoundary inputBoundary;

    public PlayerStatsController(PlayerStatsInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void execute(Game game) {
        inputBoundary.execute(game);
    }
}
