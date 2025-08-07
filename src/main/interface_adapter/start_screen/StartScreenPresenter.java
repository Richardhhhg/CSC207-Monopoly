package main.interface_adapter.start_screen;

import main.use_case.StartScreen.StartGame;

public class StartScreenPresenter {

    /**
     * Transforms the game-start result into a view model.
     *
     * @param result the result of the start-game use case, must not be null
     * @return a {@link StartScreenViewModel} populated with welcome message, rules, and button labels
     */
    public StartScreenViewModel execute(StartGame.StartGameResult result) {
        return new StartScreenViewModel(
                result.getWelcomeMessage(),
                result.getRules(),
                "Start Game",
                "Rules"
        );
    }
}
