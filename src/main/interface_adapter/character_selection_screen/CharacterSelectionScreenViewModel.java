package main.interface_adapter.character_selection_screen;

import java.util.ArrayList;
import java.util.List;

import main.use_case.character_selection_screen.CharacterSelectionPlayerViewModel;

/**
 * The ViewModel for the CharacterSelectionScreen.
 * Stores the state of selected players for the view.
 */
public class CharacterSelectionScreenViewModel {
    private static final int MAX_AMT_PLAYER = 4;
    private final List<CharacterSelectionPlayerViewModel> selectedPlayers = new ArrayList<>(4);

    /**
     * Constructs the ViewModel and initializes player slots.
     */
    public CharacterSelectionScreenViewModel() {
        for (int i = 0; i < MAX_AMT_PLAYER; i++) {
            selectedPlayers.add(null);
        }
    }

    /**
     * Sets the PlayerOutputData for a given player slot.
     *
     * @param index The player slot index.
     * @param data  The output data for the player.
     */
    public void setPlayerData(int index, CharacterSelectionPlayerViewModel data) {
        selectedPlayers.set(index, data);
    }

    /**
     * Gets the PlayerOutputData for a given player slot.
     *
     * @param index The player slot index.
     * @return The PlayerOutputData for that slot.
     */
    public CharacterSelectionPlayerViewModel getPlayerData(int index) {
        return selectedPlayers.get(index);
    }

    /**
     * Returns a copy of all players as CharacterSelectionPlayerViewModel.
     *
     * @return A list of CharacterSelectionPlayerViewModel.
     */
    public List<main.interface_adapter.character_selection_screen.CharacterSelectionPlayerViewModel> getAllPlayers() {
        final List<main.interface_adapter.character_selection_screen.CharacterSelectionPlayerViewModel> result =
                new ArrayList<>(MAX_AMT_PLAYER);
        for (CharacterSelectionPlayerViewModel d : selectedPlayers) {
            if (d == null) {
                result.add(null);
            }
            else {
                result.add(new main.interface_adapter.character_selection_screen.CharacterSelectionPlayerViewModel(
                        d.getName(),
                        d.getType(),
                        d.getColor(),
                        d.getPortrait()
                ));
            }
        }
        return result;
    }

    /**
     * Creates and returns a UI-friendly player view model for the given slot.
     * Converts the stored PlayerOutputData at the specified index
     * into a CharacterSelectionPlayerViewModel so the view layer
     * does not need to work with use case data objects directly.
     *
     * @param index the index of the player slot
     * @return a CharacterSelectionPlayerViewModel with the player's
     *         name, type, color, and portrait, or null if no player
     *         is set at that index
     * @throws IllegalArgumentException if no player data is set for the index
     */
    public main.interface_adapter.character_selection_screen.CharacterSelectionPlayerViewModel getPlayerVm(int index) {
        final CharacterSelectionPlayerViewModel data = selectedPlayers.get(index);
        if (data == null) {
            throw new IllegalArgumentException("No player data set for index " + index);
        }
        else {
            return new main.interface_adapter.character_selection_screen.CharacterSelectionPlayerViewModel(
                    data.getName(),
                    data.getType(),
                    data.getColor(),
                    data.getPortrait()
            );
        }
    }
}
