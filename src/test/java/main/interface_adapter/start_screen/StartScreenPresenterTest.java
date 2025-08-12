package main.interface_adapter.start_screen;

import main.use_case.start_screen.StartGame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class StartScreenPresenterTest {
    private StartScreenPresenter presenter;

    @BeforeEach
    void setUp() {
        presenter = new StartScreenPresenter();
    }

    @Test
    void testExecuteTransformsStartGameResult() {
        String welcomeMessage = "Welcome to Test!";
        String rules = "Test rules content";
        StartGame.StartGameResult startGameResult = new StartGame.StartGameResult(welcomeMessage, rules);

        StartScreenViewModel viewModel = presenter.execute(startGameResult);

        assertNotNull(viewModel, "ViewModel should not be null");
        assertEquals(welcomeMessage, viewModel.getWelcomeMessage(),
                "Welcome message should match input");
        assertEquals(rules, viewModel.getRules(),
                "Rules should match input");
    }

    @Test
    void testExecuteSetsCorrectButtonTexts() {
        StartGame.StartGameResult startGameResult = new StartGame.StartGameResult("Welcome", "Rules");

        StartScreenViewModel viewModel = presenter.execute(startGameResult);

        assertEquals("Start Game", viewModel.getStartButtonText(),
                "Start button text should be 'Start Game'");
        assertEquals("Rules", viewModel.getRulesButtonText(),
                "Rules button text should be 'Rules'");
    }

    @Test
    void testExecuteWithNullValues() {
        StartGame.StartGameResult startGameResult = new StartGame.StartGameResult(null, null);

        StartScreenViewModel viewModel = presenter.execute(startGameResult);

        assertNotNull(viewModel, "ViewModel should not be null even with null inputs");
        assertNull(viewModel.getWelcomeMessage(), "Welcome message should be null");
        assertNull(viewModel.getRules(), "Rules should be null");
        assertEquals("Start Game", viewModel.getStartButtonText(),
                "Start button text should still be set");
        assertEquals("Rules", viewModel.getRulesButtonText(),
                "Rules button text should still be set");
    }

    @Test
    void testExecuteWithEmptyValues() {
        StartGame.StartGameResult startGameResult = new StartGame.StartGameResult("", "");

        StartScreenViewModel viewModel = presenter.execute(startGameResult);

        assertEquals("", viewModel.getWelcomeMessage(), "Welcome message should be empty");
        assertEquals("", viewModel.getRules(), "Rules should be empty");
        assertEquals("Start Game", viewModel.getStartButtonText());
        assertEquals("Rules", viewModel.getRulesButtonText());
    }

    @Test
    void testExecuteWithRealStartGameResult() {
        // Test with actual StartGame output
        StartGame startGame = new StartGame();
        StartGame.StartGameResult realResult = startGame.execute();

        StartScreenViewModel viewModel = presenter.execute(realResult);

        assertNotNull(viewModel);
        assertTrue(viewModel.getWelcomeMessage().contains("Welcome"));
        assertTrue(viewModel.getRules().contains("Objective"));
        assertEquals("Start Game", viewModel.getStartButtonText());
        assertEquals("Rules", viewModel.getRulesButtonText());
    }
}