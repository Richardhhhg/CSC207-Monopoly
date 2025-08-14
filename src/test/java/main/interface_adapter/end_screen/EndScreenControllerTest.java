package main.interface_adapter.end_screen;

import main.entity.players.AbstractPlayer;
import main.entity.players.Clerk;
import main.entity.players.CollegeStudent;
import main.use_case.end_screen.EndScreenInputBoundary;
import main.use_case.end_screen.EndScreenInputData;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class EndScreenControllerTest {

    private EndScreenController controller;
    private TestEndScreenInteractor testInteractor;
    private List<AbstractPlayer> testPlayers;

    @Before
    public void setUp() {
        testInteractor = new TestEndScreenInteractor();
        controller = new EndScreenController(testInteractor);

        // Create test players
        AbstractPlayer player1 = new Clerk("Alice", Color.RED);
        AbstractPlayer player2 = new CollegeStudent("Bob", Color.BLUE);
        player1.addMoney(1000);
        player2.addMoney(500);

        testPlayers = Arrays.asList(player1, player2);
    }

    @Test
    public void testCreateFactoryMethod() {
        EndScreenViewModel viewModel = new EndScreenViewModel();
        EndScreenController createdController = EndScreenController.create(viewModel);

        assertNotNull(createdController);
        // Test that the created controller works
        createdController.execute(testPlayers, "Factory test", 5);

        // The view model should be updated through the presenter
        assertEquals("GAME OVER", viewModel.getGameOverTitle());
        assertEquals("Factory test", viewModel.getGameEndReason());
    }

    @Test
    public void testExecuteCallsInteractorWithCorrectData() {
        String gameEndReason = "Maximum rounds reached";
        int totalRounds = 20;

        controller.execute(testPlayers, gameEndReason, totalRounds);

        assertTrue(testInteractor.wasExecuteCalled());
        EndScreenInputData inputData = testInteractor.getLastInputData();

        assertNotNull(inputData);
        assertEquals(testPlayers, inputData.getPlayers());
        assertEquals(gameEndReason, inputData.getGameEndReason());
        assertEquals(totalRounds, inputData.getTotalRounds());
    }

    @Test
    public void testExecuteWithBankruptPlayer() {
        AbstractPlayer bankruptPlayer = new CollegeStudent("Charlie", Color.GREEN);
        bankruptPlayer.deductMoney(bankruptPlayer.getMoney() + 100); // Make bankrupt
        List<AbstractPlayer> playersWithBankrupt = Arrays.asList(testPlayers.get(0), testPlayers.get(1), bankruptPlayer);

        controller.execute(playersWithBankrupt, "Player bankruptcy", 15);

        assertTrue(testInteractor.wasExecuteCalled());
        EndScreenInputData inputData = testInteractor.getLastInputData();

        assertEquals(3, inputData.getPlayers().size());
        assertEquals("Player bankruptcy", inputData.getGameEndReason());
        assertEquals(15, inputData.getTotalRounds());

        // Verify bankrupt player is included
        boolean foundBankruptPlayer = false;
        for (AbstractPlayer player : inputData.getPlayers()) {
            if (player.isBankrupt()) {
                foundBankruptPlayer = true;
                break;
            }
        }
        assertTrue(foundBankruptPlayer);
    }

    @Test
    public void testExecuteWithEmptyPlayerList() {
        List<AbstractPlayer> emptyPlayers = new ArrayList<>();

        controller.execute(emptyPlayers, "No players", 0);

        assertTrue(testInteractor.wasExecuteCalled());
        EndScreenInputData inputData = testInteractor.getLastInputData();

        assertTrue(inputData.getPlayers().isEmpty());
        assertEquals("No players", inputData.getGameEndReason());
        assertEquals(0, inputData.getTotalRounds());
    }

    @Test
    public void testExecuteWithSinglePlayer() {
        AbstractPlayer singlePlayer = new Clerk("Solo", Color.YELLOW);
        singlePlayer.addMoney(2000);
        List<AbstractPlayer> singlePlayerList = Arrays.asList(singlePlayer);

        controller.execute(singlePlayerList, "Single player game", 10);

        assertTrue(testInteractor.wasExecuteCalled());
        EndScreenInputData inputData = testInteractor.getLastInputData();

        assertEquals(1, inputData.getPlayers().size());
        assertEquals("Single player game", inputData.getGameEndReason());
        assertEquals(10, inputData.getTotalRounds());
        assertEquals(singlePlayer, inputData.getPlayers().get(0));
    }

    @Test
    public void testExecuteWithZeroRounds() {
        controller.execute(testPlayers, "Immediate end", 0);

        assertTrue(testInteractor.wasExecuteCalled());
        EndScreenInputData inputData = testInteractor.getLastInputData();

        assertEquals(0, inputData.getTotalRounds());
        assertEquals("Immediate end", inputData.getGameEndReason());
    }

    @Test
    public void testExecuteWithNegativeRounds() {
        // Edge case: negative rounds (shouldn't happen in real game but test for robustness)
        controller.execute(testPlayers, "Error case", -5);

        assertTrue(testInteractor.wasExecuteCalled());
        EndScreenInputData inputData = testInteractor.getLastInputData();

        assertEquals(-5, inputData.getTotalRounds());
        assertEquals("Error case", inputData.getGameEndReason());
    }

    @Test
    public void testExecuteWithNullGameEndReason() {
        // Edge case: null reason
        controller.execute(testPlayers, null, 10);

        assertTrue(testInteractor.wasExecuteCalled());
        EndScreenInputData inputData = testInteractor.getLastInputData();

        assertNull(inputData.getGameEndReason());
        assertEquals(10, inputData.getTotalRounds());
    }

    @Test
    public void testExecuteWithEmptyGameEndReason() {
        controller.execute(testPlayers, "", 5);

        assertTrue(testInteractor.wasExecuteCalled());
        EndScreenInputData inputData = testInteractor.getLastInputData();

        assertEquals("", inputData.getGameEndReason());
        assertEquals(5, inputData.getTotalRounds());
    }

    @Test
    public void testExecuteWithLargeNumberOfRounds() {
        controller.execute(testPlayers, "Long game", 1000);

        assertTrue(testInteractor.wasExecuteCalled());
        EndScreenInputData inputData = testInteractor.getLastInputData();

        assertEquals(1000, inputData.getTotalRounds());
        assertEquals("Long game", inputData.getGameEndReason());
    }

    @Test
    public void testMultipleExecuteCalls() {
        // Test that multiple calls work correctly
        controller.execute(testPlayers, "First call", 5);
        assertTrue(testInteractor.wasExecuteCalled());

        // Reset and test second call
        testInteractor.reset();
        assertFalse(testInteractor.wasExecuteCalled());

        controller.execute(testPlayers, "Second call", 10);
        assertTrue(testInteractor.wasExecuteCalled());

        EndScreenInputData inputData = testInteractor.getLastInputData();
        assertEquals("Second call", inputData.getGameEndReason());
        assertEquals(10, inputData.getTotalRounds());
    }

    /**
     * Test interactor implementation that captures input data for verification.
     */
    private static class TestEndScreenInteractor implements EndScreenInputBoundary {
        private boolean executeCalled = false;
        private EndScreenInputData lastInputData;

        @Override
        public void execute(EndScreenInputData inputData) {
            this.executeCalled = true;
            this.lastInputData = inputData;
        }

        public boolean wasExecuteCalled() {
            return executeCalled;
        }

        public EndScreenInputData getLastInputData() {
            return lastInputData;
        }

        public void reset() {
            executeCalled = false;
            lastInputData = null;
        }
    }
}
