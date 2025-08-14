package main.interface_adapter.endScreen;

import java.util.List;

import main.entity.players.Player;

/**
 * Controller for the End Screen.
 * Handles user input and coordinates with the interactor through the input boundary.
 */
public class EndScreenController {
    private final main.use_case.endScreen.EndScreenInputBoundary interactor;

    /**
     * Constructs the controller with the given interactor.
     *
     * @param interactor The input boundary for the end screen use case.
     */
    public EndScreenController(main.use_case.endScreen.EndScreenInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the end game use case to determine the result of the game.
     *
     * @param players        the list of players participating in the game
     * @param gameEndReason  the reason the game ended
     * @param totalRounds    the total number of rounds played
     */
    public void execute(List<Player> players, String gameEndReason, int totalRounds) {
        final main.use_case.endScreen.EndScreenInputData inputData =
                new main.use_case.endScreen.EndScreenInputData(players, gameEndReason, totalRounds);
        interactor.execute(inputData);
    }
}
