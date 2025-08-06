package main.interface_adapter.CharacterSelectionScreen;

import main.entity.Game;
import main.entity.players.Player;
import main.use_case.CharacterSelectionScreen.CharacterSelectionScreenOutputBoundary;
import main.use_case.CharacterSelectionScreen.GameLauncher;
import main.view.GameView;

import java.util.List;

// Not clean yet
public class CharacterSelectionScreenPresenter implements CharacterSelectionScreenOutputBoundary {
    private final GameLauncher gameLauncher;

    public CharacterSelectionScreenPresenter(GameLauncher gameLauncher) {
        this.gameLauncher = gameLauncher;
    }

    @Override
    public void launchGame(List<Player> players) {
        Game game = gameLauncher.launch(players);
        GameView gameView = new GameView(game);
        gameView.setVisible(true);
    }
}