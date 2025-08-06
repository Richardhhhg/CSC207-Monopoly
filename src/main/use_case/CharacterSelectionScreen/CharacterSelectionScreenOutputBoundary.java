package main.use_case.CharacterSelectionScreen;

import java.util.List;

public interface CharacterSelectionScreenOutputBoundary {
    void prepareLaunchData(List<PlayerOutputData> selectedPlayers);
}
