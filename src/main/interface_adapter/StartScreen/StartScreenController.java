package main.interface_adapter.StartScreen;

import main.use_case.StartScreen.StartGame;

public class StartScreenController {
    private final StartGame startGameUseCase;

    public StartScreenController() {
        this.startGameUseCase = new StartGame();
    }

    public StartGame.StartGameResult execute() {
        return startGameUseCase.execute();
    }
}