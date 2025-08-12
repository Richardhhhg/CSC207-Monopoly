package main.use_case.start_screen;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class StartGameTest {
    private StartGame startGame;

    @BeforeEach
    void setUp() {
        startGame = new StartGame();
    }

    // Tests for StartGame class
    @Test
    void testExecuteReturnsStartGameResult() {
        StartGame.StartGameResult result = startGame.execute();
        assertNotNull(result, "Result should not be null");
    }

    @Test
    void testWelcomeMessageIsNotEmpty() {
        StartGame.StartGameResult result = startGame.execute();
        assertNotNull(result.getWelcomeMessage(), "Welcome message should not be null");
        assertFalse(result.getWelcomeMessage().trim().isEmpty(),
                "Welcome message should not be empty");
    }

    @Test
    void testRulesAreNotEmpty() {
        StartGame.StartGameResult result = startGame.execute();
        assertNotNull(result.getRules(), "Rules should not be null");
        assertFalse(result.getRules().trim().isEmpty(),
                "Rules should not be empty");
    }

    @Test
    void testWelcomeMessageContent() {
        StartGame.StartGameResult result = startGame.execute();
        String welcomeMessage = result.getWelcomeMessage();
        assertTrue(welcomeMessage.contains("Welcome"),
                "Welcome message should contain 'Welcome'");
        assertTrue(welcomeMessage.contains("Monopoly"),
                "Welcome message should contain 'Monopoly'");
    }

    @Test
    void testRulesContainKeyGameElements() {
        StartGame.StartGameResult result = startGame.execute();
        String rules = result.getRules();

        // Check for key game concepts
        assertTrue(rules.contains("Objective"), "Rules should contain Objective section");
        assertTrue(rules.contains("Setup"), "Rules should contain Setup section");
        assertTrue(rules.contains("Turn Sequence"), "Rules should contain Turn Sequence section");
        assertTrue(rules.contains("Bankruptcy"), "Rules should contain Bankruptcy section");
        assertTrue(rules.contains("Stock Market"), "Rules should contain Stock Market section");
        assertTrue(rules.contains("Endgame"), "Rules should contain Endgame section");
    }

    @Test
    void testRulesContainGameMechanics() {
        StartGame.StartGameResult result = startGame.execute();
        String rules = result.getRules();

        // Check for specific game mechanics mentioned
        assertTrue(rules.toLowerCase().contains("dice"), "Rules should mention dice");
        assertTrue(rules.toLowerCase().contains("property"), "Rules should mention property");
        assertTrue(rules.toLowerCase().contains("stock"), "Rules should mention stock");
        assertTrue(rules.toLowerCase().contains("20 rounds"), "Rules should mention 20 rounds limit");
    }

    @Test
    void testMultipleExecutionsReturnConsistentResults() {
        StartGame.StartGameResult result1 = startGame.execute();
        StartGame.StartGameResult result2 = startGame.execute();

        assertEquals(result1.getWelcomeMessage(), result2.getWelcomeMessage(),
                "Multiple executions should return same welcome message");
        assertEquals(result1.getRules(), result2.getRules(),
                "Multiple executions should return same rules");
    }

    // Tests for StartGameResult inner class
    @Test
    void testStartGameResultCreation() {
        String welcomeMessage = "Welcome to Test Game!";
        String rules = "These are test rules.";

        StartGame.StartGameResult result = new StartGame.StartGameResult(welcomeMessage, rules);

        assertEquals(welcomeMessage, result.getWelcomeMessage(),
                "Welcome message should match constructor input");
        assertEquals(rules, result.getRules(),
                "Rules should match constructor input");
    }

    @Test
    void testStartGameResultWithNullValues() {
        StartGame.StartGameResult result = new StartGame.StartGameResult(null, null);

        assertNull(result.getWelcomeMessage(), "Welcome message should be null");
        assertNull(result.getRules(), "Rules should be null");
    }

    @Test
    void testStartGameResultWithEmptyValues() {
        StartGame.StartGameResult result = new StartGame.StartGameResult("", "");

        assertEquals("", result.getWelcomeMessage(), "Welcome message should be empty string");
        assertEquals("", result.getRules(), "Rules should be empty string");
    }

    @Test
    void testStartGameResultImmutability() {
        String originalWelcome = "Welcome!";
        String originalRules = "Rules text";

        StartGame.StartGameResult result = new StartGame.StartGameResult(originalWelcome, originalRules);

        // Verify getters return the same values
        assertEquals(originalWelcome, result.getWelcomeMessage());
        assertEquals(originalRules, result.getRules());

        // Verify calling getters multiple times returns consistent results
        assertEquals(result.getWelcomeMessage(), result.getWelcomeMessage());
        assertEquals(result.getRules(), result.getRules());
    }
}