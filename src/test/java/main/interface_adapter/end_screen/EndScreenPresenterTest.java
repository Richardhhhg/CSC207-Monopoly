package main.interface_adapter.end_screen;

import main.entity.players.AbstractPlayer;
import main.entity.players.Clerk;
import main.entity.players.CollegeStudent;
import main.entity.tiles.PropertyTile;
import main.entity.stocks.Stock;
import main.use_case.end_screen.EndScreenOutputData;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class EndScreenPresenterTest {

    private EndScreenPresenter presenter;
    private EndScreenViewModel viewModel;
    private EndScreenOutputData testOutputData;
    private List<AbstractPlayer> testAbstractPlayers;

    @Before
    public void setUp() {
        viewModel = new EndScreenViewModel();
        presenter = new EndScreenPresenter(viewModel);

        // Create test players with different financial situations
        AbstractPlayer abstractPlayer1 = new Clerk("Alice", Color.RED);
        AbstractPlayer abstractPlayer2 = new CollegeStudent("Bob", Color.BLUE);

        abstractPlayer1.addMoney(1500);
        abstractPlayer2.addMoney(800);

        // Add a property to player1
        PropertyTile property = new PropertyTile("Park Place", 200, 50);
        property.setOwned(true, abstractPlayer1);
        abstractPlayer1.getProperties().add(property);

        // Add stocks to player2
        Stock stock = new Stock("AAPL", 150.0, 0.1, 0.2);
        Map<Stock, Integer> stockMap = new HashMap<>();
        stockMap.put(stock, 5);
        abstractPlayer2.getStocks().putAll(stockMap);

        testAbstractPlayers = Arrays.asList(abstractPlayer1, abstractPlayer2);

        // Create output data using the new format
        List<EndScreenOutputData.PlayerResult> playerResults = Arrays.asList(
                new EndScreenOutputData.PlayerResult(abstractPlayer1, 1, 1500f, 200f, 0f, 1700f),
                new EndScreenOutputData.PlayerResult(abstractPlayer2, 2, 800f, 0f, 750f, 1550f)
        );

        testOutputData = new EndScreenOutputData(
                playerResults,
                "Maximum rounds reached",
                20,
                abstractPlayer1
        );
    }

    @Test
    public void testPresentEndGameResultsUpdatesViewModel() {
        presenter.presentEndGameResults(testOutputData);

        assertEquals("GAME OVER", viewModel.getGameOverTitle());
        assertEquals("Maximum rounds reached", viewModel.getGameEndReason());
        assertEquals("Total Rounds Played: 19", viewModel.getTotalRoundsText());
        assertEquals("WINNER: Alice", viewModel.getWinnerText());
        assertEquals("New Game", viewModel.getNewGameButtonText());
        assertEquals("Exit", viewModel.getExitButtonText());
    }

    @Test
    public void testPresentEndGameResultsCreatesCorrectPlayerDisplayData() {
        presenter.presentEndGameResults(testOutputData);

        List<EndScreenViewModel.PlayerDisplayData> displayData = viewModel.getPlayerDisplayData();
        assertNotNull(displayData);
        assertEquals(2, displayData.size());

        // Check first player (winner)
        EndScreenViewModel.PlayerDisplayData firstPlayer = displayData.get(0);
        assertEquals("Alice", firstPlayer.getPlayer().getName());
        assertEquals("1500.00", firstPlayer.getMoneyText());
        assertEquals("200.00", firstPlayer.getPropertyValueText());
        assertEquals("0.00", firstPlayer.getStockValueText());
        assertEquals("1700.00", firstPlayer.getNetWorthText());
        assertEquals("SOLVENT", firstPlayer.getStatusText());
        assertEquals("#1 - Alice", firstPlayer.getRankText());

        // Check second player
        EndScreenViewModel.PlayerDisplayData secondPlayer = displayData.get(1);
        assertEquals("Bob", secondPlayer.getPlayer().getName());
        assertEquals("800.00", secondPlayer.getMoneyText());
        assertEquals("0.00", secondPlayer.getPropertyValueText());
        assertEquals("750.00", secondPlayer.getStockValueText());
        assertEquals("1550.00", secondPlayer.getNetWorthText());
        assertEquals("SOLVENT", secondPlayer.getStatusText());
        assertEquals("#2 - Bob", secondPlayer.getRankText());
    }

    @Test
    public void testPresentEndGameResultsWithBankruptPlayer() {
        AbstractPlayer bankruptAbstractPlayer = new CollegeStudent("Charlie", Color.GREEN);
        bankruptAbstractPlayer.deductMoney(bankruptAbstractPlayer.getMoney() + 100); // Make bankrupt

        List<EndScreenOutputData.PlayerResult> playerResults = Arrays.asList(
                new EndScreenOutputData.PlayerResult(testAbstractPlayers.get(0), 1, 1500f, 200f, 0f, 1700f),
                new EndScreenOutputData.PlayerResult(bankruptAbstractPlayer, 3, 0f, 0f, 0f, 0f),
                new EndScreenOutputData.PlayerResult(testAbstractPlayers.get(1), 2, 800f, 0f, 750f, 1550f)
        );

        EndScreenOutputData outputDataWithBankruptcy = new EndScreenOutputData(
                playerResults,
                "Player went bankrupt",
                15,
                testAbstractPlayers.get(0)
        );

        presenter.presentEndGameResults(outputDataWithBankruptcy);

        // Find bankrupt player in display data
        EndScreenViewModel.PlayerDisplayData bankruptData = null;
        for (EndScreenViewModel.PlayerDisplayData data : viewModel.getPlayerDisplayData()) {
            if (data.getPlayer().getName().equals("Charlie")) {
                bankruptData = data;
                break;
            }
        }

        assertNotNull(bankruptData);
        assertEquals("BANKRUPT", bankruptData.getStatusText());
        assertEquals("0.00", bankruptData.getMoneyText());
        assertEquals("0.00", bankruptData.getNetWorthText());
        assertEquals("#3 - Charlie", bankruptData.getRankText());
    }

    @Test
    public void testPresentEndGameResultsWithNoWinner() {
        EndScreenOutputData outputDataWithNoWinner = new EndScreenOutputData(
                testOutputData.getPlayerResults(),
                "All players bankrupt",
                10,
                null // No winner
        );

        presenter.presentEndGameResults(outputDataWithNoWinner);

        assertEquals("", viewModel.getWinnerText());
        assertEquals("All players bankrupt", viewModel.getGameEndReason());
        assertEquals("Total Rounds Played: 9", viewModel.getTotalRoundsText());
    }

    @Test
    public void testPresentEndGameResultsFormatsNumbersCorrectly() {
        // Test with unusual numbers to verify formatting
        AbstractPlayer testAbstractPlayer = new Clerk("Test", Color.BLACK);
        testAbstractPlayer.addMoney(1234.567f);

        List<EndScreenOutputData.PlayerResult> playerResults = Arrays.asList(
                new EndScreenOutputData.PlayerResult(testAbstractPlayer, 1, 1234.567f, 98.76f, 543.21f, 1876.537f)
        );

        EndScreenOutputData testOutputDataFormatting = new EndScreenOutputData(
                playerResults, "Test", 1, testAbstractPlayer
        );

        presenter.presentEndGameResults(testOutputDataFormatting);

        EndScreenViewModel.PlayerDisplayData playerData = viewModel.getPlayerDisplayData().get(0);

        assertEquals("1234.57", playerData.getMoneyText());
        assertEquals("98.76", playerData.getPropertyValueText());
        assertEquals("543.21", playerData.getStockValueText());
        assertEquals("1876.54", playerData.getNetWorthText());
    }

    @Test
    public void testPresentEndGameResultsWithEmptyPlayerList() {
        EndScreenOutputData emptyOutputData = new EndScreenOutputData(
                Arrays.asList(), // Empty player results
                "No players",
                0,
                null
        );

        presenter.presentEndGameResults(emptyOutputData);

        assertEquals("GAME OVER", viewModel.getGameOverTitle());
        assertEquals("No players", viewModel.getGameEndReason());
        assertEquals("Total Rounds Played: -1", viewModel.getTotalRoundsText());
        assertEquals("", viewModel.getWinnerText());
        assertTrue(viewModel.getPlayerDisplayData().isEmpty());
    }

    @Test
    public void testPresentEndGameResultsWithSinglePlayer() {
        AbstractPlayer singleAbstractPlayer = new Clerk("Solo", Color.YELLOW);
        singleAbstractPlayer.addMoney(1000);

        List<EndScreenOutputData.PlayerResult> singlePlayerResults = Arrays.asList(
                new EndScreenOutputData.PlayerResult(singleAbstractPlayer, 1, 1000f, 0f, 0f, 1000f)
        );

        EndScreenOutputData singlePlayerOutputData = new EndScreenOutputData(
                singlePlayerResults,
                "Single player game",
                5,
                singleAbstractPlayer
        );

        presenter.presentEndGameResults(singlePlayerOutputData);

        assertEquals("WINNER: Solo", viewModel.getWinnerText());
        assertEquals("Single player game", viewModel.getGameEndReason());
        assertEquals("Total Rounds Played: 4", viewModel.getTotalRoundsText());
        assertEquals(1, viewModel.getPlayerDisplayData().size());

        EndScreenViewModel.PlayerDisplayData soloData = viewModel.getPlayerDisplayData().get(0);
        assertEquals("#1 - Solo", soloData.getRankText());
        assertEquals("SOLVENT", soloData.getStatusText());
    }

    @Test
    public void testPresentEndGameResultsWithZeroValues() {
        AbstractPlayer zeroAbstractPlayer = new Clerk("Zero", Color.WHITE);
        zeroAbstractPlayer.deductMoney(zeroAbstractPlayer.getMoney()); // Remove all money but don't make bankrupt

        List<EndScreenOutputData.PlayerResult> zeroResults = Arrays.asList(
                new EndScreenOutputData.PlayerResult(zeroAbstractPlayer, 1, 0f, 0f, 0f, 0f)
        );

        EndScreenOutputData zeroOutputData = new EndScreenOutputData(
                zeroResults,
                "Zero test",
                1,
                zeroAbstractPlayer
        );

        presenter.presentEndGameResults(zeroOutputData);

        EndScreenViewModel.PlayerDisplayData zeroData = viewModel.getPlayerDisplayData().get(0);
        assertEquals("0.00", zeroData.getMoneyText());
        assertEquals("0.00", zeroData.getPropertyValueText());
        assertEquals("0.00", zeroData.getStockValueText());
        assertEquals("0.00", zeroData.getNetWorthText());
        // Player is not bankrupt, just has no money
        assertEquals("BANKRUPT", zeroData.getStatusText());
    }

    @Test
    public void testPresentEndGameResultsWithHighValues() {
        AbstractPlayer richAbstractPlayer = new Clerk("Rich", Color.RED);
        richAbstractPlayer.addMoney(999999.99f);

        List<EndScreenOutputData.PlayerResult> richResults = Arrays.asList(
                new EndScreenOutputData.PlayerResult(richAbstractPlayer, 1, 999999.99f, 500000.50f, 250000.25f, 1750000.74f)
        );

        EndScreenOutputData richOutputData = new EndScreenOutputData(
                richResults,
                "Rich test",
                100,
                richAbstractPlayer
        );

        presenter.presentEndGameResults(richOutputData);

        EndScreenViewModel.PlayerDisplayData richData = viewModel.getPlayerDisplayData().get(0);
        assertEquals("1000000.00", richData.getMoneyText());
        assertEquals("500000.50", richData.getPropertyValueText());
        assertEquals("250000.25", richData.getStockValueText());
        assertEquals("1750000.75", richData.getNetWorthText());
    }

    @Test
    public void testGetViewModel() {
        EndScreenViewModel retrievedViewModel = presenter.getViewModel();
        assertSame(viewModel, retrievedViewModel);
    }

    @Test
    public void testMultiplePresentCalls() {
        // Test that multiple calls to present work correctly
        presenter.presentEndGameResults(testOutputData);
        assertEquals("Maximum rounds reached", viewModel.getGameEndReason());
        assertEquals(2, viewModel.getPlayerDisplayData().size());

        // Create different output data
        AbstractPlayer newAbstractPlayer = new Clerk("New", Color.PINK);
        newAbstractPlayer.addMoney(500);

        List<EndScreenOutputData.PlayerResult> newResults = Arrays.asList(
                new EndScreenOutputData.PlayerResult(newAbstractPlayer, 1, 500f, 0f, 0f, 500f)
        );

        EndScreenOutputData newOutputData = new EndScreenOutputData(
                newResults,
                "New game end",
                7,
                newAbstractPlayer
        );

        // Present new data
        presenter.presentEndGameResults(newOutputData);

        // Verify view model was updated with new data
        assertEquals("New game end", viewModel.getGameEndReason());
        assertEquals("Total Rounds Played: 6", viewModel.getTotalRoundsText());
        assertEquals("WINNER: New", viewModel.getWinnerText());
        assertEquals(1, viewModel.getPlayerDisplayData().size());
        assertEquals("New", viewModel.getPlayerDisplayData().get(0).getPlayer().getName());
    }

    @Test
    public void testPresentEndGameResultsWithSpecialCharactersInNames() {
        AbstractPlayer specialAbstractPlayer = new Clerk("Sp3c!@l Ch4r$", Color.CYAN);
        specialAbstractPlayer.addMoney(1000);

        List<EndScreenOutputData.PlayerResult> specialResults = Arrays.asList(
                new EndScreenOutputData.PlayerResult(specialAbstractPlayer, 1, 1000f, 0f, 0f, 1000f)
        );

        EndScreenOutputData specialOutputData = new EndScreenOutputData(
                specialResults,
                "Special character test!@#$%",
                42,
                specialAbstractPlayer
        );

        presenter.presentEndGameResults(specialOutputData);

        assertEquals("WINNER: Sp3c!@l Ch4r$", viewModel.getWinnerText());
        assertEquals("Special character test!@#$%", viewModel.getGameEndReason());
        assertEquals("#1 - Sp3c!@l Ch4r$", viewModel.getPlayerDisplayData().get(0).getRankText());
    }
}
