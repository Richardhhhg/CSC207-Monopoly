package main.use_case.endScreen;

import main.entity.players.Player;
import main.entity.players.Clerk;
import main.entity.players.CollegeStudent;
import main.entity.players.Landlord;
import main.entity.tiles.PropertyTile;
import main.entity.stocks.Stock;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class EndScreenInteractorTest {

    private TestEndScreenPresenter testPresenter;
    private EndScreenInteractor interactor;
    private List<Player> testPlayers;

    @Before
    public void setUp() {
        testPresenter = new TestEndScreenPresenter();
        interactor = new EndScreenInteractor(testPresenter);

        // Create test players with different financial situations
        Player player1 = new Clerk("Alice", Color.RED);
        Player player2 = new CollegeStudent("Bob", Color.BLUE);
        Player player3 = new Landlord("Charlie", Color.GREEN);

        player1.addMoney(1500);
        player2.addMoney(800);
        player3.addMoney(2000);

        testPlayers = Arrays.asList(player1, player2, player3);
    }

    @Test
    public void testExecuteWithBasicPlayers() {
        EndScreenInputData inputData = new EndScreenInputData(
                testPlayers, "Maximum rounds reached", 20
        );

        interactor.execute(inputData);

        // Verify presenter was called
        assertTrue(testPresenter.wasCalled());
        EndScreenOutputData outputData = testPresenter.getLastOutputData();

        assertNotNull(outputData);
        assertEquals("Maximum rounds reached", outputData.getGameEndReason());
        assertEquals(20, outputData.getTotalRounds());
        assertEquals(3, outputData.getPlayerResults().size());

        // Verify players are ranked by net worth (highest first)
        List<EndScreenOutputData.PlayerResult> results = outputData.getPlayerResults();
        assertEquals("Charlie", results.get(0).getPlayer().getName()); // Landlord with 2000
        assertEquals(1, results.get(0).getRank());
        assertEquals("Alice", results.get(1).getPlayer().getName());   // Clerk with 1500
        assertEquals(2, results.get(1).getRank());
        assertEquals("Bob", results.get(2).getPlayer().getName());     // Student with 800
        assertEquals(3, results.get(2).getRank());
    }

    @Test
    public void testExecuteWithPlayersOwningProperties() {
        Player playerWithProperty = testPlayers.get(0); // Alice
        PropertyTile property1 = new PropertyTile("Park Place", 200, 50);
        PropertyTile property2 = new PropertyTile("Boardwalk", 400, 100);

        property1.setOwned(true, playerWithProperty);
        property2.setOwned(true, playerWithProperty);
        playerWithProperty.getProperties().add(property1);
        playerWithProperty.getProperties().add(property2);

        EndScreenInputData inputData = new EndScreenInputData(
                testPlayers, "Game ended", 15
        );

        interactor.execute(inputData);

        EndScreenOutputData outputData = testPresenter.getLastOutputData();
        List<EndScreenOutputData.PlayerResult> results = outputData.getPlayerResults();

        // Alice should now be first due to property value (1500 cash + 600 property = 2100 total)
        EndScreenOutputData.PlayerResult aliceResult = null;
        for (EndScreenOutputData.PlayerResult result : results) {
            if (result.getPlayer().getName().equals("Alice")) {
                aliceResult = result;
                break;
            }
        }

        assertNotNull(aliceResult);
        assertEquals(1, aliceResult.getRank()); // Should be ranked #1 now
        assertEquals(2700f, aliceResult.getFinalCash(), 0.01f);
        assertEquals(600f, aliceResult.getTotalPropertyValue(), 0.01f); // 200 + 400
        assertEquals(0f, aliceResult.getTotalStockValue(), 0.01f);
        assertEquals(3300f, aliceResult.getNetWorth(), 0.01f); // 1500 + 600 + 0
    }

    @Test
    public void testExecuteWithPlayersOwningStocks() {
        Player playerWithStocks = testPlayers.get(1); // Bob
        Stock stock1 = new Stock("AAPL", 150.0, 0.1, 0.2);
        Stock stock2 = new Stock("GOOGL", 100.0, 0.05, 0.15);

        // Initialize stocks and add some quantities
        playerWithStocks.initializeStocks(Arrays.asList(stock1, stock2));
        playerWithStocks.getStocks().put(stock1, 5); // 5 * 150 = 750
        playerWithStocks.getStocks().put(stock2, 3); // 3 * 100 = 300

        EndScreenInputData inputData = new EndScreenInputData(
                testPlayers, "Test stocks", 10
        );

        interactor.execute(inputData);

        EndScreenOutputData outputData = testPresenter.getLastOutputData();
        List<EndScreenOutputData.PlayerResult> results = outputData.getPlayerResults();

        // Find Bob's result
        EndScreenOutputData.PlayerResult bobResult = null;
        for (EndScreenOutputData.PlayerResult result : results) {
            if (result.getPlayer().getName().equals("Bob")) {
                bobResult = result;
                break;
            }
        }

        assertNotNull(bobResult);
        assertEquals(1800f, bobResult.getFinalCash(), 0.01f);
        assertEquals(0f, bobResult.getTotalPropertyValue(), 0.01f);
        assertEquals(1050f, bobResult.getTotalStockValue(), 0.01f); // 750 + 300
        assertEquals(2850f, bobResult.getNetWorth(), 0.01f); // 800 + 0 + 1050
    }

    @Test
    public void testExecuteWithBankruptPlayers() {
        Player bankruptPlayer = testPlayers.get(2); // Charlie
        bankruptPlayer.deductMoney(bankruptPlayer.getMoney() + 100); // Make bankrupt

        EndScreenInputData inputData = new EndScreenInputData(
                testPlayers, "Player bankruptcy", 8
        );

        interactor.execute(inputData);

        EndScreenOutputData outputData = testPresenter.getLastOutputData();

        // Verify bankrupt player is not the winner
        assertNotNull(outputData.getWinner());
        assertNotEquals("Charlie", outputData.getWinner().getName());
        assertTrue(outputData.getWinner().getName().equals("Alice") ||
                outputData.getWinner().getName().equals("Bob"));

        // Find bankrupt player result
        EndScreenOutputData.PlayerResult bankruptResult = null;
        for (EndScreenOutputData.PlayerResult result : outputData.getPlayerResults()) {
            if (result.getPlayer().getName().equals("Charlie")) {
                bankruptResult = result;
                break;
            }
        }

        assertNotNull(bankruptResult);
        assertTrue(bankruptResult.getPlayer().isBankrupt());
        assertEquals(0f, bankruptResult.getFinalCash(), 0.01f);
    }

    @Test
    public void testExecuteWithAllPlayersBankrupt() {
        // Make all players bankrupt
        for (Player player : testPlayers) {
            player.deductMoney(player.getMoney() + 100);
        }

        EndScreenInputData inputData = new EndScreenInputData(
                testPlayers, "All players bankrupt", 5
        );

        interactor.execute(inputData);

        EndScreenOutputData outputData = testPresenter.getLastOutputData();

        // No winner should be determined
        assertNull(outputData.getWinner());
        assertEquals("All players bankrupt", outputData.getGameEndReason());
        assertEquals(5, outputData.getTotalRounds());

        // All players should have 0 cash
        for (EndScreenOutputData.PlayerResult result : outputData.getPlayerResults()) {
            assertTrue(result.getPlayer().isBankrupt());
            assertEquals(0f, result.getFinalCash(), 0.01f);
        }
    }

    @Test
    public void testExecuteWithSinglePlayer() {
        Player singlePlayer = new Clerk("Solo", Color.YELLOW);
        singlePlayer.addMoney(1000);
        List<Player> singlePlayerList = Arrays.asList(singlePlayer);

        EndScreenInputData inputData = new EndScreenInputData(
                singlePlayerList, "Single player test", 3
        );

        interactor.execute(inputData);

        EndScreenOutputData outputData = testPresenter.getLastOutputData();

        assertEquals(singlePlayer, outputData.getWinner());
        assertEquals(1, outputData.getPlayerResults().size());
        assertEquals(1, outputData.getPlayerResults().get(0).getRank());
        assertEquals("Solo", outputData.getPlayerResults().get(0).getPlayer().getName());
    }

    @Test
    public void testExecuteWithEmptyPlayerList() {
        List<Player> emptyPlayers = new ArrayList<>();

        EndScreenInputData inputData = new EndScreenInputData(
                emptyPlayers, "No players", 0
        );

        interactor.execute(inputData);

        EndScreenOutputData outputData = testPresenter.getLastOutputData();

        assertNull(outputData.getWinner());
        assertTrue(outputData.getPlayerResults().isEmpty());
        assertEquals("No players", outputData.getGameEndReason());
        assertEquals(0, outputData.getTotalRounds());
    }

    @Test
    public void testExecuteWithTiedNetWorth() {
        // Create two players with exactly the same net worth
        Player player1 = new Clerk("Player1", Color.RED);
        Player player2 = new Clerk("Player2", Color.BLUE);

        player1.addMoney(1000);
        player2.addMoney(800);

        // Give player2 a property to match player1's net worth
        PropertyTile property = new PropertyTile("Test Property", 200, 25);
        property.setOwned(true, player2);
        player2.getProperties().add(property);
        // player2 now has 800 cash + 200 property = 1000 total (same as player1)

        List<Player> tiedPlayers = Arrays.asList(player1, player2);

        EndScreenInputData inputData = new EndScreenInputData(
                tiedPlayers, "Tied game", 10
        );

        interactor.execute(inputData);

        EndScreenOutputData outputData = testPresenter.getLastOutputData();

        // Both players should have same net worth
        List<EndScreenOutputData.PlayerResult> results = outputData.getPlayerResults();
        assertEquals(2200f, results.get(0).getNetWorth(), 0.01f);
        assertEquals(2200f, results.get(1).getNetWorth(), 0.01f);

        // One should be winner (deterministic based on max() implementation)
        assertNotNull(outputData.getWinner());
    }

    @Test
    public void testExecuteWithComplexScenario() {
        // Create a complex scenario with properties, stocks, and bankruptcy
        Player richPlayer = new Landlord("Rich", Color.RED);
        Player poorPlayer = new CollegeStudent("Poor", Color.BLACK);
        Player bankruptPlayer = new Clerk("Bankrupt", Color.GRAY);

        richPlayer.addMoney(5000);
        poorPlayer.addMoney(100);
        bankruptPlayer.deductMoney(bankruptPlayer.getMoney() + 1); // Make bankrupt

        // Add properties to rich player
        PropertyTile expensiveProperty = new PropertyTile("Expensive Ave", 1000, 200);
        expensiveProperty.setOwned(true, richPlayer);
        richPlayer.getProperties().add(expensiveProperty);

        // Add stocks to poor player
        Stock cheapStock = new Stock("CHEAP", 10.0, 0.01, 0.02);
        poorPlayer.initializeStocks(Arrays.asList(cheapStock));
        poorPlayer.getStocks().put(cheapStock, 50); // 50 * 10 = 500

        List<Player> complexPlayers = Arrays.asList(richPlayer, poorPlayer, bankruptPlayer);

        EndScreenInputData inputData = new EndScreenInputData(
                complexPlayers, "Complex scenario", 25
        );

        interactor.execute(inputData);

        EndScreenOutputData outputData = testPresenter.getLastOutputData();

        // Rich player should be winner
        assertEquals("Rich", outputData.getWinner().getName());

        // Verify rankings
        List<EndScreenOutputData.PlayerResult> results = outputData.getPlayerResults();
        assertEquals("Rich", results.get(0).getPlayer().getName());
        assertEquals(1, results.get(0).getRank());
        assertEquals(6800f, results.get(0).getNetWorth(), 0.01f); // 5000 + 1000 + 0

        assertEquals("Poor", results.get(1).getPlayer().getName());
        assertEquals(2, results.get(1).getRank());
        assertEquals(1600f, results.get(1).getNetWorth(), 0.01f); // 100 + 0 + 500

        assertEquals("Bankrupt", results.get(2).getPlayer().getName());
        assertEquals(3, results.get(2).getRank());
        assertEquals(0f, results.get(2).getNetWorth(), 0.01f);
    }

    @Test
    public void testExecuteWithOnlyBankruptPlayersExceptOne() {
        Player survivor = testPlayers.get(0); // Alice
        testPlayers.get(1).deductMoney(testPlayers.get(1).getMoney() + 1); // Make Bob bankrupt
        testPlayers.get(2).deductMoney(testPlayers.get(2).getMoney() + 1); // Make Charlie bankrupt

        EndScreenInputData inputData = new EndScreenInputData(
                testPlayers, "Only one survivor", 12
        );

        interactor.execute(inputData);

        EndScreenOutputData outputData = testPresenter.getLastOutputData();

        // Alice should be the clear winner
        assertEquals("Alice", outputData.getWinner().getName());
        assertFalse(outputData.getWinner().isBankrupt());

        // Verify other players are bankrupt
        for (EndScreenOutputData.PlayerResult result : outputData.getPlayerResults()) {
            if (!result.getPlayer().getName().equals("Alice")) {
                assertTrue(result.getPlayer().isBankrupt());
            }
        }
    }

    /**
     * Test presenter implementation that captures the output data for verification.
     */
    private static class TestEndScreenPresenter implements EndScreenOutputBoundary {
        private boolean called = false;
        private EndScreenOutputData lastOutputData;

        @Override
        public void presentEndGameResults(EndScreenOutputData outputData) {
            this.called = true;
            this.lastOutputData = outputData;
        }

        public boolean wasCalled() {
            return called;
        }

        public EndScreenOutputData getLastOutputData() {
            return lastOutputData;
        }
    }
}
