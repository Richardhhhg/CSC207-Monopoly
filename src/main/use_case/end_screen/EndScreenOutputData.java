package main.use_case.end_screen;

import java.util.List;

import main.entity.players.AbstractPlayer;

/**
 * Output data object for the End Screen use case.
 * This class encapsulates all the processed game result data that needs to be
 * passed from the interactor to the presenter.
 */
public class EndScreenOutputData {
    private final List<PlayerResult> playerResults;
    private final String gameEndReason;
    private final int totalRounds;
    private final AbstractPlayer winner;

    /**
     * Constructs a new EndScreenOutputData object.
     *
     * @param playerResults the list of player results with rankings and statistics
     * @param gameEndReason the reason the game ended
     * @param totalRounds   the total number of rounds played
     * @param winner        the winning player, or null if no winner
     */
    public EndScreenOutputData(List<PlayerResult> playerResults, String gameEndReason,
                               int totalRounds, AbstractPlayer winner) {
        this.playerResults = playerResults;
        this.gameEndReason = gameEndReason;
        this.totalRounds = totalRounds;
        this.winner = winner;
    }

    /**
     * Returns the list of player results.
     *
     * @return the list of PlayerResult objects containing player statistics
     */
    public List<PlayerResult> getPlayerResults() {
        return playerResults;
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
        return totalRounds - 1;
    }

    /**
     * Returns the winning player.
     *
     * @return the Player who won the game, or null if no winner
     */
    public AbstractPlayer getWinner() {
        return winner;
    }

    /**
     * Data class representing the results for a single player.
     */
    public static class PlayerResult {
        private final AbstractPlayer abstractPlayer;
        private final int rank;
        private final float finalCash;
        private final float totalPropertyValue;
        private final float totalStockValue;
        private final float netWorth;

        /**
         * Constructs a PlayerResult object.
         *
         * @param abstractPlayer             the player
         * @param rank               the player's final rank
         * @param finalCash          the player's final cash amount
         * @param totalPropertyValue the total value of the player's properties
         * @param totalStockValue    the total value of the player's stocks
         * @param netWorth           the player's total net worth
         */
        public PlayerResult(AbstractPlayer abstractPlayer, int rank, float finalCash,
                            float totalPropertyValue, float totalStockValue, float netWorth) {
            this.abstractPlayer = abstractPlayer;
            this.rank = rank;
            this.finalCash = finalCash;
            this.totalPropertyValue = totalPropertyValue;
            this.totalStockValue = totalStockValue;
            this.netWorth = netWorth;
        }

        public AbstractPlayer getPlayer() {
            return abstractPlayer;
        }

        public int getRank() {
            return rank;
        }

        public float getFinalCash() {
            return finalCash;
        }

        public float getTotalPropertyValue() {
            return totalPropertyValue;
        }

        public float getTotalStockValue() {
            return totalStockValue;
        }

        public float getNetWorth() {
            return netWorth;
        }
    }
}
