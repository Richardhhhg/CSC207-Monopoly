package main.interface_adapter.dice;

import main.use_case.dice.DiceInputBoundary;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiceControllerTest {

    // Simple test double to count how many times execute() is called
    private static class FakeDiceInputBoundary implements DiceInputBoundary {
        int calls = 0;
        @Override
        public void execute() {
            calls++;
        }
    }

    @Test
    void execute_delegatesToUseCaseOncePerCall() {
        FakeDiceInputBoundary fake = new FakeDiceInputBoundary();

        DiceController controller = new DiceController(fake);

        // First call
        controller.execute();
        assertEquals(1, fake.calls, "Use case should be executed exactly once");

        // Second call (ensure no extra hidden behavior)
        controller.execute();
        assertEquals(2, fake.calls, "Use case should be executed exactly twice after two calls");
    }
}
