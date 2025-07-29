package main.use_case.dice;

/**
 * Input boundary for the roll‑dice use case.  Controllers depend on
 * this interface to request a dice roll.
 */
public interface RollDiceInputBoundary {
    void rollDice();
}
