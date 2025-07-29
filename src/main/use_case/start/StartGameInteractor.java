package main.use_case.start;

/**
 * Interactor for starting the game.
 */
public class StartGameInteractor implements StartGameInputBoundary {
    private final StartGameOutputBoundary outputBoundary;

    public StartGameInteractor(StartGameOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void startGame() {
        outputBoundary.launchGame();
    }
}
