package main.interface_adapter.CharacterSelectionScreen;

import java.util.List;

public class GameLaunchOutputData {
    private final List<String> playerNames;
    private final List<String> playerTypes;
    private final List<String> playerColors; // e.g., "RED", "BLUE"

    public GameLaunchOutputData(List<String> names, List<String> types, List<String> colors) {
        this.playerNames = names;
        this.playerTypes = types;
        this.playerColors = colors;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public List<String> getPlayerTypes() {
        return playerTypes;
    }

    public List<String> getPlayerColors() {
        return playerColors;
    }
}
