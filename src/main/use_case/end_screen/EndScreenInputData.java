package main.use_case.end_screen;

import java.util.List;

import main.entity.players.AbstractPlayer;

/**
 * Input data object for the End Screen use case.
 * This class is immutable and used to transfer data from the controller to the use case interactor.
 */
public class EndScreenInputData {
    private final List<AbstractPlayer> abstractPlayers;
    private final String gameEndReason;
    private final int totalRounds;

    /**
     * Constructs a new EndScreenInputData object.
     *
     * @param abstractPlayers       the list of players participating in the game
     * @param gameEndReason the reason the game ended
     * @param totalRounds   the total number of rounds played
     */
    public EndScreenInputData(List<AbstractPlayer> abstractPlayers, String gameEndReason, int totalRounds) {
        this.abstractPlayers = abstractPlayers;
        this.gameEndReason = gameEndReason;
        this.totalRounds = totalRounds;
    }

    /**
     * Returns the list of players.
     *
     * @return the list of players participating in the game
     */
    public List<AbstractPlayer> getPlayers() {
        return abstractPlayers;
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
