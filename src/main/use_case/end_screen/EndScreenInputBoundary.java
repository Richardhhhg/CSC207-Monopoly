package main.use_case.end_screen;

/**
 * Input boundary for the End Screen use case.
 * This interface defines the methods that the controller can call to interact
 * with the End Screen business logic. It follows Clean Architecture principles
 * by separating input handling (controller) from application logic (interactor).
 */
public interface EndScreenInputBoundary {

    /**
     * Executes the end game use case to determine the result of the game.
     *
     * @param inputData The data required to process the end game scenario.
     */
    void execute(EndScreenInputData inputData);
}
