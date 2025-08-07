package main.interface_adapter.CharacterSelectionScreen;

import java.util.ArrayList;
import java.util.List;

public class CharacterSelectionScreenViewModel {

    private final List<PlayerOutputData> selectedPlayers = new ArrayList<>(4);

    public CharacterSelectionScreenViewModel() {
        for (int i = 0; i < 4; i++) {
            selectedPlayers.add(null);
        }
    }

    public void setPlayerData(int index, PlayerOutputData data) {
        selectedPlayers.set(index, data);
    }

    public PlayerOutputData getPlayerData(int index) {
        return selectedPlayers.get(index);
    }

    public List<PlayerOutputData> getAllPlayers() {
        return selectedPlayers;
    }
}
