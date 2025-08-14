package main.use_case.character_selection_screen;

import java.util.List;

public interface CharacterSelectionScreenOutputBoundary {
    /**
     * A preparedatalauch.
     * @param players a list of PlayerOutputData
     */
    void prepareLaunchData(List<CharacterSelectionPlayerViewModel> players);

    /**
     * A prepareplayer.
     * @param data PlayerOutputData
     * @param index index
     */
    void preparePlayer(CharacterSelectionPlayerViewModel data, int index);
}
