package main.interface_adapter.PlayerStats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerStatsState {
    private final List<DisplayPlayer> players;

    public PlayerStatsState(List<DisplayPlayer> players) {
        this.players = new ArrayList<>(players);
    }

    public List<DisplayPlayer> getPlayers() {
        return Collections.unmodifiableList(players);
    }
}
