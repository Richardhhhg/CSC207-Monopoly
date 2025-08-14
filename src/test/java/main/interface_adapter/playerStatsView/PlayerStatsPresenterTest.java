package main.interface_adapter.playerStatsView;

import main.interface_adapter.player_stats.DisplayPlayer;
import main.interface_adapter.player_stats.PlayerStatsPresenter;
import main.interface_adapter.player_stats.PlayerStatsState;
import main.interface_adapter.player_stats.PlayerStatsViewModel;
import main.use_case.player_stats.PlayerStatsOutput;
import main.use_case.player_stats.PlayerStatsOutputData;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStatsPresenterTest {

    @Test
    void testPresentPlayerStatsSetsViewModelState() {
        PlayerStatsViewModel viewModel = new PlayerStatsViewModel();
        PlayerStatsPresenter presenter = new PlayerStatsPresenter(viewModel);

        PlayerStatsOutput player1 = new PlayerStatsOutput(
                "Alice", 1000f, false, Color.RED, null, Arrays.asList("Park Place", "Boardwalk"));
        PlayerStatsOutput player2 = new PlayerStatsOutput(
                "Bob", 200f, true, Color.BLUE, null, Collections.singletonList("Baltic Ave"));

        PlayerStatsOutputData outputData = new PlayerStatsOutputData();
        outputData.add(player1);
        outputData.add(player2);

        presenter.presentPlayerStats(outputData);

        PlayerStatsState state = viewModel.getState();
        assertEquals(2, state.getPlayers().size());

        DisplayPlayer dp1 = state.getPlayers().get(0);
        assertEquals("Alice", dp1.getName());
        assertEquals(1000f, dp1.getMoney());
        assertFalse(dp1.isBankrupt());
        assertEquals(Color.RED, dp1.getColor());
        assertEquals(2, dp1.getProperties().size());
        assertEquals("Park Place", dp1.getProperties().get(0).getPropertyName());
        assertEquals("Boardwalk", dp1.getProperties().get(1).getPropertyName());

        DisplayPlayer dp2 = state.getPlayers().get(1);
        assertEquals("Bob", dp2.getName());
        assertTrue(dp2.isBankrupt());
        assertEquals(1, dp2.getProperties().size());
        assertEquals("Baltic Ave", dp2.getProperties().get(0).getPropertyName());
    }
}
