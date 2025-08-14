package main.use_case.end_screen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import main.entity.players.AbstractPlayer;
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
        final List<AbstractPlayer> abstractPlayers = inputData.getPlayers();
        final String gameEndReason = inputData.getGameEndReason();
        final int totalRounds = inputData.getTotalRounds();

        final List<EndScreenOutputData.PlayerResult> playerResults = new ArrayList<>();

        // Sort players by total net worth (cash + properties + current stock values) for ranking
        final List<AbstractPlayer> sortedAbstractPlayers = new ArrayList<>(abstractPlayers);
        sortedAbstractPlayers.sort(Comparator.comparingDouble(this::calculateNetWorth).reversed());

        for (int i = 0; i < sortedAbstractPlayers.size(); i++) {
            final AbstractPlayer abstractPlayer = sortedAbstractPlayers.get(i);
            final float finalCash = abstractPlayer.getMoney();
            final float totalPropertyValue = calculateTotalPropertyValue(abstractPlayer);
            final float stockValue = calculateTotalStockValue(abstractPlayer);
            final float netWorth = finalCash + totalPropertyValue + stockValue;

            playerResults.add(new EndScreenOutputData.PlayerResult(
                    abstractPlayer,
                    i + 1,
                    finalCash,
                    totalPropertyValue,
                    stockValue,
                    netWorth
            ));
        }

        final AbstractPlayer winner = determineWinner(abstractPlayers);

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
     * @param abstractPlayer the player whose net worth is calculated
     * @return the player's net worth as a double
     */
    private double calculateNetWorth(AbstractPlayer abstractPlayer) {
        return abstractPlayer.getMoney() + calculateTotalPropertyValue(abstractPlayer)
                + calculateTotalStockValue(abstractPlayer);
    }

    /**
     * Calculate total value of all properties owned by a player.
     *
     * @param abstractPlayer the player whose properties are summed
     * @return the total property value
     */
    private float calculateTotalPropertyValue(AbstractPlayer abstractPlayer) {
        float total = 0;
        for (PropertyTile property : abstractPlayer.getProperties()) {
            total += property.getPrice();
        }
        return total;
    }

    /**
     * Calculate total value of stocks owned by a player.
     *
     * @param abstractPlayer the player whose stock holdings are valued
     * @return the total stock value
     */
    private float calculateTotalStockValue(AbstractPlayer abstractPlayer) {
        float total = 0;
        final Map<Stock, Integer> stocks = abstractPlayer.getStocks();

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
     * @param abstractPlayers the list of all players
     * @return the winning player, or null if no clear winner exists
     */
    private AbstractPlayer determineWinner(List<AbstractPlayer> abstractPlayers) {
        // Filter out bankrupt players
        final List<AbstractPlayer> solventAbstractPlayers = abstractPlayers.stream()
                .filter(player -> !player.isBankrupt())
                .toList();

        AbstractPlayer winner = null;
        if (solventAbstractPlayers.size() == 1) {
            winner = solventAbstractPlayers.get(0);
        }
        else if (solventAbstractPlayers.size() > 1) {
            // Player with highest net worth (cash + properties + stock values)
            winner = solventAbstractPlayers.stream()
                    .max(Comparator.comparingDouble(this::calculateNetWorth))
                    .orElse(null);
        }
        return winner;
    }
}
