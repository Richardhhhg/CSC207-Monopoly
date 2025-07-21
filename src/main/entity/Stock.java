package main.entity;

import main.Constants.Constants;
import main.data_access.StockMarket.StockInfoDataOutputObject;
import org.apache.commons.math3.distribution.NormalDistribution;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Stock {
    private final String symbol;
    private double currentPrice;

    // Distribution parameters
    private final double meanDailyReturnPct;
    private final double standardDeviationPct;
    private final NormalDistribution pctChangeDistribution;

    public Stock(StockInfoDataOutputObject stockInfoData) {
        this.symbol = stockInfoData.getTicker();
        this.meanDailyReturnPct = stockInfoData.getMeanDailyReturnPct();
        this.standardDeviationPct = stockInfoData.getStandardDeviationPct();
        this.currentPrice = stockInfoData.getCurrentPrice();
        this.pctChangeDistribution = new NormalDistribution(meanDailyReturnPct, standardDeviationPct);
    }

    public void updatePrice() {
        double percent_change = pctChangeDistribution.sample();

        double newPrice = currentPrice * (1 + percent_change / Constants.PERCENTAGE_MULTIPLIER);

        BigDecimal newPriceRounded = BigDecimal.valueOf(newPrice).setScale(2, RoundingMode.HALF_UP);
        this.currentPrice = newPriceRounded.doubleValue();
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public String getTicker() {
        return symbol;
    }

    // For debugging
    @Override
    public String toString() {
        return String.format("StockInfo{ticker='%s', currentPrice=%.2f, meanDailyReturn=%.4f%%, stdDev=%.4f%%}",
                symbol, currentPrice, meanDailyReturnPct, standardDeviationPct);
    }
}
