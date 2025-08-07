package main.use_case.CharacterSelectionScreen;

import java.util.List;

public class GameLaunchOutputData {
    private final List<String> playerNames;
    private final List<String> playerTypes;
    private final List<String> playerColors;

    public GameLaunchOutputData(List<String> playerNames, List<String> playerTypes, List<String> playerColors) {
        this.playerNames = playerNames;
        this.playerTypes = playerTypes;
        this.playerColors = playerColors;
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
