package main.use_case.player_stats;

import main.entity.Game;

/**
 * Input Boundary for the Player Stats use case.
 * This interface defines the method that the controller can call to request
 * the execution of the Player Stats use case.
 */
public interface PlayerStatsInputBoundary {
    /**
     * A execution of iteractor from the controller.
     * @param game the game we want to excecute
     */
    void execute(Game game);
}
