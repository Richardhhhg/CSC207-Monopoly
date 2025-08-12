package main.entity.stocks;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.math3.distribution.NormalDistribution;

import main.constants.Constants;

public class Stock {
    private final String symbol;
    private double currentPrice;
    private double percentChange;

    private final NormalDistribution pctChangeDistribution;

    public Stock(String symbol, double currentPrice, double meanDailyReturnPct, double standardDeviationPct) {
        this.symbol = symbol;
        this.currentPrice = currentPrice;
        this.pctChangeDistribution = new NormalDistribution(meanDailyReturnPct, standardDeviationPct);
        this.percentChange = 0;
    }

    /**
     * Updates the price of the stock based on its distribution.
     */
    public void updatePrice() {
        final double percentChangeSample = pctChangeDistribution.sample();

        final double newPrice = currentPrice * (1 + percentChangeSample / Constants.PERCENTAGE_MULTIPLIER);

        final BigDecimal newPriceRounded = BigDecimal.valueOf(newPrice).setScale(2, RoundingMode.HALF_UP);
        this.currentPrice = newPriceRounded.doubleValue();
        this.percentChange = percentChangeSample;
    }

    public float getCurrentPrice() {
        return (float) currentPrice;
    }

    public String getTicker() {
        return symbol;
    }

    public float getChange() {
        return (float) percentChange;
    }
}
