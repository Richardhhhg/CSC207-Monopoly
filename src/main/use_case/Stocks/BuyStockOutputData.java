package main.use_case.Stocks;

/**
 * Output data for the buy stock use case.
 * Contains the result data of a successful stock purchase.
 */
public class BuyStockOutputData {
    private final String playerId;
    private final String stockTicker;
    private final int quantity;
    private final double totalCost;
    private final double remainingMoney;

    public BuyStockOutputData(String playerId, String stockTicker, int quantity, 
                             double totalCost, double remainingMoney) {
        this.playerId = playerId;
        this.stockTicker = stockTicker;
        this.quantity = quantity;
        this.totalCost = totalCost;
        this.remainingMoney = remainingMoney;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getStockTicker() {
        return stockTicker;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public double getRemainingMoney() {
        return remainingMoney;
    }
}