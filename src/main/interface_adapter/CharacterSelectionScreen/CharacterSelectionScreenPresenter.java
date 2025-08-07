package main.interface_adapter.CharacterSelectionScreen;

import main.use_case.CharacterSelectionScreen.CharacterSelectionScreenOutputBoundary;

import java.util.List;

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

    public CharacterSelectionScreenViewModel getViewModel() {
        return viewModel;
    }
}
