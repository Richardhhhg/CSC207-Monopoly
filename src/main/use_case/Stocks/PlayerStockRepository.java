package main.use_case.Stocks;

/**
 * Repository interface for player stock operations.
 * This interface defines the contract for accessing and modifying player stock data
 * without direct dependencies on entity implementations.
 */
public interface PlayerStockRepository {
    /**
     * Gets the current money amount for a player.
     *
     * @param playerId the player's identifier
     * @return the player's current money amount
     */
    double getPlayerMoney(String playerId);

    /**
     * Gets the quantity of a specific stock owned by a player.
     *
     * @param playerId the player's identifier
     * @param stockTicker the stock ticker symbol
     * @return the quantity of the stock owned by the player
     */
    int getPlayerStockQuantity(String playerId, String stockTicker);

    /**
     * Executes a stock purchase for a player.
     *
     * @param playerId the player's identifier
     * @param stockTicker the stock ticker symbol
     * @param quantity the quantity to buy
     * @param totalCost the total cost of the purchase
     */
    void buyStockForPlayer(String playerId, String stockTicker, int quantity, double totalCost);

    /**
     * Executes a stock sale for a player.
     *
     * @param playerId the player's identifier
     * @param stockTicker the stock ticker symbol
     * @param quantity the quantity to sell
     * @param totalSale the total sale amount
     */
    void sellStockForPlayer(String playerId, String stockTicker, int quantity, double totalSale);
}