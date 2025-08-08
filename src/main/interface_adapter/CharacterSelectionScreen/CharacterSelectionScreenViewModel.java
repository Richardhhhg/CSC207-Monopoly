package main.interface_adapter.CharacterSelectionScreen;

import java.util.ArrayList;
import java.util.List;

public class CharacterSelectionScreenViewModel {
    private static final int MAX_AMT_PLAYER = 4;
    private final List<PlayerOutputData> selectedPlayers = new ArrayList<>(4);

    public CharacterSelectionScreenViewModel() {
        for (int i = 0; i < MAX_AMT_PLAYER; i++) {
            selectedPlayers.add(null);
        }
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @param data Data.
     * @param index Index.
     */
    public void setPlayerData(int index, PlayerOutputData data) {
        selectedPlayers.set(index, data);
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @param index Index.
     * @return PlayerOutputData.
     */
    public PlayerOutputData getPlayerData(int index) {
        return selectedPlayers.get(index);
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @return list of PlayerOutputData
     */
    public List<PlayerOutputData> getAllPlayers() {
        return selectedPlayers;
    }
}
