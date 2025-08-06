package main.interface_adapter.CharacterSelectionScreen;

import main.entity.Game;
import main.entity.players.Player;
import main.use_case.CharacterSelectionScreen.CharacterSelectionScreenOutputBoundary;
import main.use_case.CharacterSelectionScreen.GameLauncher;
import main.view.GameView;

import java.util.List;

// Not clean yet
public class CharacterSelectionScreenPresenter implements CharacterSelectionScreenOutputBoundary {
    private GameLaunchOutputData launchOutputData;

    @Override
    public void prepareLaunchData(List<String> names, List<String> types, List<String> colors) {
        this.launchOutputData = new GameLaunchOutputData(names, types, colors);
    }

    public GameLaunchOutputData getLaunchOutputData() {
        return launchOutputData;
    }
}