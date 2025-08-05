package main.use_case.EndScreen;

import main.entity.players.Player;
import main.entity.tiles.PropertyTile;
import main.entity.Stocks.Stock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class EndGame {
    public EndGameResult execute(List<Player> players, String gameEndReason, int totalRounds) {
        List<PlayerResult> playerResults = new ArrayList<>();

        // Auto-liquidate all stocks for all players at game end
        for (Player player : players) {
            liquidateAllStocks(player);
        }

        // Sort players by total net worth (money + property value) for ranking
        List<Player> sortedPlayers = new ArrayList<>(players);
        sortedPlayers.sort(Comparator.comparingDouble(this::calculateNetWorth).reversed());

        for (int i = 0; i < sortedPlayers.size(); i++) {
            Player player = sortedPlayers.get(i);
            float totalPropertyValue = calculateTotalPropertyValue(player);
            float stockValue = calculateTotalStockValue(player); // Should be 0 after liquidation
            float netWorth = player.getMoney() + totalPropertyValue + stockValue;

            playerResults.add(new PlayerResult(
                    player,
                    i + 1, // rank
                    totalPropertyValue,
                    stockValue,
                    netWorth
            ));
        }

        Player winner = determineWinner(players);

        return new EndGameResult(
                playerResults,
                gameEndReason,
                totalRounds,
                winner
        );
    }

    /**
     * Liquidates all stocks owned by a player, converting them to cash
     */
    private void liquidateAllStocks(Player player) {
        Map<Stock, Integer> stocks = player.getStocks();
        List<Stock> stocksToSell = new ArrayList<>(stocks.keySet());

        for (Stock stock : stocksToSell) {
            int quantity = stocks.get(stock);
            if (quantity > 0) {
                player.sellStock(stock, quantity);
            }
        }
    }

    /**
     * Calculate total net worth including cash, properties, and stocks
     */
    private double calculateNetWorth(Player player) {
        return player.getMoney() + calculateTotalPropertyValue(player) + calculateTotalStockValue(player);
    }

    private float calculateTotalPropertyValue(Player player) {
        float total = 0;
        for (PropertyTile property : player.getProperties()) {
            total += property.getPrice();
        }
        return total;
    }

    /**
     * Calculate total value of stocks owned by player
     */
    private float calculateTotalStockValue(Player player) {
        float total = 0;
        Map<Stock, Integer> stocks = player.getStocks();

        for (Map.Entry<Stock, Integer> entry : stocks.entrySet()) {
            Stock stock = entry.getKey();
            int quantity = entry.getValue();
            total += (float)(stock.getCurrentPrice() * quantity);
        }

        return total;
    }

    private Player determineWinner(List<Player> players) {
        // Filter out bankrupt players
        List<Player> solventPlayers = players.stream()
                .filter(p -> !p.isBankrupt())
                .toList();

        if (solventPlayers.size() == 1) {
            return solventPlayers.get(0);
        } else if (solventPlayers.size() > 1) {
            // Find player with highest net worth (including liquidated stocks)
            return solventPlayers.stream()
                    .max(Comparator.comparingDouble(this::calculateNetWorth))
                    .orElse(null);
        }

        return null; // No winner if all bankrupt
    }

    public static class EndGameResult {
        private final List<PlayerResult> playerResults;
        private final String gameEndReason;
        private final int totalRounds;
        private final Player winner;

        public EndGameResult(List<PlayerResult> playerResults, String gameEndReason,
                             int totalRounds, Player winner) {
            this.playerResults = playerResults;
            this.gameEndReason = gameEndReason;
            this.totalRounds = totalRounds;
            this.winner = winner;
        }

        public List<PlayerResult> getPlayerResults() { return playerResults; }
        public String getGameEndReason() { return gameEndReason; }
        public int getTotalRounds() { return totalRounds; }
        public Player getWinner() { return winner; }
    }

    public static class PlayerResult {
        private final Player player;
        private final int rank;
        private final float totalPropertyValue;
        private final float totalStockValue;
        private final float netWorth;

        public PlayerResult(Player player, int rank, float totalPropertyValue,
                            float totalStockValue, float netWorth) {
            this.player = player;
            this.rank = rank;
            this.totalPropertyValue = totalPropertyValue;
            this.totalStockValue = totalStockValue;
            this.netWorth = netWorth;
        }

        public Player getPlayer() { return player; }
        public int getRank() { return rank; }
        public float getTotalPropertyValue() { return totalPropertyValue; }
        public float getTotalStockValue() { return totalStockValue; }
        public float getNetWorth() { return netWorth; }
    }
}