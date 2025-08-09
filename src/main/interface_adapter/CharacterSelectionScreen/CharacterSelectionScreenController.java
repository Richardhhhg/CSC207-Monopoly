package main.interface_adapter.CharacterSelectionScreen;

import main.use_case.CharacterSelectionScreen.CharacterSelectionInputBoundary;
import main.use_case.CharacterSelectionScreen.CharacterSelectionInputData;

public class CharacterSelectionScreenController {
    private final CharacterSelectionInputBoundary interactor;

    public CharacterSelectionScreenController(CharacterSelectionInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @param index index.
     * @param name name.
     * @param type type.
     */
    public void selectPlayer(int index, String name, String type) {
        CharacterSelectionInputData inputData = new CharacterSelectionInputData(index, name, type);
        interactor.selectPlayer(inputData);

    }

    /**
     * This class is a placeholder for constants used throughout the application.
     */
    public void confirmSelection() {
        this.interactor.confirmSelection();
    }

    /**
     * This class is a placeholder for constants used throughout the application.
     * @return stuff
     */
    public boolean canStartGame() {
        return this.interactor.canStartGame();
    }

}
