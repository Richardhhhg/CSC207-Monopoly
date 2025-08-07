package main.interface_adapter.start_screen;

import main.use_case.StartScreen.StartGame;

public class StartScreenController {
    private final StartGame startGameUseCase;

    public StartScreenController() {
        this.startGameUseCase = new StartGame();
    }

    /**
     * Executes the start-game use case and returns the result.
     *
     * @return a {@link StartGame.StartGameResult} containing game initialization data
     */
    public StartGame.StartGameResult execute() {
        return startGameUseCase.execute();
    }
}
