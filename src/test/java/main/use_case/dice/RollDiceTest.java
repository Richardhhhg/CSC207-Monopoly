package main.use_case.dice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class RollDiceTest {
    private RollDice rollDice;

    @BeforeEach
    void setUp() {
        rollDice = new RollDice();
    }

    @Test
    void testExecuteReturnsDiceResult() {
        RollDice.DiceResult result = rollDice.execute();
        assertNotNull(result, "Result should not be null");
    }

    @Test
    void testDiceValuesAreWithinValidRange() {
        RollDice.DiceResult result = rollDice.execute();

        assertTrue(result.getDice1() >= 1 && result.getDice1() <= 6,
                "Dice 1 should be between 1 and 6, got: " + result.getDice1());
        assertTrue(result.getDice2() >= 1 && result.getDice2() <= 6,
                "Dice 2 should be between 1 and 6, got: " + result.getDice2());
    }

    @Test
    void testSumIsCorrect() {
        RollDice.DiceResult result = rollDice.execute();
        int expectedSum = result.getDice1() + result.getDice2();
        assertEquals(expectedSum, result.getSum(),
                "Sum should equal dice1 + dice2");
    }

    @Test
    void testSumIsWithinValidRange() {
        RollDice.DiceResult result = rollDice.execute();
        assertTrue(result.getSum() >= 2 && result.getSum() <= 12,
                "Sum should be between 2 and 12, got: " + result.getSum());
    }

    @Test
    void testMultipleRolls() {
        // Test multiple rolls to ensure randomness works
        boolean foundDifferentResults = false;
        RollDice.DiceResult firstResult = rollDice.execute();

        // Try up to 20 rolls to find a different result
        for (int i = 0; i < 20; i++) {
            RollDice.DiceResult currentResult = rollDice.execute();
            if (currentResult.getDice1() != firstResult.getDice1() ||
                    currentResult.getDice2() != firstResult.getDice2()) {
                foundDifferentResults = true;
                break;
            }
        }

        assertTrue(foundDifferentResults,
                "Should get different results across multiple rolls (randomness check)");
    }
}