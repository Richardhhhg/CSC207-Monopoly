package main.interface_adapter.CharacterSelectionScreen;

import main.entity.players.Player;
import main.use_case.CharacterSelectionScreen.CharacterSelectionInputBoundary;

public class CharacterSelectionScreenController {
    private final CharacterSelectionInputBoundary interactor;

    public CharacterSelectionScreenController(CharacterSelectionInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void selectPlayer(int index, Player player) {
        this.interactor.selectPlayer(index, player);
    }

    public void confirmSelection() {
        this.interactor.confirmSelection();
    }

    public boolean canStartGame() {
        return this.interactor.canStartGame();
    }

    public Player selectPlayer(int index, String name, String type) {
        return interactor.selectPlayer(index, name, type);
    }

}

