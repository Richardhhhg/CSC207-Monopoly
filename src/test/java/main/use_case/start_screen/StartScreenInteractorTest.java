package main.use_case.start_screen;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class StartScreenInteractorTest {

    private TestStartScreenPresenter testPresenter;
    private StartScreenInteractor interactor;

    @BeforeEach
    void setUp() {
        testPresenter = new TestStartScreenPresenter();
        interactor = new StartScreenInteractor(testPresenter);
    }

    @Test
    void testExecuteCallsPresenter() {
        interactor.execute();

        assertTrue(testPresenter.wasCalled(), "Presenter should have been called");
        assertNotNull(testPresenter.getReceivedOutputData(), "Output data should not be null");
    }

    @Test
    void testExecutePassesCorrectWelcomeMessage() {
        interactor.execute();

        StartScreenOutputData outputData = testPresenter.getReceivedOutputData();
        assertEquals("Welcome to our Monopoly!", outputData.getWelcomeMessage());
    }

    @Test
    void testExecutePassesRulesWithKeyContent() {
        interactor.execute();

        StartScreenOutputData outputData = testPresenter.getReceivedOutputData();
        String rules = outputData.getRules();

        assertTrue(rules.contains("Objective"), "Rules should contain Objective section");
        assertTrue(rules.contains("Setup"), "Rules should contain Setup section");
        assertTrue(rules.contains("Turn Sequence"), "Rules should contain Turn Sequence section");
        assertTrue(rules.contains("Bankruptcy"), "Rules should contain Bankruptcy section");
        assertTrue(rules.contains("Stock Market"), "Rules should contain Stock Market section");
        assertTrue(rules.contains("Endgame"), "Rules should contain Endgame section");
    }

    @Test
    void testExecutePassesRulesWithGameMechanics() {
        interactor.execute();

        StartScreenOutputData outputData = testPresenter.getReceivedOutputData();
        String rules = outputData.getRules().toLowerCase();

        assertTrue(rules.contains("dice"), "Rules should mention dice");
        assertTrue(rules.contains("property"), "Rules should mention property");
        assertTrue(rules.contains("stock"), "Rules should mention stock");
        assertTrue(rules.contains("20 rounds"), "Rules should mention 20 rounds limit");
    }

    @Test
    void testConstructorWithNullPresenter() {
        assertThrows(NullPointerException.class, () -> {
            new StartScreenInteractor(null).execute();
        });
    }

    @Test
    void testMultipleExecutionsCallPresenterMultipleTimes() {
        interactor.execute();
        interactor.execute();

        assertEquals(2, testPresenter.getCallCount(), "Presenter should be called twice");
    }

    @Test
    void testExecuteCreatesOutputDataWithNonNullValues() {
        interactor.execute();

        StartScreenOutputData outputData = testPresenter.getReceivedOutputData();
        assertNotNull(outputData.getWelcomeMessage(), "Welcome message should not be null");
        assertNotNull(outputData.getRules(), "Rules should not be null");
    }

    @Test
    void testExecuteCreatesOutputDataWithNonEmptyValues() {
        interactor.execute();

        StartScreenOutputData outputData = testPresenter.getReceivedOutputData();
        assertFalse(outputData.getWelcomeMessage().trim().isEmpty(),
                "Welcome message should not be empty");
        assertFalse(outputData.getRules().trim().isEmpty(), "Rules should not be empty");
    }

    @Test
    void testWelcomeMessageContent() {
        interactor.execute();

        StartScreenOutputData outputData = testPresenter.getReceivedOutputData();
        String welcomeMessage = outputData.getWelcomeMessage();
        assertTrue(welcomeMessage.contains("Welcome"), "Welcome message should contain 'Welcome'");
        assertTrue(welcomeMessage.contains("Monopoly"), "Welcome message should contain 'Monopoly'");
    }

    @Test
    void testMultipleExecutionsReturnConsistentResults() {
        interactor.execute();
        StartScreenOutputData result1 = testPresenter.getReceivedOutputData();

        testPresenter.reset(); // Reset for second call
        interactor.execute();
        StartScreenOutputData result2 = testPresenter.getReceivedOutputData();

        assertEquals(result1.getWelcomeMessage(), result2.getWelcomeMessage(),
                "Multiple executions should return same welcome message");
        assertEquals(result1.getRules(), result2.getRules(),
                "Multiple executions should return same rules");
    }

    // Test helper class - implements the output boundary for testing
    private static class TestStartScreenPresenter implements StartScreenOutputBoundary {
        private StartScreenOutputData receivedOutputData;
        private int callCount = 0;

        @Override
        public void presentStartScreenData(StartScreenOutputData outputData) {
            this.receivedOutputData = outputData;
            this.callCount++;
        }

        public boolean wasCalled() {
            return callCount > 0;
        }

        public StartScreenOutputData getReceivedOutputData() {
            return receivedOutputData;
        }

        public int getCallCount() {
            return callCount;
        }

        public void reset() {
            this.receivedOutputData = null;
            this.callCount = 0;
        }
    }
}
