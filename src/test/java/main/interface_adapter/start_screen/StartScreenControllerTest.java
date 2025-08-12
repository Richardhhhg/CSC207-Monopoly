package main.interface_adapter.start_screen;

import main.use_case.start_screen.StartGame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class StartScreenControllerTest {
    private StartScreenController controller;

    @BeforeEach
    void setUp() {
        controller = new StartScreenController();
    }

    @Test
    void testExecuteReturnStartGameResult() {
        StartGame.StartGameResult result = controller.execute();
        assertNotNull(result, "Controller should return a non-null result");
    }

    @Test
    void testExecuteReturnsValidWelcomeMessage() {
        StartGame.StartGameResult result = controller.execute();
        assertNotNull(result.getWelcomeMessage(), "Welcome message should not be null");
        assertFalse(result.getWelcomeMessage().trim().isEmpty(),
                "Welcome message should not be empty");
    }

    @Test
    void testExecuteReturnsValidRules() {
        StartGame.StartGameResult result = controller.execute();
        assertNotNull(result.getRules(), "Rules should not be null");
        assertFalse(result.getRules().trim().isEmpty(),
                "Rules should not be empty");
    }

    @Test
    void testMultipleExecutions() {
        StartGame.StartGameResult result1 = controller.execute();
        StartGame.StartGameResult result2 = controller.execute();

        assertNotNull(result1, "First result should not be null");
        assertNotNull(result2, "Second result should not be null");

        // Results should be consistent
        assertEquals(result1.getWelcomeMessage(), result2.getWelcomeMessage(),
                "Multiple executions should return same welcome message");
        assertEquals(result1.getRules(), result2.getRules(),
                "Multiple executions should return same rules");
    }

    @Test
    void testControllerUsesStartGameUseCase() {
        // This test verifies that the controller properly delegates to the use case
        StartGame.StartGameResult result = controller.execute();

        // Verify the result has expected characteristics from StartGame use case
        assertTrue(result.getWelcomeMessage().contains("Welcome"));
        assertTrue(result.getRules().contains("Objective"));
    }
}