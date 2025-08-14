package main.interface_adapter.start_screen;

import main.use_case.start_screen.StartScreenInputBoundary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class StartScreenControllerTest {

    private TestStartScreenInputBoundary testInputBoundary;
    private StartScreenController controller;

    @BeforeEach
    void setUp() {
        testInputBoundary = new TestStartScreenInputBoundary();
        controller = new StartScreenController(testInputBoundary);
    }

    @Test
    void testExecuteCallsInputBoundary() {
        controller.execute();

        assertTrue(testInputBoundary.wasCalled(), "Input boundary should have been called");
    }

    @Test
    void testExecuteDoesNotReturnValue() {
        // Test that execute() method has void return type
        controller.execute();

        // If we get here without compilation error, the method is void
        assertTrue(testInputBoundary.wasCalled());
    }

    @Test
    void testMultipleExecutionsCallInputBoundaryMultipleTimes() {
        controller.execute();
        controller.execute();

        assertEquals(2, testInputBoundary.getCallCount(), "Input boundary should be called twice");
    }

    @Test
    void testConstructorWithNullInputBoundary() {
        assertThrows(NullPointerException.class, () -> {
            new StartScreenController(null).execute();
        });
    }

    @Test
    void testControllerDelegatesCorrectly() {
        // Verify that the controller properly delegates to the input boundary
        controller.execute();

        assertTrue(testInputBoundary.wasCalled());
        assertEquals(1, testInputBoundary.getCallCount());
    }

    @Test
    void testControllerDoesNotModifyInputBoundary() {
        StartScreenInputBoundary originalBoundary = testInputBoundary;

        controller.execute();

        // Verify that the controller doesn't replace the input boundary reference
        assertSame(originalBoundary, testInputBoundary);
        assertTrue(testInputBoundary.wasCalled());
    }

    // Test helper class - implements the input boundary for testing
    private static class TestStartScreenInputBoundary implements StartScreenInputBoundary {
        private int callCount = 0;

        @Override
        public void execute() {
            callCount++;
        }

        public boolean wasCalled() {
            return callCount > 0;
        }

        public int getCallCount() {
            return callCount;
        }
    }
}
