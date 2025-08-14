package main.interface_adapter.start_screen;

import main.use_case.start_screen.StartScreenOutputData;
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
    void testPresentStartScreenDataCreatesViewModel() {
        String welcomeMessage = "Welcome to Test!";
        String rules = "Test rules content";
        StartScreenOutputData outputData = new StartScreenOutputData(welcomeMessage, rules);

        presenter.presentStartScreenData(outputData);
        StartScreenViewModel viewModel = presenter.getViewModel();

        assertNotNull(viewModel, "ViewModel should not be null");
        assertEquals(welcomeMessage, viewModel.getWelcomeMessage(),
                "Welcome message should match output data");
        assertEquals(rules, viewModel.getRules(),
                "Rules should match output data");
    }

    @Test
    void testPresentStartScreenDataSetsCorrectButtonTexts() {
        StartScreenOutputData outputData = new StartScreenOutputData("Welcome", "Rules");

        presenter.presentStartScreenData(outputData);
        StartScreenViewModel viewModel = presenter.getViewModel();

        assertEquals("Start Game", viewModel.getStartButtonText(),
                "Start button text should be 'Start Game'");
        assertEquals("Rules", viewModel.getRulesButtonText(),
                "Rules button text should be 'Rules'");
    }

    @Test
    void testPresentStartScreenDataWithNullValues() {
        StartScreenOutputData outputData = new StartScreenOutputData(null, null);

        presenter.presentStartScreenData(outputData);
        StartScreenViewModel viewModel = presenter.getViewModel();

        assertNotNull(viewModel, "ViewModel should not be null even with null inputs");
        assertNull(viewModel.getWelcomeMessage(), "Welcome message should be null");
        assertNull(viewModel.getRules(), "Rules should be null");
        assertEquals("Start Game", viewModel.getStartButtonText(),
                "Start button text should still be set");
        assertEquals("Rules", viewModel.getRulesButtonText(),
                "Rules button text should still be set");
    }

    @Test
    void testPresentStartScreenDataWithEmptyValues() {
        StartScreenOutputData outputData = new StartScreenOutputData("", "");

        presenter.presentStartScreenData(outputData);
        StartScreenViewModel viewModel = presenter.getViewModel();

        assertEquals("", viewModel.getWelcomeMessage(), "Welcome message should be empty");
        assertEquals("", viewModel.getRules(), "Rules should be empty");
        assertEquals("Start Game", viewModel.getStartButtonText());
        assertEquals("Rules", viewModel.getRulesButtonText());
    }

    @Test
    void testGetViewModelBeforePresentReturnsNull() {
        StartScreenViewModel viewModel = presenter.getViewModel();

        assertNull(viewModel, "ViewModel should be null before presentStartScreenData is called");
    }

    @Test
    void testMultiplePresentCallsUpdateViewModel() {
        StartScreenOutputData outputData1 = new StartScreenOutputData("Welcome 1", "Rules 1");
        StartScreenOutputData outputData2 = new StartScreenOutputData("Welcome 2", "Rules 2");

        presenter.presentStartScreenData(outputData1);
        StartScreenViewModel viewModel1 = presenter.getViewModel();

        presenter.presentStartScreenData(outputData2);
        StartScreenViewModel viewModel2 = presenter.getViewModel();

        assertNotEquals(viewModel1.getWelcomeMessage(), viewModel2.getWelcomeMessage());
        assertEquals("Welcome 2", viewModel2.getWelcomeMessage());
        assertEquals("Rules 2", viewModel2.getRules());
    }

    @Test
    void testPresentStartScreenDataWithLongContent() {
        String longWelcomeMessage = "Welcome to our amazing, fantastic, incredible Monopoly game!";
        String longRules = String.join("\n",
                "Rule 1: This is a long rule",
                "Rule 2: This is another long rule",
                "Rule 3: And yet another rule",
                "Rule 4: Rules can be very detailed");

        StartScreenOutputData outputData = new StartScreenOutputData(longWelcomeMessage, longRules);

        presenter.presentStartScreenData(outputData);
        StartScreenViewModel viewModel = presenter.getViewModel();

        assertEquals(longWelcomeMessage, viewModel.getWelcomeMessage());
        assertEquals(longRules, viewModel.getRules());
        assertTrue(viewModel.getRules().contains("Rule 1"));
        assertTrue(viewModel.getRules().contains("Rule 4"));
    }

    @Test
    void testPresenterImplementsOutputBoundary() {
        assertTrue(main.use_case.start_screen.StartScreenOutputBoundary.class.isAssignableFrom(StartScreenPresenter.class),
                "StartScreenPresenter should implement StartScreenOutputBoundary");
    }
}
