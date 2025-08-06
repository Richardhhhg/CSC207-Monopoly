package main.interface_adapter.CharacterSelectionScreen;

import main.entity.Game;

public class CharacterSelectionScreenViewModel {
    private final Game game;

    public CharacterSelectionScreenViewModel(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
