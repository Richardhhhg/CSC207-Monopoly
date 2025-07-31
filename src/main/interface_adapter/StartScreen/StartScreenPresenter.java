package main.interface_adapter.StartScreen;

import main.use_case.StartScreen.StartGame;

public class StartScreenPresenter {
    public StartScreenViewModel execute(StartGame.StartGameResult result) {
        return new StartScreenViewModel(
                result.getWelcomeMessage(),
                result.getRules(),
                "Start Game",
                "Rules"
        );
    }
}
