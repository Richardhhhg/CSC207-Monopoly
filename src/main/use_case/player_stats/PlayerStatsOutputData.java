package main.use_case.player_stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds a list of player stats created by the interactor.
 * Sent to the presenter to prepare for display.
 */
public class PlayerStatsOutputData {
    private final List<PlayerStatsOutput> playerStats = new ArrayList<>();

    /**
     * Get output name.
     * @param stat .
     */
    public void add(PlayerStatsOutput stat) {
        playerStats.add(stat);
    }

    /**
     * Get output name.
     * @return  a list of PlayerStatsOutput.
     */
    public List<PlayerStatsOutput> getPlayerStats() {
        return Collections.unmodifiableList(playerStats);
    }
}
