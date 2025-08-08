package main.use_case.Stocks;

/**
 * Output data for the sell stock use case.
 * Contains the result data of a successful stock sale.
 */
public class SellStockOutputData {
    private final String playerId;
    private final String stockTicker;
    private final int quantity;
    private final double totalSale;
    private final double newMoney;

    public SellStockOutputData(String playerId, String stockTicker, int quantity, 
                              double totalSale, double newMoney) {
        this.playerId = playerId;
        this.stockTicker = stockTicker;
        this.quantity = quantity;
        this.totalSale = totalSale;
        this.newMoney = newMoney;
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

    public double getTotalSale() {
        return totalSale;
    }

    public double getNewMoney() {
        return newMoney;
    }
}