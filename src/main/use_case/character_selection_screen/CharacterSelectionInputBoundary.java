package main.use_case.character_selection_screen;

/**
 * Input boundary for the Character Selection use case.
 * This interface defines the methods that the controller can call to interact
 * with the Character Selection business logic. It follows Clean Architecture principles
 * by separating input handling (controller) from application logic (interactor).
 */
public interface CharacterSelectionInputBoundary {

    /**
     * Sets a player into the selection list at the specified index.
     * This method is typically used when users are setting up their characters silently
     * @param inputData the character's index, name, and type.
     */
    void execute(CharacterSelectionInputData inputData);

    /**
     * Finalizes the character selection process and launches the game.
     * This should be called only when all necessary player slots have been filled.
     * The output boundary will be notified with the prepared data for the game start.
     */
    void confirmSelection();

    /**
     * Checks whether the current selection state meets the minimum
     * number of players required to start the game.
     * @return {@code true} if the game can start, otherwise {@code false}.
     */
    boolean canStartGame();
}
