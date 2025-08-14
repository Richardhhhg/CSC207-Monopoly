package main.use_case.endScreen;

import java.util.List;

import main.entity.players.Player;

/**
 * Input data object for the End Screen use case.
 * This class is immutable and used to transfer data from the controller to the use case interactor.
 */
public class EndScreenInputData {
    private final List<Player> players;
    private final String gameEndReason;
    private final int totalRounds;

    /**
     * Constructs a new EndScreenInputData object.
     *
     * @param players       the list of players participating in the game
     * @param gameEndReason the reason the game ended
     * @param totalRounds   the total number of rounds played
     */
    public EndScreenInputData(List<Player> players, String gameEndReason, int totalRounds) {
        this.players = players;
        this.gameEndReason = gameEndReason;
        this.totalRounds = totalRounds;
    }

    /**
     * Returns the list of players.
     *
     * @return the list of players participating in the game
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the reason the game ended.
     *
     * @return the game end reason
     */
    public String getGameEndReason() {
        return gameEndReason;
    }

    /**
     * Returns the total number of rounds played.
     *
     * @return the total rounds played
     */
    public int getTotalRounds() {
        return totalRounds;
    }
}
