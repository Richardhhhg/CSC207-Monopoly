package main.interface_adapter.PlayerStats;

import java.util.Collections;

/**
 * Holds the current player stats for the view to read.
 */
public class PlayerStatsViewModel {
    private PlayerStatsState state = new PlayerStatsState(Collections.emptyList());

    public PlayerStatsState getState() {
        return state;
    }

    public void setState(PlayerStatsState state) {
        this.state = state;
    }
}
