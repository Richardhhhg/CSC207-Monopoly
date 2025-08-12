package main.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.Timer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class DiceAnimatorTest {
    private DiceAnimator diceAnimator;

    // Reflection helpers
    private int FRAME_LIMIT;
    private Method handleTimerEvent;
    private Field frameCountField;
    private Field diceTimerField;
    private Field currentResultField;
    private Field animationD1Field;
    private Field animationD2Field;

    @BeforeEach
    void setUp() throws Exception {
        diceAnimator = new DiceAnimator();

        // Discover private/static fields/methods
        FRAME_LIMIT = getStaticInt(DiceAnimator.class, "FRAME_LIMIT");

        handleTimerEvent = DiceAnimator.class.getDeclaredMethod("handleTimerEvent", Runnable.class, Runnable.class);
        handleTimerEvent.setAccessible(true);

        frameCountField = DiceAnimator.class.getDeclaredField("frameCount");
        frameCountField.setAccessible(true);

        diceTimerField = DiceAnimator.class.getDeclaredField("diceTimer");
        diceTimerField.setAccessible(true);

        currentResultField = DiceAnimator.class.getDeclaredField("currentResult");
        currentResultField.setAccessible(true);

        animationD1Field = DiceAnimator.class.getDeclaredField("animationD1");
        animationD1Field.setAccessible(true);

        animationD2Field = DiceAnimator.class.getDeclaredField("animationD2");
        animationD2Field.setAccessible(true);
    }

    private static int getStaticInt(Class<?> c, String name) throws Exception {
        Field f = c.getDeclaredField(name);
        f.setAccessible(true);
        return f.getInt(null);
    }

    private void setInt(Field f, Object target, int v) throws Exception { f.setInt(target, v); }
    private int getInt(Field f, Object target) throws Exception { return f.getInt(target); }
    private void set(Object target, Field f, Object v) throws Exception { f.set(target, v); }

    @Test
    void covers_initial_defaults_and_icons() {
        // Default branches before any animation
        assertEquals(2, diceAnimator.getLastDiceSum());
        assertEquals(1, diceAnimator.getFinalD1());
        assertEquals(1, diceAnimator.getFinalD2());

        // Icon slots 1..6 should exist (may be empty ImageIcons if resources absent, but non-null)
        for (int i = 1; i <= 6; i++) {
            assertNotNull(diceAnimator.getDiceIcon(i));
        }
    }

    @Test
    void covers_animating_branch_getters() throws Exception {
        // Prepare a real Swing Timer we can start/stop; we won't let it tick
        Timer t = new Timer(60_000, e -> {}); // long delay; no ticks during test
        set(diceAnimator, diceTimerField, t);

        // Simulate being in early animation frames: frameCount will be incremented inside handle call
        setInt(frameCountField, diceAnimator, 0);

        final boolean[] onFrame = {false};
        final boolean[] onDone  = {false};
        handleTimerEvent.invoke(diceAnimator,
                (Runnable) () -> onFrame[0] = true,
                (Runnable) () -> onDone[0] = true);

        assertTrue(onFrame[0], "onAnimationFrame should run in animating branch");
        assertFalse(onDone[0], "onComplete should not run yet");

        // Now mark timer as running to hit animating getter branches
        t.start();
        int d1 = diceAnimator.getFinalD1();
        int d2 = diceAnimator.getFinalD2();
        assertTrue(d1 >= 1 && d1 <= 6);
        assertTrue(d2 >= 1 && d2 <= 6);
        t.stop();
    }

    @Test
    void covers_finalization_branch_and_final_getters() throws Exception {
        // Need a Timer instance present (even if not started)
        Timer t = new Timer(60_000, e -> {});
        set(diceAnimator, diceTimerField, t);

        // Set frameCount so that after ++ it equals FRAME_LIMIT + 1
        setInt(frameCountField, diceAnimator, FRAME_LIMIT);

        final boolean[] onFrame = {false};
        final boolean[] onDone  = {false};
        handleTimerEvent.invoke(diceAnimator,
                (Runnable) () -> onFrame[0] = true,
                (Runnable) () -> onDone[0] = true);

        assertTrue(onFrame[0], "onAnimationFrame should run at finalization frame");
        assertFalse(onDone[0], "onComplete should not run at finalization frame");

        // Now currentResult should be set by presenter→use case
        Object currentResult = currentResultField.get(diceAnimator);
        assertNotNull(currentResult, "currentResult should be set at finalization");

        // With no running timer, getters should use the final result branch
        int d1 = diceAnimator.getFinalD1();
        int d2 = diceAnimator.getFinalD2();
        int sum = diceAnimator.getLastDiceSum();
        assertTrue(d1 >= 1 && d1 <= 6);
        assertTrue(d2 >= 1 && d2 <= 6);
        assertTrue(sum >= 2 && sum <= 12);
        assertEquals(d1 + d2, sum);
    }

    @Test
    void covers_completion_branch_and_callbacks() throws Exception {
        // Provide a Timer and set frameCount so that after ++ it goes to > FRAME_LIMIT + 1
        Timer t = new Timer(60_000, e -> {});
        set(diceAnimator, diceTimerField, t);
        setInt(frameCountField, diceAnimator, FRAME_LIMIT + 1);

        final boolean[] onFrame = {false};
        final boolean[] onDone  = {false};
        handleTimerEvent.invoke(diceAnimator,
                (Runnable) () -> onFrame[0] = true,
                (Runnable) () -> onDone[0] = true);

        assertFalse(onFrame[0], "No frame callback in completion branch");
        assertTrue(onDone[0], "Completion callback should run");
        // Timer.stop() was called — safe to call stop again
        t.stop();
    }

    @Test
    void covers_animating_getters_without_timer_ticks() throws Exception {
        // Explicitly set animating values and simulate "timer running" to hit first getter branch again
        Timer t = new Timer(60_000, e -> {});
        set(diceAnimator, diceTimerField, t);
        setInt(frameCountField, diceAnimator, Math.min(FRAME_LIMIT, 1));
        setInt(animationD1Field, diceAnimator, 5);
        setInt(animationD2Field, diceAnimator, 6);
        t.start();
        assertEquals(5, diceAnimator.getFinalD1());
        assertEquals(6, diceAnimator.getFinalD2());
        t.stop();
    }

    @Test
    void covers_startDiceAnimation_lines() throws Exception {
        // Call the public API (no need to let the timer tick)
        diceAnimator.startDiceAnimation(() -> {}, () -> {});

        // Grab the timer and stop it so the test is deterministic
        Timer t = (Timer) diceTimerField.get(diceAnimator);
        assertNotNull(t, "Timer should be created");
        assertTrue(t.isRunning(), "Timer should have been started");
        t.stop();
    }
}
