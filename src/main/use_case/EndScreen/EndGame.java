package main.use_case.EndScreen;

import main.entity.players.Player;
import main.entity.tiles.PropertyTile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EndGame {
    public EndGameResult execute(List<Player> players, String gameEndReason, int totalRounds) {
        List<PlayerResult> playerResults = new ArrayList<>();

        // Sort players by money (descending) for ranking
        List<Player> sortedPlayers = new ArrayList<>(players);
        sortedPlayers.sort(Comparator.comparingDouble(Player::getMoney).reversed());

        for (int i = 0; i < sortedPlayers.size(); i++) {
            Player player = sortedPlayers.get(i);
            float totalPropertyValue = calculateTotalPropertyValue(player);
            float netWorth = player.getMoney() + totalPropertyValue;

            playerResults.add(new PlayerResult(
                    player,
                    i + 1, // rank
                    totalPropertyValue,
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

    private float calculateTotalPropertyValue(Player player) {
        float total = 0;
        for (PropertyTile property : player.getProperties()) {
            total += property.getPrice();
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
            // Find player with most money
            return solventPlayers.stream()
                    .max(Comparator.comparingDouble(Player::getMoney))
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
        private final float netWorth;

        public PlayerResult(Player player, int rank, float totalPropertyValue, float netWorth) {
            this.player = player;
            this.rank = rank;
            this.totalPropertyValue = totalPropertyValue;
            this.netWorth = netWorth;
        }

        public Player getPlayer() { return player; }
        public int getRank() { return rank; }
        public float getTotalPropertyValue() { return totalPropertyValue; }
        public float getNetWorth() { return netWorth; }
    }
}
