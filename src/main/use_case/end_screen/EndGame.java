package main.use_case.end_screen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import main.entity.Stocks.Stock;
import main.entity.players.Player;
import main.entity.tiles.PropertyTile;

/**
 * Use case for computing end-of-game results, including player rankings and winner.
 */
public class EndGame {

    /**
     * Executes the end-game logic and returns the results.
     *
     * @param players       list of participating players, must not be null
     * @param gameEndReason description of why the game ended
     * @param totalRounds   total number of rounds played
     * @return an EndGameResult containing player results, game end reason, total
     *         rounds, and winner
     */
    public EndGameResult execute(List<Player> players,
                                 String gameEndReason,
                                 int totalRounds) {
        final List<PlayerResult> playerResults = new ArrayList<>();

        final List<Player> sortedPlayers = new ArrayList<>(players);
        sortedPlayers.sort(
                Comparator.comparingDouble(this::calculateNetWorth).reversed()
        );

        for (int i = 0; i < sortedPlayers.size(); i++) {
            final Player player = sortedPlayers.get(i);
            final float finalCash = player.getMoney();
            final float totalPropertyValue = calculateTotalPropertyValue(player);
            final float totalStockValue = calculateTotalStockValue(player);
            final float netWorth = finalCash + totalPropertyValue + totalStockValue;

            playerResults.add(
                    new PlayerResult(
                            player,
                            i + 1,
                            finalCash,
                            totalPropertyValue,
                            totalStockValue,
                            netWorth
                    )
            );
        }

        final Player winner;
        final List<Player> solventPlayers = players.stream()
                .filter(player -> !player.isBankrupt())
                .toList();

        if (solventPlayers.size() == 1) {
            winner = solventPlayers.get(0);
        }
        else if (solventPlayers.size() > 1) {
            winner = solventPlayers.stream()
                    .max(Comparator.comparingDouble(this::calculateNetWorth))
                    .orElse(null);
        }
        else {
            winner = null;
        }

        return new EndGameResult(
                playerResults,
                gameEndReason,
                totalRounds,
                winner
        );
    }

    /**
     * Calculates the total net worth for a player, including cash, properties, and
     * stocks.
     *
     * @param player the player to calculate net worth for, must not be null
     * @return the total net worth as a double
     */
    private double calculateNetWorth(Player player) {
        return player.getMoney()
                + calculateTotalPropertyValue(player)
                + calculateTotalStockValue(player);
    }

    /**
     * Calculates total property value for a player.
     *
     * @param player the player whose properties to sum, must not be null
     * @return the sum of property prices as a float
     */
    private float calculateTotalPropertyValue(Player player) {
        float total = 0;
        for (PropertyTile property : player.getProperties()) {
            total += property.getPrice();
        }
        return total;
    }

    /**
     * Calculates total stock value for a player.
     *
     * @param player the player whose stocks to value, must not be null
     * @return the sum of stock values as a float
     */
    private float calculateTotalStockValue(Player player) {
        float total = 0;
        final Map<Stock, Integer> stocks = player.getStocks();
        for (Map.Entry<Stock, Integer> entry : stocks.entrySet()) {
            final Stock stock = entry.getKey();
            final int quantity = entry.getValue();
            total += (float) (stock.getCurrentPrice() * quantity);
        }
        return total;
    }

    /**
     * Value object containing all end-of-game results.
     */
    public static class EndGameResult {
        private final List<PlayerResult> playerResults;
        private final String gameEndReason;
        private final int totalRounds;
        private final Player winner;

        public EndGameResult(List<PlayerResult> playerResults,
                             String gameEndReason,
                             int totalRounds,
                             Player winner) {
            this.playerResults = playerResults;
            this.gameEndReason = gameEndReason;
            this.totalRounds = totalRounds;
            this.winner = winner;
        }

        public List<PlayerResult> getPlayerResults() {
            return playerResults;
        }

        public String getGameEndReason() {
            return gameEndReason;
        }

        public int getTotalRounds() {
            return totalRounds;
        }

        public Player getWinner() {
            return winner;
        }
    }

    /**
     * Value object for an individual player's end-game metrics.
     */
    public static class PlayerResult {
        private final Player player;
        private final int rank;
        private final float finalCash;
        private final float totalPropertyValue;
        private final float totalStockValue;
        private final float netWorth;

        public PlayerResult(Player player,
                            int rank,
                            float finalCash,
                            float totalPropertyValue,
                            float totalStockValue,
                            float netWorth) {
            this.player = player;
            this.rank = rank;
            this.finalCash = finalCash;
            this.totalPropertyValue = totalPropertyValue;
            this.totalStockValue = totalStockValue;
            this.netWorth = netWorth;
        }

        public Player getPlayer() {
            return player;
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
