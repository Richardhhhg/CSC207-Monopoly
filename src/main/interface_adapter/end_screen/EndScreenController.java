package main.interface_adapter.end_screen;

import java.util.List;

import main.entity.players.AbstractPlayer;

/**
 * Controller for the End Screen.
 * Handles user input and coordinates with the interactor through the input boundary.
 */
public class EndScreenController {
    private final main.use_case.end_screen.EndScreenInputBoundary interactor;

    /**
     * Constructs the controller with the given interactor.
     *
     * @param interactor The input boundary for the end screen use case.
     */
    public EndScreenController(main.use_case.end_screen.EndScreenInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the end game use case to determine the result of the game.
     *
     * @param abstractPlayers        the list of players participating in the game
     * @param gameEndReason  the reason the game ended
     * @param totalRounds    the total number of rounds played
     */
    public void execute(List<AbstractPlayer> abstractPlayers, String gameEndReason, int totalRounds) {
        final main.use_case.end_screen.EndScreenInputData inputData =
                new main.use_case.end_screen.EndScreenInputData(abstractPlayers, gameEndReason, totalRounds);
        interactor.execute(inputData);
    }
}
