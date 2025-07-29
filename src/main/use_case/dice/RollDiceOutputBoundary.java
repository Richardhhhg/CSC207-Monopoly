package main.use_case.dice;

/**
 * Output boundary for the roll‑dice use case.  Interactors call this
 * interface to present the dice results.
 */
public interface RollDiceOutputBoundary {
    void presentDiceRoll(RollDiceResponseModel response);
}
