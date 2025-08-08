package main.use_case.Stocks;

/**
 * Input data for the sell stock use case.
 * Contains all necessary information for selling a stock without direct entity dependencies.
 */
public class SellStockInputData {
    private final String playerId;
    private final String stockTicker;
    private final double stockPrice;
    private final int quantity;

    public SellStockInputData(String playerId, String stockTicker, double stockPrice, int quantity) {
        this.playerId = playerId;
        this.stockTicker = stockTicker;
        this.stockPrice = stockPrice;
        this.quantity = quantity;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getStockTicker() {
        return stockTicker;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}