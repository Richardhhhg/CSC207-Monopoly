package main.interface_adapter.start;

import main.use_case.start.StartGameInputBoundary;

/**
 * Controller for the Start Game use case.
 */
public class StartGameController {
    private final StartGameInputBoundary interactor;

    public StartGameController(StartGameInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void startGame() {
        interactor.startGame();
    }
}
