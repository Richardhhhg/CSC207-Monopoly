package main.use_case.PlayerStats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerStatsOutputData {
    private final List<PlayerStatsOutput> playerStats = new ArrayList<>();

    public void add(PlayerStatsOutput stat) {
        playerStats.add(stat);
    }

    public List<PlayerStatsOutput> getPlayerStats() {
        return Collections.unmodifiableList(playerStats);
    }
}
