package main.use_case.CharacterSelectionScreen;

import main.entity.players.Player;

public interface CharacterSelectionInputBoundary {
    void selectPlayer(int index, Player player);
    void confirmSelection();
    boolean canStartGame();
    Player selectPlayer(int index, String name, String type);
}
