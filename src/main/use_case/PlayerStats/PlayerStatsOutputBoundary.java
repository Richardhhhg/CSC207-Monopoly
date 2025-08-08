package main.use_case.PlayerStats;

public interface PlayerStatsOutputBoundary {
    /**
     * Get output name.
     * @param outputData .
     */
    void presentPlayerStats(PlayerStatsOutputData outputData);
}
