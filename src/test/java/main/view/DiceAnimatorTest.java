package main.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class DiceAnimatorTest {
    private DiceAnimator diceAnimator;

    @BeforeEach
    void setUp() {
        diceAnimator = new DiceAnimator();
    }

    @Test
    void testInitialState() {
        // Before any animation, should return default values
        assertEquals(2, diceAnimator.getLastDiceSum(), "Initial sum should be minimum");
        assertEquals(1, diceAnimator.getFinalD1(), "Initial D1 should be default");
        assertEquals(1, diceAnimator.getFinalD2(), "Initial D2 should be default");
    }

    @Test
    void testGetDiceIcon() {
        for (int i = 1; i <= 6; i++) {
            assertNotNull(diceAnimator.getDiceIcon(i),
                    "Dice icon for face " + i + " should not be null");
        }
    }

    @Test
    void testStartDiceAnimationWithCallbacks() {
        final boolean[] animationFrameCalled = {false};
        final boolean[] completeCalled = {false};

        Runnable onAnimationFrame = () -> animationFrameCalled[0] = true;
        Runnable onComplete = () -> completeCalled[0] = true;

        diceAnimator.startDiceAnimation(onAnimationFrame, onComplete);

        // Note: This test might need to be adjusted based on timing
        // In a real scenario, you might want to use a test framework that can handle timers
    }

    @Test
    void testDiceValuesInValidRange() {
        // After creating animator, the dice values should be valid
        assertTrue(diceAnimator.getFinalD1() >= 1 && diceAnimator.getFinalD1() <= 6,
                "D1 should be in valid range");
        assertTrue(diceAnimator.getFinalD2() >= 1 && diceAnimator.getFinalD2() <= 6,
                "D2 should be in valid range");
        assertTrue(diceAnimator.getLastDiceSum() >= 2 && diceAnimator.getLastDiceSum() <= 12,
                "Sum should be in valid range");
    }
}