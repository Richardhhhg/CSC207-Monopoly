package main.interface_adapter.endScreen;

import main.entity.players.Player;
import main.entity.players.Clerk;
import main.entity.players.CollegeStudent;
import main.use_case.endScreen.EndGame;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class EndScreenControllerTest {

    private EndScreenController controller;
    private List<Player> testPlayers;

    @Before
    public void setUp() {
        controller = new EndScreenController();

        // Create test players
        Player player1 = new Clerk("Alice", Color.RED);
        Player player2 = new CollegeStudent("Bob", Color.BLUE);
        player1.addMoney(1000);
        player2.addMoney(500);

        testPlayers = Arrays.asList(player1, player2);
    }

    @Test
    public void testExecuteReturnsValidResult() {
        String gameEndReason = "Maximum rounds reached";
        int totalRounds = 20;

        EndGame.EndGameResult result = controller.execute(testPlayers, gameEndReason, totalRounds);

        assertNotNull(result);
        assertEquals(gameEndReason, result.getGameEndReason());
        assertEquals(totalRounds, result.getTotalRounds());
        assertNotNull(result.getPlayerResults());
        assertEquals(2, result.getPlayerResults().size());
    }

    @Test
    public void testExecuteWithBankruptPlayer() {
        Player bankruptPlayer = new CollegeStudent("Charlie", Color.GREEN);
        bankruptPlayer.deductMoney(bankruptPlayer.getMoney() + 100); // Make bankrupt
        List<Player> playersWithBankrupt = Arrays.asList(testPlayers.get(0), testPlayers.get(1), bankruptPlayer);

        EndGame.EndGameResult result = controller.execute(playersWithBankrupt, "Player bankruptcy", 15);

        assertNotNull(result);
        assertEquals(3, result.getPlayerResults().size());

        // Find the bankrupt player in results
        boolean foundBankruptPlayer = false;
        for (EndGame.PlayerResult pr : result.getPlayerResults()) {
            if (pr.getPlayer().isBankrupt()) {
                foundBankruptPlayer = true;
                break;
            }
        }
        assertTrue(foundBankruptPlayer);
    }

    @Test
    public void testExecuteWithEmptyPlayerList() {
        List<Player> emptyPlayers = Arrays.asList();

        EndGame.EndGameResult result = controller.execute(emptyPlayers, "No players", 0);

        assertNotNull(result);
        assertTrue(result.getPlayerResults().isEmpty());
        assertNull(result.getWinner());
    }

    @Test
    public void testExecuteWithSinglePlayer() {
        Player singlePlayer = new Clerk("Solo", Color.YELLOW);
        singlePlayer.addMoney(2000);
        List<Player> singlePlayerList = Arrays.asList(singlePlayer);

        EndGame.EndGameResult result = controller.execute(singlePlayerList, "Single player game", 10);

        assertNotNull(result);
        assertEquals(1, result.getPlayerResults().size());
        assertEquals(singlePlayer, result.getWinner());
        assertEquals(1, result.getPlayerResults().get(0).getRank());
    }
}