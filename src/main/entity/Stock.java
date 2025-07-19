package main.entity;

import org.apache.commons.math3.distribution.NormalDistribution;
import java.math.BigDecimal;
import java.math.RoundingMode;

// TODO: This code is largely AI written, review and refactor as necessary
public class Stock {
    private final String symbol;
    private BigDecimal currentPrice;

    // Distribution parameters
    private double mean;
    private double stddev;

    public Stock(String symbol, double mean, double stddev) {
        this.symbol = symbol;
        this.mean = mean;
        this.stddev = stddev;
        // TODO: Make this call the api to get real time price when initializing stock
        this.currentPrice = BigDecimal.valueOf(mean).setScale(2, RoundingMode.HALF_UP);
    }

    public void updatePrice() {
        NormalDistribution pct_change_dist = new NormalDistribution(mean, stddev);
        double percent_change = pct_change_dist.sample();

        double newPrice = currentPrice.doubleValue() * (1 + percent_change);

        this.currentPrice = BigDecimal.valueOf(newPrice).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public String getSymbol() {
        return symbol;
    }
}
