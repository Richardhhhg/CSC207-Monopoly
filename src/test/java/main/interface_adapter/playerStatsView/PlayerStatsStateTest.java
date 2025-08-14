package main.interface_adapter.playerStatsView;

import main.interface_adapter.player_stats.DisplayPlayer;
import main.interface_adapter.player_stats.DisplayProperty;
import main.interface_adapter.player_stats.PlayerStatsState;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStatsStateTest {
    @Test
    void testGetPlayersReturnsUnmodifiableList() {
        DisplayPlayer player = new DisplayPlayer("Dave", 500f, false, null, null, Arrays.asList(new DisplayProperty("Bar")));
        PlayerStatsState state = new PlayerStatsState(Arrays.asList(player));
        assertEquals(1, state.getPlayers().size());
        assertThrows(UnsupportedOperationException.class, () -> state.getPlayers().add(player));
    }
}
