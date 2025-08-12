package playerstatsview;

import main.interface_adapter.playerStats.DisplayPlayer;
import main.interface_adapter.playerStats.DisplayProperty;
import main.interface_adapter.playerStats.PlayerStatsState;
import main.interface_adapter.playerStats.PlayerStatsViewModel;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStatsViewModelTest {

    @Test
    void testSetAndGetState() {
        PlayerStatsViewModel vm = new PlayerStatsViewModel();
        DisplayPlayer player = new DisplayPlayer("Fake1", 300f, false, null, null, Arrays.asList(new DisplayProperty("Foo2")));
        PlayerStatsState state = new PlayerStatsState(Arrays.asList(player));

        vm.setState(state);
        PlayerStatsState result = vm.getState();

        assertEquals(1, result.getPlayers().size());
        assertEquals("Fake1", result.getPlayers().get(0).getName());
        assertEquals("Foo2", result.getPlayers().get(0).getProperties().get(0).getPropertyName());
    }
}