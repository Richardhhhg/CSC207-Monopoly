package main.interface_adapter.PlayerStats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerStatsState {
    private final List<DisplayPlayer> players;

    /**
     * This class is a placeholder for constants used throughout the application.
     * @param players  propertyName.
     */
    public PlayerStatsState(List<DisplayPlayer> players) {
        this.players = new ArrayList<>(players);
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @return Collections.unmodifiableList(players);
     */
    public List<DisplayPlayer> getPlayers() {
        return Collections.unmodifiableList(players);
    }
}
