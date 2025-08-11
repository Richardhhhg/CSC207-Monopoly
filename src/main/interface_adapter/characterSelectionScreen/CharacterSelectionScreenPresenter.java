package main.interface_adapter.characterSelectionScreen;

import java.util.List;

import main.use_case.characterSelectionScreen.CharacterSelectionScreenOutputBoundary;
import main.use_case.characterSelectionScreen.CharacterSelectionPlayerViewModel;

/**
 * Presenter for CharacterSelectionScreen.
 * Updates the ViewModel in response to interactor/presenter output.
 */
public class CharacterSelectionScreenPresenter implements CharacterSelectionScreenOutputBoundary {
    private final CharacterSelectionScreenViewModel viewModel;

    /**
     * Constructs the presenter with the given ViewModel.
     *
     * @param viewModel The ViewModel for the character selection screen.
     */
    public CharacterSelectionScreenPresenter(CharacterSelectionScreenViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Prepares all player launch data for the start of the game.
     *
     * @param players The list of all selected players.
     */
    @Override
    public void prepareLaunchData(List<CharacterSelectionPlayerViewModel> players) {
        for (int i = 0; i < players.size(); i++) {
            viewModel.setPlayerData(i, players.get(i));
        }
    }

    /**
     * Updates the ViewModel for a single player slot.
     *
     * @param data  The output data for the player.
     * @param index The player slot index.
     */
    @Override
    public void preparePlayer(CharacterSelectionPlayerViewModel data, int index) {

        viewModel.setPlayerData(index, data);
    }

    /**
     * Returns the associated ViewModel.
     *
     * @return The ViewModel instance.
     */
    public CharacterSelectionScreenViewModel getViewModel() {
        return viewModel;
    }
}
