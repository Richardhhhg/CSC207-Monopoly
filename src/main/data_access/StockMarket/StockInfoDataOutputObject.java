package main.data_access.StockMarket;

public record StockInfoDataOutputObject(String ticker, double currentPrice, double meanDailyReturnPct,
                                        double standardDeviationPct) {
}