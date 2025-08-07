package main.entity.stocks;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.math3.distribution.NormalDistribution;

import main.constants.Constants;

public class Stock {
    private final String symbol;
    private double currentPrice;
    private double percentChange;

    // Distribution parameters
    private final double meanDailyReturnPct;
    private final double standardDeviationPct;
    private final NormalDistribution pctChangeDistribution;

    public Stock(String symbol, double currentPrice, double meanDailyReturnPct, double standardDeviationPct) {
        this.symbol = symbol;
        this.meanDailyReturnPct = meanDailyReturnPct;
        this.standardDeviationPct = standardDeviationPct;
        this.currentPrice = currentPrice;
        this.pctChangeDistribution = new NormalDistribution(meanDailyReturnPct, standardDeviationPct);
        this.percentChange = 0;
    }

    /**
     * Updates price of stock according to its distribution.
     */
    public void updatePrice() {
        final double percentChangeSample = pctChangeDistribution.sample();

        final double newPrice = currentPrice * (1 + percentChangeSample / Constants.PERCENTAGE_MULTIPLIER);

        final BigDecimal newPriceRounded = BigDecimal.valueOf(newPrice).setScale(2, RoundingMode.HALF_UP);
        this.currentPrice = newPriceRounded.doubleValue();
        this.percentChange = percentChangeSample;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public String getTicker() {
        return symbol;
    }

    public double getChange() {
        return percentChange;
    }

    // For debugging
    @Override
    public String toString() {
        return String.format("StockInfo{ticker='%s', currentPrice=%.2f, meanDailyReturn=%.4f%%, stdDev=%.4f%%}",
                symbol, currentPrice, meanDailyReturnPct, standardDeviationPct);
    }
}
