package main.interface_adapter.StockMarket;

/**
 * Data Transfer Object for stock information in the interface adapter layer.
 * This replaces direct dependencies on Stock entities.
 */
public class StockData {
    private final String ticker;
    private final double currentPrice;
    private final double change;

    public StockData(String ticker, double currentPrice, double change) {
        this.ticker = ticker;
        this.currentPrice = currentPrice;
        this.change = change;
    }

    public String getTicker() {
        return ticker;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getChange() {
        return change;
    }
}