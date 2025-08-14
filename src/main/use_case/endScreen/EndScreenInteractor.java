package main.use_case.endScreen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import main.entity.players.Player;
import main.entity.stocks.Stock;
import main.entity.tiles.PropertyTile;

/**
 * Interactor for the End Screen use case.
 * Handles the business logic for processing end game results, calculating player statistics,
 * determining rankings, and identifying the winner.
 */
public class EndScreenInteractor implements EndScreenInputBoundary {
    private final EndScreenOutputBoundary presenter;

    /**
     * Constructs the interactor with the given presenter.
     *
     * @param presenter Output boundary for presenting results.
     */
    public EndScreenInteractor(EndScreenOutputBoundary presenter) {
        this.presenter = presenter;
    }

    /**
     * Executes the end game use case.
     * Processes player data, calculates rankings and statistics, and presents the results.
     *
     * @param inputData The data required to process the end game scenario.
     */
    @Override
    public void execute(EndScreenInputData inputData) {
        final List<Player> players = inputData.getPlayers();
        final String gameEndReason = inputData.getGameEndReason();
        final int totalRounds = inputData.getTotalRounds();

        final List<EndScreenOutputData.PlayerResult> playerResults = new ArrayList<>();

        // Sort players by total net worth (cash + properties + current stock values) for ranking
        final List<Player> sortedPlayers = new ArrayList<>(players);
        sortedPlayers.sort(Comparator.comparingDouble(this::calculateNetWorth).reversed());

        for (int i = 0; i < sortedPlayers.size(); i++) {
            final Player player = sortedPlayers.get(i);
            final float finalCash = player.getMoney();
            final float totalPropertyValue = calculateTotalPropertyValue(player);
            final float stockValue = calculateTotalStockValue(player);
            final float netWorth = finalCash + totalPropertyValue + stockValue;

            playerResults.add(new EndScreenOutputData.PlayerResult(
                    player,
                    i + 1,
                    finalCash,
                    totalPropertyValue,
                    stockValue,
                    netWorth
            ));
        }

        final Player winner = determineWinner(players);

        final EndScreenOutputData outputData = new EndScreenOutputData(
                playerResults,
                gameEndReason,
                totalRounds,
                winner
        );

        presenter.presentEndGameResults(outputData);
    }

    /**
     * Calculate total net worth including cash, properties, and current stock values.
     *
     * @param player the player whose net worth is calculated
     * @return the player's net worth as a double
     */
    private double calculateNetWorth(Player player) {
        return player.getMoney() + calculateTotalPropertyValue(player) + calculateTotalStockValue(player);
    }

    /**
     * Calculate total value of all properties owned by a player.
     *
     * @param player the player whose properties are summed
     * @return the total property value
     */
    private float calculateTotalPropertyValue(Player player) {
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
    private float calculateTotalStockValue(Player player) {
        float total = 0;
        final Map<Stock, Integer> stocks = player.getStocks();

        for (Map.Entry<Stock, Integer> entry : stocks.entrySet()) {
            final Stock stock = entry.getKey();
            final int quantity = entry.getValue();
            total += stock.getCurrentPrice() * quantity;
        }

        return total;
    }

    /**
     * Determines the winner of the game based on solvency and net worth.
     *
     * @param players the list of all players
     * @return the winning player, or null if no clear winner exists
     */
    private Player determineWinner(List<Player> players) {
        // Filter out bankrupt players
        final List<Player> solventPlayers = players.stream()
                .filter(player -> !player.isBankrupt())
                .toList();

        Player winner = null;
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
}
