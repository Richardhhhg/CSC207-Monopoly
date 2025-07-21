package main.data_access.StockMarket;

public class StockInfoDataOutputObject {
    private final String ticker;
    private final double currentPrice;
    private final double meanDailyReturnPct;
    private final double standardDeviationPct;

    public StockInfoDataOutputObject(String ticker, double currentPrice, double meanDailyReturnPct, double standardDeviationPct) {
        this.ticker = ticker;
        this.currentPrice = currentPrice;
        this.meanDailyReturnPct = meanDailyReturnPct;
        this.standardDeviationPct = standardDeviationPct;
    }

    public String getTicker() {
        return ticker;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getMeanDailyReturnPct() {
        return meanDailyReturnPct;
    }

    public double getStandardDeviationPct() {
        return standardDeviationPct;
    }

    /**
     * Returns a string representation of the stock information.
     * This is for debugging purposes mainly
     *
     * @return A string containing the ticker, current price, mean daily return percentage, and standard deviation percentage.
     */
    @Override
    public String toString() {
        return String.format("StockInfo{ticker='%s', currentPrice=%.2f, meanDailyReturn=%.4f%%, stdDev=%.4f%%}",
                ticker, currentPrice, meanDailyReturnPct, standardDeviationPct);
    }
}