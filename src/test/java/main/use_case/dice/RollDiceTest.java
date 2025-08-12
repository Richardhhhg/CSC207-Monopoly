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

    // Tests for RollDice class
    @Test
    void testExecuteReturnsDiceResult() {
        RollDice.DiceResult result = rollDice.execute();
        assertNotNull(result, "Result should not be null");
    }

    @Test
    void testDiceValuesAreWithinValidRange() {
        RollDice.DiceResult result = rollDice.execute();

        assertTrue(result.getDice1() >= 1 && result.getDice1() <= 6,
                "Dice 1 should be between 1 and 6");
        assertTrue(result.getDice2() >= 1 && result.getDice2() <= 6,
                "Dice 2 should be between 1 and 6");
    }

    @Test
    void testSumIsCorrect() {
        RollDice.DiceResult result = rollDice.execute();
        assertEquals(result.getDice1() + result.getDice2(), result.getSum(),
                "Sum should equal dice1 + dice2");
    }

    @Test
    void testMultipleRollsProduceDifferentResults() {
        boolean foundDifferentResults = false;
        RollDice.DiceResult firstResult = rollDice.execute();

        for (int i = 0; i < 20; i++) {
            RollDice.DiceResult currentResult = rollDice.execute();
            if (currentResult.getSum() != firstResult.getSum()) {
                foundDifferentResults = true;
                break;
            }
        }

        assertTrue(foundDifferentResults,
                "Multiple rolls should produce different results (randomness check)");
    }

    // Tests for DiceResult inner class
    @Test
    void testDiceResultCreation() {
        RollDice.DiceResult result = new RollDice.DiceResult(3, 5, 8);

        assertEquals(3, result.getDice1(), "Dice1 should be 3");
        assertEquals(5, result.getDice2(), "Dice2 should be 5");
        assertEquals(8, result.getSum(), "Sum should be 8");
    }

    @Test
    void testDiceResultWithMinValues() {
        RollDice.DiceResult result = new RollDice.DiceResult(1, 1, 2);

        assertEquals(1, result.getDice1());
        assertEquals(1, result.getDice2());
        assertEquals(2, result.getSum());
    }

    @Test
    void testDiceResultWithMaxValues() {
        RollDice.DiceResult result = new RollDice.DiceResult(6, 6, 12);

        assertEquals(6, result.getDice1());
        assertEquals(6, result.getDice2());
        assertEquals(12, result.getSum());
    }
}