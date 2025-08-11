package main.interface_adapter.characterSelectionScreen;

import main.use_case.characterSelectionScreen.CharacterSelectionInputBoundary;
import main.use_case.characterSelectionScreen.CharacterSelectionInputData;
import main.use_case.characterSelectionScreen.CharacterTraitsText;

/**
 * The controller for the CharacterSelectionScreen.
 * Handles user input and coordinates with the interactor.
 */
public class CharacterSelectionScreenController {
    private final CharacterSelectionInputBoundary interactor;
    private final CharacterTraitsText lore;

    /**
     * Constructs the controller with the given interactor.
     *
     * @param interactor The input boundary for character selection use case.
     */
    public CharacterSelectionScreenController(CharacterSelectionInputBoundary interactor) {
        this.interactor = interactor;
        this.lore = new CharacterTraitsText();
    }

    /**
     * Requests a player selection update.
     *
     * @param index The player slot index.
     * @param name  The player's name.
     * @param type  The character type.
     */
    public void selectPlayer(int index, String name, String type) {
        final CharacterSelectionInputData inputData = new CharacterSelectionInputData(index, name, type);
        interactor.execute(inputData);

    }

    /**
     * Confirms the current player selections.
     */
    public void confirmSelection() {
        this.interactor.confirmSelection();
    }

    /**
     * Checks if the game can be started based on current selections.
     * @return true if enough valid players have been selected, false otherwise.
     */
    public boolean canStartGame() {
        return this.interactor.canStartGame();
    }

    public String getCharacterTraitsText() {
        return lore.getText();
    }

}
