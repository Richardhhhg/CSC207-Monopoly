package main.use_case.dice;

import main.constants.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceInteractorTest {

    private static class CapturingBoundary implements DiceOutputBoundary {
        int calls = 0;
        DiceOutputData last;
        @Override
        public void presentDiceResult(DiceOutputData outputData) {
            calls++;
            last = outputData;
        }
    }

    @Test
    void execute_emitsValidDiceValuesAndDelegatesOnce() {
        CapturingBoundary boundary = new CapturingBoundary();
        DiceInteractor interactor = new DiceInteractor(boundary);

        interactor.execute();

        assertEquals(1, boundary.calls, "presentDiceResult should be called exactly once");
        assertNotNull(boundary.last, "Output data should not be null");

        int d1 = boundary.last.getDice1();
        int d2 = boundary.last.getDice2();
        int sum = boundary.last.getSum();

        // Face bounds
        assertTrue(d1 >= 1 && d1 <= Constants.DICE_SIDES, "die 1 out of range");
        assertTrue(d2 >= 1 && d2 <= Constants.DICE_SIDES, "die 2 out of range");

        // Sum correctness and bounds
        assertEquals(d1 + d2, sum, "sum must equal dice1 + dice2");
        assertTrue(sum >= 2 && sum <= 2 * Constants.DICE_SIDES, "sum out of range");
    }

    @Test
    void execute_canBeCalledMultipleTimes() {
        CapturingBoundary boundary = new CapturingBoundary();
        DiceInteractor interactor = new DiceInteractor(boundary);

        interactor.execute();
        assertEquals(1, boundary.calls);
        DiceOutputData first = boundary.last;

        interactor.execute();
        assertEquals(2, boundary.calls);
        DiceOutputData second = boundary.last;

        // Ensure the second call produced (potentially) new data object
        assertNotSame(first, second, "Each call should produce a fresh output object");
    }
}
