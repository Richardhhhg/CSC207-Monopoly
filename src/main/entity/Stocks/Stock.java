package main.entity.Stocks;

import main.Constants.Constants;
import org.apache.commons.math3.distribution.NormalDistribution;
import java.math.BigDecimal;
import java.math.RoundingMode;

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

    public void updatePrice() {
        double percent_change = pctChangeDistribution.sample();

        double newPrice = currentPrice * (1 + percent_change / Constants.PERCENTAGE_MULTIPLIER);

        BigDecimal newPriceRounded = BigDecimal.valueOf(newPrice).setScale(2, RoundingMode.HALF_UP);
        this.currentPrice = newPriceRounded.doubleValue();
        this.percentChange = percent_change;
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
}
