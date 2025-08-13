package main.interface_adapter.endScreen;

import main.entity.players.Player;
import main.entity.players.Clerk;
import main.entity.players.CollegeStudent;
import main.entity.tiles.PropertyTile;
import main.entity.Stocks.Stock;
import main.use_case.endScreen.EndGame;
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
    private EndGame.EndGameResult testResult;
    private List<Player> testPlayers;

    @Before
    public void setUp() {
        presenter = new EndScreenPresenter();

        // Create test players with different financial situations
        Player player1 = new Clerk("Alice", Color.RED);
        Player player2 = new CollegeStudent("Bob", Color.BLUE);

        player1.addMoney(1500);
        player2.addMoney(800);

        // Add a property to player1
        PropertyTile property = new PropertyTile("Park Place", 200, 50);
        property.setOwned(true, player1);
        player1.getProperties().add(property);

        // Add stocks to player2
        Stock stock = new Stock("AAPL", 150.0, 0.1, 0.2);
        Map<Stock, Integer> stockMap = new HashMap<>();
        stockMap.put(stock, 5);
        player2.getStocks().putAll(stockMap);

        testPlayers = Arrays.asList(player1, player2);

        // Create mock EndGameResult
        List<EndGame.PlayerResult> playerResults = Arrays.asList(
                new EndGame.PlayerResult(player1, 1, 1500f, 200f, 0f, 1700f),
                new EndGame.PlayerResult(player2, 2, 800f, 0f, 750f, 1550f)
        );

        testResult = new EndGame.EndGameResult(
                playerResults,
                "Maximum rounds reached",
                20,
                player1
        );
    }

    @Test
    public void testExecuteCreatesValidViewModel() {
        EndScreenViewModel viewModel = presenter.execute(testResult);

        assertNotNull(viewModel);
        assertEquals("GAME OVER", viewModel.getGameOverTitle());
        assertEquals("Maximum rounds reached", viewModel.getGameEndReason());
        assertEquals("Total Rounds Played: 20", viewModel.getTotalRoundsText());
        assertEquals("WINNER: Alice", viewModel.getWinnerText());
        assertEquals("New Game", viewModel.getNewGameButtonText());
        assertEquals("Exit", viewModel.getExitButtonText());
    }

    @Test
    public void testExecuteCreatesCorrectPlayerDisplayData() {
        EndScreenViewModel viewModel = presenter.execute(testResult);

        List<EndScreenViewModel.PlayerDisplayData> displayData = viewModel.getPlayerDisplayData();
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
    public void testExecuteWithBankruptPlayer() {
        Player bankruptPlayer = new CollegeStudent("Charlie", Color.GREEN);
        bankruptPlayer.deductMoney(bankruptPlayer.getMoney() + 100); // Make bankrupt

        List<EndGame.PlayerResult> playerResults = Arrays.asList(
                new EndGame.PlayerResult(testPlayers.get(0), 1, 1500f, 200f, 0f, 1700f),
                new EndGame.PlayerResult(bankruptPlayer, 3, 0f, 0f, 0f, 0f),
                new EndGame.PlayerResult(testPlayers.get(1), 2, 800f, 0f, 750f, 1550f)
        );

        EndGame.EndGameResult resultWithBankruptcy = new EndGame.EndGameResult(
                playerResults,
                "Player went bankrupt",
                15,
                testPlayers.get(0)
        );

        EndScreenViewModel viewModel = presenter.execute(resultWithBankruptcy);

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
    }

    @Test
    public void testExecuteWithNoWinner() {
        EndGame.EndGameResult resultWithNoWinner = new EndGame.EndGameResult(
                testResult.getPlayerResults(),
                "All players bankrupt",
                10,
                null // No winner
        );

        EndScreenViewModel viewModel = presenter.execute(resultWithNoWinner);

        assertEquals("", viewModel.getWinnerText());
        assertEquals("All players bankrupt", viewModel.getGameEndReason());
    }

    @Test
    public void testExecuteFormatsNumbersCorrectly() {
        // Test with unusual numbers to verify formatting
        Player testPlayer = new Clerk("Test", Color.BLACK);
        testPlayer.addMoney(1234.567f);

        List<EndGame.PlayerResult> playerResults = Arrays.asList(
                new EndGame.PlayerResult(testPlayer, 1, 1234.567f, 98.76f, 543.21f, 1876.537f)
        );

        EndGame.EndGameResult testResultFormatting = new EndGame.EndGameResult(
                playerResults, "Test", 1, testPlayer
        );

        EndScreenViewModel viewModel = presenter.execute(testResultFormatting);
        EndScreenViewModel.PlayerDisplayData playerData = viewModel.getPlayerDisplayData().get(0);

        assertEquals("1234.57", playerData.getMoneyText());
        assertEquals("98.76", playerData.getPropertyValueText());
        assertEquals("543.21", playerData.getStockValueText());
        assertEquals("1876.54", playerData.getNetWorthText());
    }
}