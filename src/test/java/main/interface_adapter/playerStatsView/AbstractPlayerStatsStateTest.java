package main.interface_adapter.playerStatsView;

import main.interface_adapter.playerStats.DisplayPlayer;
import main.interface_adapter.playerStats.DisplayProperty;
import main.interface_adapter.playerStats.PlayerStatsState;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AbstractPlayerStatsStateTest {
    @Test
    void testGetPlayersReturnsUnmodifiableList() {
        DisplayPlayer player = new DisplayPlayer("Dave", 500f, false, null, null, Arrays.asList(new DisplayProperty("Bar")));
        PlayerStatsState state = new PlayerStatsState(Arrays.asList(player));
        assertEquals(1, state.getPlayers().size());
        assertThrows(UnsupportedOperationException.class, () -> state.getPlayers().add(player));
    }
}
