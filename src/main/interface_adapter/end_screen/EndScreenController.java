package main.interface_adapter.end_screen;

import java.util.List;

import main.entity.players.AbstractPlayer;
import main.use_case.end_screen.EndGame;

public class EndScreenController {
    private final EndGame endGameUseCase;

    /**
     * Creates an EndScreenController with a new EndGame use case.
     */
    public EndScreenController() {
        this.endGameUseCase = new EndGame();
    }

    /**
     * Executes the end game use case to determine the result of the game.
     *
     * @param players        the list of players participating in the game
     * @param gameEndReason  the reason the game ended
     * @param totalRounds    the total number of rounds played
     * @return the result of the end game evaluation
     */
    public EndGame.EndGameResult execute(List<AbstractPlayer> players, String gameEndReason, int totalRounds) {
        return endGameUseCase.execute(players, gameEndReason, totalRounds);
    }
}
