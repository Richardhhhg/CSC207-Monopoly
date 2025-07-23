package main.interface_adapter;

/**
 * Observer interface for views to receive updates from view model
 */
public interface GameViewObserver {
    void onGameStateChanged();
    void onPlayerMovementStart(int playerIndex, int steps);
}