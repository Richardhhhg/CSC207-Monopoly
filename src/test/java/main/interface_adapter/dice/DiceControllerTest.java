package main.interface_adapter.dice;

import main.use_case.dice.RollDice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class DiceControllerTest {
    private DiceController diceController;

    @BeforeEach
    void setUp() {
        diceController = new DiceController();
    }

    @Test
    void testExecuteReturnsDiceResult() {
        RollDice.DiceResult result = diceController.execute();
        assertNotNull(result, "Controller should return a non-null result");
    }

    @Test
    void testExecuteReturnsValidDiceValues() {
        RollDice.DiceResult result = diceController.execute();

        assertTrue(result.getDice1() >= 1 && result.getDice1() <= 6,
                "Dice1 should be valid");
        assertTrue(result.getDice2() >= 1 && result.getDice2() <= 6,
                "Dice2 should be valid");
        assertEquals(result.getDice1() + result.getDice2(), result.getSum(),
                "Sum should be correct");
    }

    @Test
    void testMultipleExecutions() {
        // Test that controller can be called multiple times
        RollDice.DiceResult result1 = diceController.execute();
        RollDice.DiceResult result2 = diceController.execute();

        assertNotNull(result1, "First result should not be null");
        assertNotNull(result2, "Second result should not be null");

        // Both should be valid
        assertTrue(result1.getSum() >= 2 && result1.getSum() <= 12);
        assertTrue(result2.getSum() >= 2 && result2.getSum() <= 12);
    }
}