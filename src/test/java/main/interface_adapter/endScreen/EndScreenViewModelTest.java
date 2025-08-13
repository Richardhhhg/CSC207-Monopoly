package main.interface_adapter.endScreen;

import main.entity.players.Player;
import main.entity.players.Clerk;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class EndScreenViewModelTest {

    private EndScreenViewModel viewModel;
    private List<EndScreenViewModel.PlayerDisplayData> testDisplayData;

    @Before
    public void setUp() {
        Player testPlayer = new Clerk("TestPlayer", Color.RED);
        EndScreenViewModel.PlayerDisplayData playerData =
                new EndScreenViewModel.PlayerDisplayData(
                        testPlayer,
                        1,
                        "1000.00",   // moneyText
                        "400.00",    // propertyValueText
                        "300.00",    // stockValueText
                        "1700.00",   // netWorthText
                        "SOLVENT"    // statusText
                );

        testDisplayData = Arrays.asList(playerData);

        viewModel = new EndScreenViewModel(
                "GAME OVER",
                "Test end reason",
                "Total Rounds Played: 15",
                "WINNER: TestPlayer",
                testDisplayData,
                "New Game",
                "Exit"
        );
    }

    @Test
    public void testViewModelGetters() {
        assertEquals("GAME OVER", viewModel.getGameOverTitle());
        assertEquals("Test end reason", viewModel.getGameEndReason());
        assertEquals("Total Rounds Played: 15", viewModel.getTotalRoundsText());
        assertEquals("WINNER: TestPlayer", viewModel.getWinnerText());
        assertEquals("New Game", viewModel.getNewGameButtonText());
        assertEquals("Exit", viewModel.getExitButtonText());
        assertEquals(1, viewModel.getPlayerDisplayData().size());
    }

    @Test
    public void testPlayerDisplayDataGetters() {
        EndScreenViewModel.PlayerDisplayData playerData = testDisplayData.get(0);

        assertEquals("TestPlayer", playerData.getPlayer().getName());
        assertEquals("1000.00", playerData.getMoneyText());
        assertEquals("400.00", playerData.getPropertyValueText());
        assertEquals("300.00", playerData.getStockValueText());
        assertEquals("1700.00", playerData.getNetWorthText());
        assertEquals("SOLVENT", playerData.getStatusText());
        assertEquals("#1 - TestPlayer", playerData.getRankText());
    }

    @Test
    public void testPlayerDisplayDataConstructor() {
        Player player = new Clerk("Alice", Color.BLUE);
        EndScreenViewModel.PlayerDisplayData data =
                new EndScreenViewModel.PlayerDisplayData(
                        player,
                        2,
                        "500.00",   // moneyText
                        "100.00",   // propertyValueText
                        "200.00",   // stockValueText
                        "800.00",   // netWorthText
                        "BANKRUPT"  // statusText
                );

        assertEquals(player, data.getPlayer());
        assertEquals("500.00", data.getMoneyText());
        assertEquals("100.00", data.getPropertyValueText());
        assertEquals("200.00", data.getStockValueText());
        assertEquals("800.00", data.getNetWorthText());
        assertEquals("BANKRUPT", data.getStatusText());
        assertEquals("#2 - Alice", data.getRankText());
    }

    @Test
    public void testViewModelWithEmptyWinnerText() {
        EndScreenViewModel emptyWinnerViewModel = new EndScreenViewModel(
                "GAME OVER", "No winner", "Rounds: 10", "",
                testDisplayData, "New Game", "Exit"
        );
        assertEquals("", emptyWinnerViewModel.getWinnerText());
    }

    @Test
    public void testViewModelWithNullSafetyChecks() {
        Player nullColorPlayer = new Clerk("NullTest", null);
        EndScreenViewModel.PlayerDisplayData nullData =
                new EndScreenViewModel.PlayerDisplayData(
                        nullColorPlayer,
                        1,
                        "0.00",
                        "0.00",
                        "0.00",
                        "0.00",
                        "BANKRUPT"
                );

        assertNotNull(nullData.getPlayer());
        assertEquals("NullTest", nullData.getPlayer().getName());
        assertEquals("#1 - NullTest", nullData.getRankText());
    }
}
