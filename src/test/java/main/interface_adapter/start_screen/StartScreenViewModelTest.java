package main.interface_adapter.start_screen;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StartScreenViewModelTest {

    @Test
    void testStartScreenViewModelCreation() {
        String welcomeMessage = "Welcome to Test Game!";
        String rules = "Test rules";
        String startButtonText = "Start";
        String rulesButtonText = "Rules";

        StartScreenViewModel viewModel = new StartScreenViewModel(
                welcomeMessage, rules, startButtonText, rulesButtonText);

        assertEquals(welcomeMessage, viewModel.getWelcomeMessage());
        assertEquals(rules, viewModel.getRules());
        assertEquals(startButtonText, viewModel.getStartButtonText());
        assertEquals(rulesButtonText, viewModel.getRulesButtonText());
    }

    @Test
    void testGetters() {
        String welcomeMessage = "Welcome!";
        String rules = "Game rules";
        String startButtonText = "Begin";
        String rulesButtonText = "Instructions";

        StartScreenViewModel viewModel = new StartScreenViewModel(
                welcomeMessage, rules, startButtonText, rulesButtonText);

        assertEquals(welcomeMessage, viewModel.getWelcomeMessage());
        assertEquals(rules, viewModel.getRules());
        assertEquals(startButtonText, viewModel.getStartButtonText());
        assertEquals(rulesButtonText, viewModel.getRulesButtonText());
    }

    @Test
    void testStartScreenViewModelWithNullValues() {
        StartScreenViewModel viewModel = new StartScreenViewModel(null, null, null, null);

        assertNull(viewModel.getWelcomeMessage());
        assertNull(viewModel.getRules());
        assertNull(viewModel.getStartButtonText());
        assertNull(viewModel.getRulesButtonText());
    }

    @Test
    void testStartScreenViewModelWithEmptyStrings() {
        StartScreenViewModel viewModel = new StartScreenViewModel("", "", "", "");

        assertEquals("", viewModel.getWelcomeMessage());
        assertEquals("", viewModel.getRules());
        assertEquals("", viewModel.getStartButtonText());
        assertEquals("", viewModel.getRulesButtonText());
    }

    @Test
    void testStartScreenViewModelImmutability() {
        String welcomeMessage = "Welcome!";
        String rules = "Rules";
        String startButtonText = "Start";
        String rulesButtonText = "Rules";

        StartScreenViewModel viewModel = new StartScreenViewModel(
                welcomeMessage, rules, startButtonText, rulesButtonText);

        // Verify calling getters multiple times returns consistent results
        assertEquals(viewModel.getWelcomeMessage(), viewModel.getWelcomeMessage());
        assertEquals(viewModel.getRules(), viewModel.getRules());
        assertEquals(viewModel.getStartButtonText(), viewModel.getStartButtonText());
        assertEquals(viewModel.getRulesButtonText(), viewModel.getRulesButtonText());
    }

    @Test
    void testStartScreenViewModelWithLongContent() {
        String longWelcomeMessage = "Welcome to our amazing, fantastic, incredible Monopoly game!";
        String longRules = String.join("\n",
                "Rule 1: This is a long rule",
                "Rule 2: This is another long rule",
                "Rule 3: And yet another rule",
                "Rule 4: Rules can be very detailed");

        StartScreenViewModel viewModel = new StartScreenViewModel(
                longWelcomeMessage, longRules, "Start Game", "Show Rules");

        assertEquals(longWelcomeMessage, viewModel.getWelcomeMessage());
        assertEquals(longRules, viewModel.getRules());
        assertTrue(viewModel.getRules().contains("Rule 1"));
        assertTrue(viewModel.getRules().contains("Rule 4"));
    }
}