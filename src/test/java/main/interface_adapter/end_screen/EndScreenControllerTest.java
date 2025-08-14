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
    private List<AbstractPlayer> testAbstractPlayers;

    @Before
    public void setUp() {
        testInteractor = new TestEndScreenInteractor();
        controller = new EndScreenController(testInteractor);

        // Create test players
        AbstractPlayer abstractPlayer1 = new Clerk("Alice", Color.RED);
        AbstractPlayer abstractPlayer2 = new CollegeStudent("Bob", Color.BLUE);
        abstractPlayer1.addMoney(1000);
        abstractPlayer2.addMoney(500);

        testAbstractPlayers = Arrays.asList(abstractPlayer1, abstractPlayer2);
    }

    @Test
    public void testExecuteCallsInteractorWithCorrectData() {
        String gameEndReason = "Maximum rounds reached";
        int totalRounds = 20;

        controller.execute(testAbstractPlayers, gameEndReason, totalRounds);

        assertTrue(testInteractor.wasExecuteCalled());
        EndScreenInputData inputData = testInteractor.getLastInputData();

        assertNotNull(inputData);
        assertEquals(testAbstractPlayers, inputData.getPlayers());
        assertEquals(gameEndReason, inputData.getGameEndReason());
        assertEquals(totalRounds, inputData.getTotalRounds());
    }

    @Test
    public void testExecuteWithBankruptPlayer() {
        AbstractPlayer bankruptAbstractPlayer = new CollegeStudent("Charlie", Color.GREEN);
        bankruptAbstractPlayer.deductMoney(bankruptAbstractPlayer.getMoney() + 100); // Make bankrupt
        List<AbstractPlayer> playersWithBankrupt = Arrays.asList(testAbstractPlayers.get(0), testAbstractPlayers.get(1), bankruptAbstractPlayer);

        controller.execute(playersWithBankrupt, "Player bankruptcy", 15);

        assertTrue(testInteractor.wasExecuteCalled());
        EndScreenInputData inputData = testInteractor.getLastInputData();

        assertEquals(3, inputData.getPlayers().size());
        assertEquals("Player bankruptcy", inputData.getGameEndReason());
        assertEquals(15, inputData.getTotalRounds());

        // Verify bankrupt player is included
        boolean foundBankruptPlayer = false;
        for (AbstractPlayer abstractPlayer : inputData.getPlayers()) {
            if (abstractPlayer.isBankrupt()) {
                foundBankruptPlayer = true;
                break;
            }
        }
        assertTrue(foundBankruptPlayer);
    }

    @Test
    public void testExecuteWithEmptyPlayerList() {
        List<AbstractPlayer> emptyAbstractPlayers = new ArrayList<>();

        controller.execute(emptyAbstractPlayers, "No players", 0);

        assertTrue(testInteractor.wasExecuteCalled());
        EndScreenInputData inputData = testInteractor.getLastInputData();

        assertTrue(inputData.getPlayers().isEmpty());
        assertEquals("No players", inputData.getGameEndReason());
        assertEquals(0, inputData.getTotalRounds());
    }

    @Test
    public void testExecuteWithSinglePlayer() {
        AbstractPlayer singleAbstractPlayer = new Clerk("Solo", Color.YELLOW);
        singleAbstractPlayer.addMoney(2000);
        List<AbstractPlayer> singleAbstractPlayerList = Arrays.asList(singleAbstractPlayer);

        controller.execute(singleAbstractPlayerList, "Single player game", 10);

        assertTrue(testInteractor.wasExecuteCalled());
        EndScreenInputData inputData = testInteractor.getLastInputData();

        assertEquals(1, inputData.getPlayers().size());
        assertEquals("Single player game", inputData.getGameEndReason());
        assertEquals(10, inputData.getTotalRounds());
        assertEquals(singleAbstractPlayer, inputData.getPlayers().get(0));
    }

    @Test
    public void testExecuteWithZeroRounds() {
        controller.execute(testAbstractPlayers, "Immediate end", 0);

        assertTrue(testInteractor.wasExecuteCalled());
        EndScreenInputData inputData = testInteractor.getLastInputData();

        assertEquals(0, inputData.getTotalRounds());
        assertEquals("Immediate end", inputData.getGameEndReason());
    }

    @Test
    public void testExecuteWithNegativeRounds() {
        // Edge case: negative rounds (shouldn't happen in real game but test for robustness)
        controller.execute(testAbstractPlayers, "Error case", -5);

        assertTrue(testInteractor.wasExecuteCalled());
        EndScreenInputData inputData = testInteractor.getLastInputData();

        assertEquals(-5, inputData.getTotalRounds());
        assertEquals("Error case", inputData.getGameEndReason());
    }

    @Test
    public void testExecuteWithNullGameEndReason() {
        // Edge case: null reason
        controller.execute(testAbstractPlayers, null, 10);

        assertTrue(testInteractor.wasExecuteCalled());
        EndScreenInputData inputData = testInteractor.getLastInputData();

        assertNull(inputData.getGameEndReason());
        assertEquals(10, inputData.getTotalRounds());
    }

    @Test
    public void testExecuteWithEmptyGameEndReason() {
        controller.execute(testAbstractPlayers, "", 5);

        assertTrue(testInteractor.wasExecuteCalled());
        EndScreenInputData inputData = testInteractor.getLastInputData();

        assertEquals("", inputData.getGameEndReason());
        assertEquals(5, inputData.getTotalRounds());
    }

    @Test
    public void testExecuteWithLargeNumberOfRounds() {
        controller.execute(testAbstractPlayers, "Long game", 1000);

        assertTrue(testInteractor.wasExecuteCalled());
        EndScreenInputData inputData = testInteractor.getLastInputData();

        assertEquals(1000, inputData.getTotalRounds());
        assertEquals("Long game", inputData.getGameEndReason());
    }

    @Test
    public void testMultipleExecuteCalls() {
        // Test that multiple calls work correctly
        controller.execute(testAbstractPlayers, "First call", 5);
        assertTrue(testInteractor.wasExecuteCalled());

        // Reset and test second call
        testInteractor.reset();
        assertFalse(testInteractor.wasExecuteCalled());

        controller.execute(testAbstractPlayers, "Second call", 10);
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
