package main.use_case.CharacterSelectionScreen;

import main.entity.players.Player;

import java.util.List;

public interface CharacterSelectionScreenOutputBoundary {
    void launchGame(List<Player> players);
}