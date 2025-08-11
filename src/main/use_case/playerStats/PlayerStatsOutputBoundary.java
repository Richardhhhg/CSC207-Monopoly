package main.use_case.playerStats;

/**
 * Output for the Player Stats use case.
 * The interactor calls this to send results to the presenter.
 */
public interface PlayerStatsOutputBoundary {
    /**
     * Sends player stats to presenter to be shown on screen.
     * @param outputData the player stats to show
     */
    void presentPlayerStats(PlayerStatsOutputData outputData);
}
