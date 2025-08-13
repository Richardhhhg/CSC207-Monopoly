package main.use_case.endScreen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import main.entity.stocks.Stock;
import main.entity.players.AbstractPlayer;
import main.entity.tiles.PropertyTile;

public class EndGame {

    /**
     * Builds the end-game result: ranks players, computes totals, and selects a winner.
     *
     * @param players       all players at game end
     * @param gameEndReason human-readable reason the game ended
     * @param totalRounds   total rounds played
     * @return an {@link EndGameResult} containing per-player results and winner info
     */
    public EndGameResult execute(List<AbstractPlayer> players, String gameEndReason, int totalRounds) {
        final List<PlayerResult> playerResults = new ArrayList<>();

        // Sort players by total net worth (cash + properties + current stock values) for ranking
        final List<AbstractPlayer> sortedPlayers = new ArrayList<>(players);
        sortedPlayers.sort(Comparator.comparingDouble(this::calculateNetWorth).reversed());

        for (int i = 0; i < sortedPlayers.size(); i++) {
            final AbstractPlayer player = sortedPlayers.get(i);
            final float finalCash = player.getMoney();
            final float totalPropertyValue = calculateTotalPropertyValue(player);
            final float stockValue = calculateTotalStockValue(player);
            final float netWorth = finalCash + totalPropertyValue + stockValue;

            playerResults.add(new PlayerResult(
                    player,
                    i + 1,
                    finalCash,
                    totalPropertyValue,
                    stockValue,
                    netWorth
            ));
        }

        final AbstractPlayer winner = determineWinner(players);

        return new EndGameResult(
                playerResults,
                gameEndReason,
                totalRounds,
                winner
        );
    }

    /**
     * Calculate total net worth including cash, properties, and current stock values.
     *
     * @param player the player whose net worth is calculated
     * @return the player's net worth as a double
     */
    private double calculateNetWorth(AbstractPlayer player) {
        return player.getMoney() + calculateTotalPropertyValue(player) + calculateTotalStockValue(player);
    }

    /**
     * Calculate total value of all properties owned by a player.
     *
     * @param player the player whose properties are summed
     * @return the total property value
     */
    private float calculateTotalPropertyValue(AbstractPlayer player) {
        float total = 0;
        for (PropertyTile property : player.getProperties()) {
            total += property.getPrice();
        }
        return total;
    }

    /**
     * Calculate total value of stocks owned by a player.
     *
     * @param player the player whose stock holdings are valued
     * @return the total stock value
     */
    private float calculateTotalStockValue(AbstractPlayer player) {
        float total = 0;
        final Map<Stock, Integer> stocks = player.getStocks();

        for (Map.Entry<Stock, Integer> entry : stocks.entrySet()) {
            final Stock stock = entry.getKey();
            final int quantity = entry.getValue();
            total += stock.getCurrentPrice() * quantity;
        }

        return total;
    }

    private AbstractPlayer determineWinner(List<AbstractPlayer> players) {
        // Filter out bankrupt players
        final List<AbstractPlayer> solventPlayers = players.stream()
                .filter(player -> !player.isBankrupt())
                .toList();

        AbstractPlayer winner = null;
        if (solventPlayers.size() == 1) {
            winner = solventPlayers.get(0);
        }
        else if (solventPlayers.size() > 1) {
            // Player with highest net worth (cash + properties + stock values)
            winner = solventPlayers.stream()
                    .max(Comparator.comparingDouble(this::calculateNetWorth))
                    .orElse(null);
        }
        return winner;
    }

    public static class EndGameResult {
        private final List<PlayerResult> playerResults;
        private final String gameEndReason;
        private final int totalRounds;
        private final AbstractPlayer winner;

        public EndGameResult(List<PlayerResult> playerResults, String gameEndReason,
                             int totalRounds, AbstractPlayer winner) {
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

        public AbstractPlayer getWinner() {
            return winner;
        }
    }

    public static class PlayerResult {
        private final AbstractPlayer player;
        private final int rank;
        private final float finalCash;
        private final float totalPropertyValue;
        private final float totalStockValue;
        private final float netWorth;

        public PlayerResult(AbstractPlayer player, int rank, float finalCash,
                            float totalPropertyValue, float totalStockValue, float netWorth) {
            this.player = player;
            this.rank = rank;
            this.finalCash = finalCash;
            this.totalPropertyValue = totalPropertyValue;
            this.totalStockValue = totalStockValue;
            this.netWorth = netWorth;
        }

        public AbstractPlayer getPlayer() {
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
