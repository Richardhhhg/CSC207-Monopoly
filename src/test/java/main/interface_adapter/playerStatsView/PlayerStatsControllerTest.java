package main.interface_adapter.playerStatsView;

import main.entity.Game;
import main.interface_adapter.player_stats.PlayerStatsController;
import main.use_case.player_stats.PlayerStatsInputBoundary;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerStatsControllerTest {

    private PlayerStatsController controller;
    private StubInputBoundary stub;

    @Before
    public void setUp() {
        stub = new StubInputBoundary();
        controller = new PlayerStatsController(stub);
    }

    @Test
    public void execute_Game() {
        Game game = new Game();
        controller.execute(game);

        assertTrue("execute should be called", stub.called);
        assertSame("game instance should be forwarded unchanged", game, stub.lastGame);
    }

    private static class StubInputBoundary implements PlayerStatsInputBoundary {
        boolean called = false;
        Game lastGame;

        @Override
        public void execute(Game game) {
            called = true;
            lastGame = game;
        }
    }


}

