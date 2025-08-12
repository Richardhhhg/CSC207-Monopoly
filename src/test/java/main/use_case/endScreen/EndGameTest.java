package main.use_case.endScreen;

import main.entity.players.Player;
import main.entity.players.Clerk;
import main.entity.players.CollegeStudent;
import main.entity.tiles.PropertyTile;
import main.entity.Stocks.Stock;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class EndGameTest {

    private EndGame endGame;
    private List<Player> testPlayers;

    @Before
    public void setUp() {
        endGame = new EndGame();

        // Create test players with different wealth levels
        Player richPlayer = new Clerk("Rich", Color.RED);
        Player poorPlayer = new CollegeStudent("Poor", Color.BLUE);
        Player bankruptPlayer = new CollegeStudent("Bankrupt", Color.GREEN);

        richPlayer.addMoney(2000);
        poorPlayer.addMoney(500);
        bankruptPlayer.deductMoney(bankruptPlayer.getMoney() + 100); // Make bankrupt

        // Add properties to rich player
        PropertyTile expensiveProperty = new PropertyTile("Boardwalk", 400, 100);
        PropertyTile cheapProperty = new PropertyTile("Baltic Ave", 60, 10);
        expensiveProperty.setOwned(true, richPlayer);
        cheapProperty.setOwned(true, richPlayer);
        richPlayer.getProperties().add(expensiveProperty);
        richPlayer.getProperties().add(cheapProperty);

        // Add stocks to poor player
        Stock stock = new Stock("GOOGL", 100.0, 0.1, 0.2);
        Map<Stock, Integer> stocks = new HashMap<>();
        stocks.put(stock, 10);
        poorPlayer.getStocks().putAll(stocks);

        testPlayers = Arrays.asList(richPlayer, poorPlayer, bankruptPlayer);
    }

    @Test
    public void testExecuteReturnsCorrectResult() {
        EndGame.EndGameResult result = endGame.execute(testPlayers, "Test end", 15);

        assertNotNull(result);
        assertEquals("Test end", result.getGameEndReason());
        assertEquals(15, result.getTotalRounds());
        assertEquals(3, result.getPlayerResults().size());
    }

    @Test
    public void testPlayerRankingByNetWorth() {
        EndGame.EndGameResult result = endGame.execute(testPlayers, "Test end", 15);

        List<EndGame.PlayerResult> playerResults = result.getPlayerResults();

        // Should be sorted by net worth (descending)
        assertTrue(playerResults.get(0).getNetWorth() >= playerResults.get(1).getNetWorth());
        assertTrue(playerResults.get(1).getNetWorth() >= playerResults.get(2).getNetWorth());

        // Check ranks are assigned correctly
        assertEquals(1, playerResults.get(0).getRank());
        assertEquals(2, playerResults.get(1).getRank());
        assertEquals(3, playerResults.get(2).getRank());
    }

    @Test
    public void testWinnerDeterminationWithSolventPlayers() {
        EndGame.EndGameResult result = endGame.execute(testPlayers, "Test end", 15);

        assertNotNull(result.getWinner());
        // Winner should be the richest non-bankrupt player
        assertEquals("Rich", result.getWinner().getName());
    }

    @Test
    public void testWinnerDeterminationWithOneSolventPlayer() {
        // Make poor player bankrupt too
        testPlayers.get(1).deductMoney(testPlayers.get(1).getMoney() + 100);

        EndGame.EndGameResult result = endGame.execute(testPlayers, "Only one left", 10);

        assertNotNull(result.getWinner());
        assertEquals("Rich", result.getWinner().getName());
    }

    @Test
    public void testWinnerDeterminationWithAllBankrupt() {
        // Make all players bankrupt
        for (Player player : testPlayers) {
            player.deductMoney(player.getMoney() + 100);
        }

        EndGame.EndGameResult result = endGame.execute(testPlayers, "All bankrupt", 5);

        assertNull(result.getWinner());
    }

    @Test
    public void testPlayerResultCalculations() {
        EndGame.EndGameResult result = endGame.execute(testPlayers, "Test end", 15);

        // Find rich player's result
        EndGame.PlayerResult richResult = null;
        for (EndGame.PlayerResult pr : result.getPlayerResults()) {
            if (pr.getPlayer().getName().equals("Rich")) {
                richResult = pr;
                break;
            }
        }

        assertNotNull(richResult);
        // Rich player should have: 1200 (Clerk starting) + 2000 (added) = 3200
        assertEquals(3200f, richResult.getFinalCash(), 0.01f);
        assertEquals(460f, richResult.getTotalPropertyValue(), 0.01f); // 400 + 60
        assertEquals(0f, richResult.getTotalStockValue(), 0.01f);
        assertEquals(3660f, richResult.getNetWorth(), 0.01f);
    }

    @Test
    public void testStockValueCalculation() {
        EndGame.EndGameResult result = endGame.execute(testPlayers, "Test end", 15);

        // Find poor player's result (has stocks)
        EndGame.PlayerResult poorResult = null;
        for (EndGame.PlayerResult pr : result.getPlayerResults()) {
            if (pr.getPlayer().getName().equals("Poor")) {
                poorResult = pr;
                break;
            }
        }

        assertNotNull(poorResult);
        assertTrue(poorResult.getTotalStockValue() > 0);
        assertEquals(1500f, poorResult.getFinalCash(), 0.01f);
        assertEquals(0f, poorResult.getTotalPropertyValue(), 0.01f);
        // Stock value should be 10 shares * current stock price (around 100)
        // Allow for some variance due to stock price fluctuation
        assertTrue(poorResult.getTotalStockValue() > 500f);
    }

    @Test
    public void testBankruptPlayerHandling() {
        EndGame.EndGameResult result = endGame.execute(testPlayers, "Test end", 15);

        // Find bankrupt player's result
        EndGame.PlayerResult bankruptResult = null;
        for (EndGame.PlayerResult pr : result.getPlayerResults()) {
            if (pr.getPlayer().getName().equals("Bankrupt")) {
                bankruptResult = pr;
                break;
            }
        }

        assertNotNull(bankruptResult);
        assertTrue(bankruptResult.getPlayer().isBankrupt());
        assertEquals(0f, bankruptResult.getFinalCash(), 0.01f);
        assertEquals(0f, bankruptResult.getTotalPropertyValue(), 0.01f);
        assertEquals(0f, bankruptResult.getTotalStockValue(), 0.01f);
        assertEquals(0f, bankruptResult.getNetWorth(), 0.01f);
    }

    @Test
    public void testEmptyPlayerList() {
        List<Player> emptyPlayers = Arrays.asList();
        EndGame.EndGameResult result = endGame.execute(emptyPlayers, "No players", 0);

        assertNotNull(result);
        assertTrue(result.getPlayerResults().isEmpty());
        assertNull(result.getWinner());
        assertEquals("No players", result.getGameEndReason());
        assertEquals(0, result.getTotalRounds());
    }

    @Test
    public void testSinglePlayerGame() {
        Player singlePlayer = new Clerk("Solo", Color.YELLOW);
        singlePlayer.addMoney(1000);
        List<Player> singlePlayerList = Arrays.asList(singlePlayer);

        EndGame.EndGameResult result = endGame.execute(singlePlayerList, "Solo game", 5);

        assertNotNull(result);
        assertEquals(1, result.getPlayerResults().size());
        assertEquals(singlePlayer, result.getWinner());
        assertEquals(1, result.getPlayerResults().get(0).getRank());
    }

    @Test
    public void testPlayerResultGetters() {
        Player testPlayer = new Clerk("Test", Color.BLACK);
        testPlayer.addMoney(1000);

        EndGame.PlayerResult playerResult = new EndGame.PlayerResult(
                testPlayer, 1, 1000f, 200f, 300f, 1500f
        );

        assertEquals(testPlayer, playerResult.getPlayer());
        assertEquals(1, playerResult.getRank());
        assertEquals(1000f, playerResult.getFinalCash(), 0.01f);
        assertEquals(200f, playerResult.getTotalPropertyValue(), 0.01f);
        assertEquals(300f, playerResult.getTotalStockValue(), 0.01f);
        assertEquals(1500f, playerResult.getNetWorth(), 0.01f);
    }

    @Test
    public void testEndGameResultGetters() {
        Player winner = new Clerk("Winner", Color.RED);
        List<EndGame.PlayerResult> playerResults = Arrays.asList(
                new EndGame.PlayerResult(winner, 1, 1000f, 0f, 0f, 1000f)
        );

        EndGame.EndGameResult result = new EndGame.EndGameResult(
                playerResults, "Test game over", 10, winner
        );

        assertEquals(playerResults, result.getPlayerResults());
        assertEquals("Test game over", result.getGameEndReason());
        assertEquals(10, result.getTotalRounds());
        assertEquals(winner, result.getWinner());
    }
}