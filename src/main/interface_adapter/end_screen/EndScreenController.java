package main.interface_adapter.end_screen;

import java.util.List;

import main.entity.players.Player;
import main.use_case.end_screen.EndGame;

/**
 * Controller for the end-screen use case.
 * Delegates to {@link EndGame} to compute final standings and summary
 * based on the list of players, the reason the game ended, and rounds played.
 */
public class EndScreenController {

    /** The use case responsible for processing end-of-game logic. */
    private final EndGame endGameUseCase;

    /**
     * Constructs a new EndScreenController with its own {@link EndGame} use case.
     */
    public EndScreenController() {
        this.endGameUseCase = new EndGame();
    }

    /**
     * Executes the end-game use case and returns the result.
     *
     * @param players       the list of players who participated, must not be null
     * @param gameEndReason description of why the game ended (e.g., bankruptcy or round limit)
     * @param totalRounds   the number of rounds completed
     * @return an {@link EndGame.EndGameResult} containing final standings and summary
     */
    public EndGame.EndGameResult execute(
            List<Player> players,
            String gameEndReason,
            int totalRounds
    ) {
        return endGameUseCase.execute(players, gameEndReason, totalRounds);
    }
}
