package main.entity;

import org.apache.commons.math3.distribution.NormalDistribution;
import java.math.BigDecimal;
import java.math.RoundingMode;

// TODO: This code is largely AI written, review and refactor as necessary
public class Stock {
    private final String symbol;
    private double currentPrice;

    // Distribution parameters
    private double mean;
    private double stddev;

    public Stock(String symbol, double mean, double stddev) {
        this.symbol = symbol;
        this.mean = mean;
        this.stddev = stddev;
        // TODO: Make this call the api to get real time price when initializing stock
        BigDecimal currentPrice = BigDecimal.valueOf(mean).setScale(2, RoundingMode.HALF_UP);
        this.currentPrice = currentPrice.doubleValue();
    }

    public void updatePrice() {
        NormalDistribution pct_change_dist = new NormalDistribution(mean, stddev);
        double percent_change = pct_change_dist.sample();

        double newPrice = currentPrice * (1 + percent_change);

        BigDecimal newPriceRounded = BigDecimal.valueOf(newPrice).setScale(2, RoundingMode.HALF_UP);
        this.currentPrice = newPriceRounded.doubleValue();
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public String getTicker() {
        return symbol;
    }
}
