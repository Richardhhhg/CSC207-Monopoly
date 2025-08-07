package main.interface_adapter.CharacterSelectionScreen;

import main.use_case.CharacterSelectionScreen.CharacterSelectionInputBoundary;
import main.use_case.CharacterSelectionScreen.CharacterSelectionInputData;

public class CharacterSelectionScreenController {
    private final CharacterSelectionInputBoundary interactor;

    public CharacterSelectionScreenController(CharacterSelectionInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void selectPlayer(int index, String name, String type) {
        CharacterSelectionInputData inputData = new CharacterSelectionInputData(index, name, type);
        interactor.selectPlayer(inputData);

    }

    public void confirmSelection() {
        this.interactor.confirmSelection();
    }

    public boolean canStartGame() {
        return this.interactor.canStartGame();
    }

}

