package main.use_case.CharacterSelectionScreen;

import main.interface_adapter.CharacterSelectionScreen.PlayerOutputData;

import java.util.List;

public interface CharacterSelectionScreenOutputBoundary {
    void prepareLaunchData(List<PlayerOutputData> players);
    void preparePlayer(PlayerOutputData data, int index);
}
