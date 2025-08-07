package main.interface_adapter.CharacterSelectionScreen;

import java.util.ArrayList;
import java.util.List;

public class CharacterSelectionState {

    private List<PlayerOutputData> selectedPlayers = new ArrayList<>();

    public List<PlayerOutputData> getSelectedPlayers() {
        return selectedPlayers;
    }

    public void setSelectedPlayers(List<PlayerOutputData> selectedPlayers) {
        this.selectedPlayers = selectedPlayers;
    }
}
