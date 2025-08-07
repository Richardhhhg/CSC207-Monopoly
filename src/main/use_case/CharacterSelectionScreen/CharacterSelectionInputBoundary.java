package main.use_case.CharacterSelectionScreen;

import main.entity.players.Player;

public interface CharacterSelectionInputBoundary {
    void execute(CharacterSelectionInputData inputData);
    void confirmSelection();
    boolean canStartGame();
    void selectPlayer(CharacterSelectionInputData inputData);
}
