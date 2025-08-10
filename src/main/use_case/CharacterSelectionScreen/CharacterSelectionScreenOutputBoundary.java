package main.use_case.CharacterSelectionScreen;

import java.util.List;

public interface CharacterSelectionScreenOutputBoundary {
    /**
     * A preparedatalauch.
     * @param players a list of PlayerOutputData
     */
    void prepareLaunchData(List<PlayerOutputData> players);

    /**
     * A prepareplayer.
     * @param data PlayerOutputData
     * @param index index
     */
    void preparePlayer(PlayerOutputData data, int index);
}
