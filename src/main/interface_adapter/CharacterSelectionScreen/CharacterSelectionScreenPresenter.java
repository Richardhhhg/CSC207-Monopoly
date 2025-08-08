package main.interface_adapter.CharacterSelectionScreen;

import java.util.List;

import main.use_case.CharacterSelectionScreen.CharacterSelectionScreenOutputBoundary;

public class CharacterSelectionScreenPresenter implements CharacterSelectionScreenOutputBoundary {
    private final CharacterSelectionScreenViewModel viewModel;

    public CharacterSelectionScreenPresenter(CharacterSelectionScreenViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareLaunchData(List<PlayerOutputData> players) {
        for (int i = 0; i < players.size(); i++) {
            viewModel.setPlayerData(i, players.get(i));
        }
    }

    @Override
    public void preparePlayer(PlayerOutputData data, int index) {

        viewModel.setPlayerData(index, data);
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @return stuff
     */
    public CharacterSelectionScreenViewModel getViewModel() {
        return viewModel;
    }
}
