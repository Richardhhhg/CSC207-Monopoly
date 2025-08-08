package main.use_case.PlayerStats;

import main.entity.Game;

public interface PlayerStatsInputBoundary {
    /**
     * A execution of iteractor from the controller.
     * @param game the game we want to excecute
     */
    void execute(Game game);
}
